/*
 *    Copyright 2019 MetaRing s.r.l.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.metaring.springbootappexample.strategy;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ff4j.core.FeatureStore;
import org.ff4j.core.FlippingExecutionContext;
import org.ff4j.strategy.AbstractFlipStrategy;
import org.mozilla.javascript.Context;

import com.google.common.io.Files;
import com.metaring.framework.util.ObjectUtil;
import com.metaring.framework.util.StringUtil;

public class JavascriptFlipStrategy extends AbstractFlipStrategy {

    private static final long serialVersionUID = 5103342692592508589L;

    private static final Map<String, String> PARAMETERS = new HashMap<>();
    private static final List<String> ORDERED_PARAMETERS = new ArrayList<>();

    private static final Field PARAMETERS_FIELD;
    static {
        Field f = null;
        try {
            f = FlippingExecutionContext.class.getDeclaredField("parameters");
            f.setAccessible(true);
        } catch (Exception e) {
        }
        PARAMETERS_FIELD = f;
    }

    @Override
    public void init(String featureName, Map<String, String> initParam) {
        if(ObjectUtil.isNullOrEmpty(initParam) || !ObjectUtil.isNullOrEmpty(PARAMETERS)) {
            return;
        }
        initParam.forEach((key, value) -> PARAMETERS.put(new String(key), tryLoadFile(new String(value).trim())));
        ORDERED_PARAMETERS.addAll(PARAMETERS.keySet());
        Collections.sort(ORDERED_PARAMETERS);
    }

    private static final String tryLoadFile(String value) {
        if(StringUtil.isNullOrEmpty(value)) {
            return value == null ? null : value.trim();
        }
        try {
            final StringBuilder sb = new StringBuilder();
            Files.readLines(new File(value.trim()), Charset.defaultCharset()).forEach(it -> sb.append(it).append('\n'));
            return sb.toString().trim();
        } catch(Exception e) {
        }
        return value.trim();
    }

    @SuppressWarnings("unchecked")
    private static final Map<String, Object> extractParameters(FlippingExecutionContext ctx) {
        if (ctx == null) {
            return null;
        }
        try {
            return (Map<String, Object>) PARAMETERS_FIELD.get(ctx);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public boolean evaluate(String featureName, FeatureStore fStore, FlippingExecutionContext ctx) {
        if (ObjectUtil.isNullOrEmpty(PARAMETERS)) {
            return true;
        }
        final Map<String, Object> parameters = extractParameters(ctx);
        StringBuilder sb = new StringBuilder();
        parameters.keySet().forEach(it -> {
            Object elem = parameters.get(it);
            if(elem != null && elem instanceof String) {
                elem = "'" + elem + "'";
            }
            sb.append("this['").append(it).append("'] = ").append(elem == null ? "null" : elem.toString()).append(";\n");
        });
        String vars = sb.toString();
        for (String key : ORDERED_PARAMETERS) {
            try {
                Context context = Context.getCurrentContext();
                Object jsObjectResult = (context = context != null ? context : Context.enter()).compileString((vars + PARAMETERS.get(key)), key, 0, null).exec(context, context.initStandardObjects());
                boolean result = Boolean.parseBoolean(Context.toString(jsObjectResult));
                if(!result) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}