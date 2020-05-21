package com.metaring.springbootappexample.service.roles.callback;

import org.mozilla.javascript.*;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class CallbackScriptRunner {
    /***
     * With javax.script example
     * @throws ScriptException
     */
    public static void invokeDatabaseCallback() throws ScriptException {
        ScriptEngine js = new ScriptEngineManager().getEngineByExtension("js");
        System.out.println();
        js.getContext().setAttribute("callBack", new DatabaseCallbackImpl(), ScriptContext.ENGINE_SCOPE);
        js.eval("var impl = { run:  function () { print('Hello, World!'); } };\n"
                + "var runnable = new java.lang.Runnable(impl);\n"
                + "callBack.invoke(runnable);\n");
    }

    /***
     * With rhino {@link #executeScriptWithContinuations()}.
     */
    public static void executeScriptWithContinuations() {
        Context cx = Context.enter();
        Scriptable globalScope = null;
        try {
            globalScope = cx.initStandardObjects();
            cx.setOptimizationLevel(-1); // must use interpreter mode
            globalScope.put("myObject", globalScope, Context.javaToJS(new MyCallbackClass(), globalScope));
            Script script = cx.compileString("myObject.function(3) + 1;", "test source", 1, null);
            cx.executeScriptWithContinuations(script, globalScope);
            fail("Should throw ContinuationPending");
        } catch (ContinuationPending pending) {
            Object applicationState = pending.getApplicationState();
            assertEquals(new Integer(3), applicationState);
            int saved = (Integer) applicationState;
            Object result = cx.resumeContinuation(pending.getContinuation(), globalScope, saved + 1);
            assertEquals(5, ((Number) result).intValue());
        } finally {
            Context.exit();
        }
    }

    /***
     * With rhino {@link #doTopCall()}.
     */
    public static void doTopCall() {
        Context context = Context.enter();
        Scriptable globalScope;
        try {
            globalScope = context.initStandardObjects();
            context.setOptimizationLevel(-1);
            globalScope.put("myCallbackObject", globalScope, Context.javaToJS(new MyCallbackClass(), globalScope));
            Object result = ScriptRuntime.doTopCall((cx, scope, thisObj, args) -> {
                System.out.println("We are in callback .......");
                Script script = cx.compileString("myCallbackObject.getUserRole(\"hhovhann\");", "callback source", 1, null);
                return script.exec(cx, scope);
            }, context, globalScope, globalScope, new String[]{"Hello", "World", "Armenia"}, true);
            System.out.println(result);
        } finally {
            Context.exit();
        }
    }
}
