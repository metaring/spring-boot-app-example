package com.metaring.springbootappexample.service.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.ff4j.FF4j;
import org.ff4j.core.FlippingExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.metaring.framework.auth.LimitedAccessModuleInfo;

public class VerifyEnableFunctionalityImpl extends VerifyEnableFunctionality {

    @Autowired
    private FF4j ff4j;

    @Override
    protected CompletableFuture<Void> preConditionCheck(EnableDataModel input) throws Exception {
        return end;
    }

    @Override
    protected CompletableFuture<Boolean> call(EnableDataModel input) throws Exception {
        @SuppressWarnings("unchecked")
        Map<String, Object> context = (Map<String, Object>) getContext().getData().clone();
        context.put("ENABLE_DATA", input);
        FlippingExecutionContext flippingExecutionContext = new FlippingExecutionContext(context);
        List<String> functionalities = new ArrayList<>();

        functionalities.add(LimitedAccessModuleInfo.INFO.getFunctionalityFullyQualifiedName());
        functionalities.add(input.getFunctionalityName());

        for(String functionalityName : functionalities) {
            if(!passed(functionalityName, flippingExecutionContext)) {
                return end(false);
            }
        }
        return end(true);
    }

    @Override
    protected CompletableFuture<Void> postConditionCheck(EnableDataModel input, Boolean output) throws Exception {
        return end;
    }

    private final boolean passed(String functionalityName, FlippingExecutionContext context) {
        boolean exists = ff4j.exist(functionalityName);
        context.addValue(functionalityName + ".exists", exists);
        boolean isEnabled = exists && ff4j.getFeature(functionalityName).isEnable();
        context.addValue(functionalityName + ".isEnabled", isEnabled);
        boolean check = isEnabled && ff4j.check(functionalityName, context);
        context.addValue(functionalityName + ".check", check);
        boolean passed = check || !isEnabled;
        context.addValue(functionalityName + ".passed", passed);
        return passed;
    }
}