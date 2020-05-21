package com.metaring.springbootappexample.service.roles.callback;

import com.metaring.springbootappexample.service.roles.RoleData;
import com.metaring.springbootappexample.service.roles.RoleFunctionalitiesManager;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;

import java.util.concurrent.CompletableFuture;

import static com.ea.async.Async.await;


public class MyCallbackClass {

    public int function(int a) {
        Context cx = Context.enter();
        try {
            ContinuationPending pending = cx.captureContinuation();
            pending.setApplicationState(a);
            throw pending;
        } finally {
            Context.exit();
        }
    }

    public CompletableFuture<String> getUserRole(String userId) throws Exception {
        System.out.println("MyCallbackClass.getUserRole invoked ...");
        RoleData roleData = await(RoleFunctionalitiesManager.getUserRole(userId)); // TODO freezing with message:  Warning: Illegal call to await, the method invoking await must return a CompletableFuture
        String roleId = roleData.getRoleId();
        System.out.println("Get for user " + userId + ", user role." + roleId);
        return CompletableFuture.completedFuture(roleId);
    }
}