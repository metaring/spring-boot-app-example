package com.metaring.springbootappexample.service.auth;

import com.metaring.framework.functionality.AbstractFunctionality;
import com.metaring.framework.functionality.GeneratedFunctionality;
import com.metaring.framework.auth.LimitedAccessModuleInfo;
import com.metaring.springbootappexample.service.auth.EnableDataModel;
import java.util.concurrent.CompletableFuture;

public abstract class VerifyEnableFunctionality extends AbstractFunctionality implements GeneratedFunctionality {

    public static final VerifyEnableFunctionality INSTANCE = new VerifyEnableFunctionalityImpl();

    protected VerifyEnableFunctionality() {
        super(LimitedAccessModuleInfo.INFO, Boolean.class);
    }

    @Override
    protected final CompletableFuture<Void> beforePreConditionCheck(Object input) throws Exception {
        return beforePreConditionCheck(input == null ? null : (EnableDataModel) input);
    }

    protected CompletableFuture<Void> beforePreConditionCheck(EnableDataModel input) throws Exception {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected final CompletableFuture<Void> preConditionCheck(Object input) throws Exception {
        return preConditionCheck(input == null ? null : (EnableDataModel) input);
    }

    protected abstract CompletableFuture<Void> preConditionCheck(EnableDataModel input) throws Exception;

    @Override
    protected final CompletableFuture<Void> afterPreConditionCheck(Object input) throws Exception {
        return afterPreConditionCheck(input == null ? null : (EnableDataModel) input);
    }

    protected CompletableFuture<Void> afterPreConditionCheck(EnableDataModel input) throws Exception {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected final CompletableFuture<Void> beforeCall(Object input) throws Exception {
        return beforeCall(input == null ? null : (EnableDataModel) input);
    }

    protected CompletableFuture<Void> beforeCall(EnableDataModel input) throws Exception {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected final CompletableFuture<Object> call(Object input) throws Exception {
        CompletableFuture<Boolean> call = call(input == null ? null : (EnableDataModel) input);
        if(call == null) {
            return CompletableFuture.completedFuture(null);
        }
        final CompletableFuture<Object> response = new CompletableFuture<>();
        call.whenCompleteAsync((result, error) -> {
            if(error != null) {
                response.completeExceptionally(error);
                return;
            }
            response.complete(result);
        }, EXECUTOR);
        return response;
    }

    protected abstract CompletableFuture<Boolean> call(EnableDataModel input) throws Exception;

    @Override
    protected final CompletableFuture<Void> afterCall(Object input, Object output) throws Exception {
        return afterCall(input == null ? null : (EnableDataModel) input, output == null ? null : (Boolean) output);
    }

    protected CompletableFuture<Void> afterCall(EnableDataModel input, Boolean output) throws Exception {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected final CompletableFuture<Void> beforePostConditionCheck(Object input, Object output) throws Exception {
        return beforePostConditionCheck(input == null ? null : (EnableDataModel) input, output == null ? null : (Boolean) output);
    }

    protected CompletableFuture<Void> beforePostConditionCheck(EnableDataModel input, Boolean output) throws Exception {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected final CompletableFuture<Void> postConditionCheck(Object input, Object output) throws Exception {
        return postConditionCheck(input == null ? null : (EnableDataModel) input, output == null ? null : (Boolean) output);
    }

    protected abstract CompletableFuture<Void> postConditionCheck(EnableDataModel input, Boolean output) throws Exception;

    @Override
    protected final CompletableFuture<Void> afterPostConditionCheck(Object input, Object output) throws Exception {
        return afterPostConditionCheck(input == null ? null : (EnableDataModel) input, output == null ? null : (Boolean) output);
    }

    protected CompletableFuture<Void> afterPostConditionCheck(EnableDataModel input, Boolean output) throws Exception {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected final Object getInputFromJsonWork(String inputJson) {
        return EnableDataModel.fromJson(inputJson);
    }
}