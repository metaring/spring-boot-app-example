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
import org.ff4j.property.Property;
import org.ff4j.strategy.AbstractFlipStrategy;
import org.mozilla.javascript.Context;

import com.google.common.io.Files;
import com.metaring.framework.util.ObjectUtil;
import com.metaring.framework.util.StringUtil;

public class JavascriptFlipStrategy extends AbstractFlipStrategy {

    private static final long serialVersionUID = 5103342692592508589L;

    private static final String PARAM_MODULAR_NAME = "modular";
    private static final String NEW_LINE_SPLITERATOR = "\n";

    private static final Map<String, Map<String, String>> PARAMETERS = new HashMap<>();
    private static final Map<String, List<String>> ORDERED_PARAMETERS = new HashMap<>();

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
        if(ObjectUtil.isNullOrEmpty(initParam) || PARAMETERS.containsKey(featureName)) {
            return;
        }
        PARAMETERS.put(featureName, new HashMap<>());
        ORDERED_PARAMETERS.put(featureName, new ArrayList<>());
        final Map<String, String> featureParameters = PARAMETERS.get(featureName);
        final List<String> featureOrderedParameters = ORDERED_PARAMETERS.get(featureName);
        final boolean[] modular = {false};
        initParam.forEach((key, value) -> {
            if(key.equals(PARAM_MODULAR_NAME)) {
                modular[0] = Boolean.parseBoolean(value);
                return;
            }
            featureParameters.put(new String(key), tryLoadFile(new String(value).trim()));
        });
        featureOrderedParameters.addAll(featureParameters.keySet());
        Collections.sort(featureOrderedParameters);
        if(modular[0] || featureOrderedParameters.size() == 1) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        featureOrderedParameters.forEach(it -> sb.append(featureParameters.get(it)).append(NEW_LINE_SPLITERATOR));
        featureOrderedParameters.clear();
        featureParameters.clear();
        featureOrderedParameters.add("script");
        featureParameters.put("script", sb.toString());
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
    private static final Map<String, Object> extractParametersFromContext(FlippingExecutionContext ctx) {
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
        final Map<String, String> featureParameters = PARAMETERS.get(featureName);
        if (ObjectUtil.isNullOrEmpty(featureParameters)) {
            return true;
        }
        final Map<String, Object> contextParameters = extractParametersFromContext(ctx);
        StringBuilder sb = new StringBuilder("var context = {};\n");
        contextParameters.keySet().forEach(it -> {
            Object elem = contextParameters.get(it);
            if(elem != null && elem instanceof String) {
                elem = "'" + elem + "'";
            }
            sb.append("context['").append(it).append("'] = ").append(elem == null ? "null" : elem.toString()).append(";\n");
        });
        // TODO The way to read the properties defined between <properties></properties>, but when adding in context then rhino throwing } exception
//        FF4j ff4j = (FF4j) contextParameters.get("FF4J");
//        Map<String, Property<?>> properties = ff4j.getProperties();
        // The way to read the properties defined between <custom-properties></custom-properties>
        Map<String, Property<?>> customProperties = fStore.read(featureName).getCustomProperties();
        if (!customProperties.get("modular").asBoolean()) {
            String mergedScript = String.join(NEW_LINE_SPLITERATOR,
                    tryLoadFile(customProperties.get("scriptPath").asString()),
                    featureParameters.get("script"));
            featureParameters.put("script", mergedScript); // will replace existing value for script key
        }
        String vars = sb.toString();
        Context context = Context.getCurrentContext();
        context = context != null ? context : Context.enter();
        for (String key : ORDERED_PARAMETERS.get(featureName)) {
            try {
                if(!Boolean.parseBoolean(Context.toString(context.compileString((vars + featureParameters.get(key)), key, 0, null).exec(context, context.initSafeStandardObjects())))) {
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