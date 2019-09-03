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
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.ff4j.core.FeatureStore;
import org.ff4j.core.FlippingExecutionContext;
import org.ff4j.property.Property;
import org.ff4j.strategy.AbstractFlipStrategy;
import org.mozilla.javascript.Context;

import com.google.common.io.Files;
import com.metaring.framework.util.ObjectUtil;
import com.metaring.framework.util.StringUtil;
import com.metaring.springbootappexample.configuration.FF4JConfiguration;

public class JavascriptFlipStrategy extends AbstractFlipStrategy {

    private static final long serialVersionUID = 5103342692592508589L;

    private static final String PARAM_MODULAR_NAME = "js_modular";
    private static final String PARAM_LIBRARY_NAME_PREFIX = "js_lib_";
    private static final String NEW_LINE_SPLITERATOR = "\n";
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private static final Map<String, Map<String, String>> PARAMETERS = new TreeMap<>();
//    private static final Map<String, List<String>> ORDERED_PARAMETERS = new HashMap<>();
    private static final Map<String, String> LIBRARIES = new TreeMap<>();

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
        if (ObjectUtil.isNullOrEmpty(initParam) || PARAMETERS.containsKey(featureName)) {
            return;
        }
        PARAMETERS.put(featureName, new HashMap<>());
//        ORDERED_PARAMETERS.put(featureName, new ArrayList<>());
        final Map<String, String> featureParameters = PARAMETERS.get(featureName);
//        final List<String> featureOrderedParameters = ORDERED_PARAMETERS.get(featureName);
        initParam.forEach((key, value) -> featureParameters.put(new String(key), tryLoadFile(new String(value).trim())));
//        featureOrderedParameters.addAll(featureParameters.keySet());
//        Collections.sort(featureOrderedParameters);
    }

    private static final String tryLoadFile(String value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return value == null ? null : value.trim();
        }
        try {
            final StringBuilder sb = new StringBuilder();
            Files.readLines(new File(value.trim()), CHARSET).forEach(it -> sb.append(it).append(NEW_LINE_SPLITERATOR));
            return sb.toString().trim();
        } catch (Exception e) {
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

    private final String setupCustomVars(Map<String, String> featureParameters, Map<String, Property<?>> customProperties) {
        Map<String, Property<?>> globalProperties = FF4JConfiguration.GLOBAL_PROPERTIES;
        boolean modular = globalProperties.containsKey(PARAM_MODULAR_NAME) && globalProperties.get(PARAM_MODULAR_NAME).asBoolean();
        modular = customProperties.containsKey(PARAM_MODULAR_NAME) ? customProperties.get(PARAM_MODULAR_NAME).asBoolean() : modular;

        if (!modular && featureParameters.size() > 1) {
            StringBuilder sb = new StringBuilder();
            featureParameters.forEach((key, value) -> sb.append(value).append(NEW_LINE_SPLITERATOR));
//            featureOrderedParameters.clear();
            featureParameters.clear();
//            featureOrderedParameters.add("script");
            featureParameters.put("script", sb.toString());
        }
        StringBuilder libraries = new StringBuilder();
//        ConcurrentSkipListSet globalLibraryNames = new ConcurrentSkipListSet();
        Stream.concat(globalProperties.keySet().stream(), customProperties.keySet().stream()
                .filter(it -> !globalProperties.containsKey(it) || customProperties.get(it).asBoolean()))
                .filter(it -> it.startsWith(PARAM_LIBRARY_NAME_PREFIX))
                .distinct()
                .sorted()
//         .forEach(it -> {
//            Map<String, Property<?>> map = globalProperties.containsKey(it) ? globalProperties : customProperties;
//            libraries
//                    .append(tryLoadFile(map.get(it).asString()))
//                    .append(NEW_LINE_SPLITERATOR);
//        });
        .collect((it, value) -> {
            Map<String, Property<?>> map = globalProperties.containsKey(it) ? globalProperties : customProperties;
         Collectors.joining();
         return null;
        });
//        return libraries.toString().trim();
    }

    @Override
    public boolean evaluate(String featureName, FeatureStore fStore, FlippingExecutionContext ctx) {
        final Map<String, String> featureParameters = PARAMETERS.get(featureName);
        if (ObjectUtil.isNullOrEmpty(featureParameters)) {
            return true;
        }
//        final List<String> featureOrderedParameters = ORDERED_PARAMETERS.get(featureName);
        String libraries = LIBRARIES.get(featureName);
        if (libraries == null) {
            LIBRARIES.put(featureName, libraries = setupCustomVars(featureParameters, fStore.read(featureName).getCustomProperties()));
        }
        final Map<String, Object> contextParameters = extractParametersFromContext(ctx);
        StringBuilder sb = new StringBuilder("var context = {};").append(NEW_LINE_SPLITERATOR);
        contextParameters.keySet().forEach(it -> {
            Object elem = contextParameters.get(it);
            if (elem != null && elem instanceof String) {
                elem = "'" + elem + "'";
            }
            sb.append("context['").append(it).append("'] = ").append(elem == null ? "null" : elem.toString()).append(";").append(NEW_LINE_SPLITERATOR);
        });
        final String vars = sb.toString();
        Context context = Context.getCurrentContext();
        context = context != null ? context : Context.enter();
        for (String key : featureParameters.keySet()) {
            try {
                if (!Boolean.parseBoolean(Context.toString(context.compileString((libraries + vars + featureParameters.get(key)), key, 0, null).exec(context, context.initSafeStandardObjects())))) {
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