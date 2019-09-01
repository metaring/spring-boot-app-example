package com.metaring.springbootappexample.service.auth;

import com.metaring.framework.functionality.AbstractFunctionality;
import com.metaring.framework.functionality.GeneratedFunctionality;
import java.util.concurrent.CompletableFuture;
import com.metaring.framework.auth.IdentificationModuleInfo;
import com.metaring.springbootappexample.service.auth.IdentificationDataModel;

public abstract class VerifyIdentificationFunctionality extends AbstractFunctionality implements GeneratedFunctionality {

    public static final VerifyIdentificationFunctionality INSTANCE = new VerifyIdentificationFunctionalityImpl();

    protected VerifyIdentificationFunctionality() {
        super(IdentificationModuleInfo.INFO, Boolean.class);
    }

    @Override
    protected final CompletableFuture<Void> beforePreConditionCheck(Object input) throws Exception {
        return beforePreConditionCheck(input == null ? null : (IdentificationDataModel) input);
    }

    protected CompletableFuture<Void> beforePreConditionCheck(IdentificationDataModel input) throws Exception {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected final CompletableFuture<Void> preConditionCheck(Object input) throws Exception {
        return preConditionCheck(input == null ? null : (IdentificationDataModel) input);
    }

    protected abstract CompletableFuture<Void> preConditionCheck(IdentificationDataModel input) throws Exception;

    @Override
    protected final CompletableFuture<Void> afterPreConditionCheck(Object input) throws Exception {
        return afterPreConditionCheck(input == null ? null : (IdentificationDataModel) input);
    }

    protected CompletableFuture<Void> afterPreConditionCheck(IdentificationDataModel input) throws Exception {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected final CompletableFuture<Void> beforeCall(Object input) throws Exception {
        return beforeCall(input == null ? null : (IdentificationDataModel) input);
    }

    protected CompletableFuture<Void> beforeCall(IdentificationDataModel input) throws Exception {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected final CompletableFuture<Object> call(Object input) throws Exception {
        CompletableFuture<Boolean> call = call(input == null ? null : (IdentificationDataModel) input);
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

    protected abstract CompletableFuture<Boolean> call(IdentificationDataModel input) throws Exception;

    @Override
    protected final CompletableFuture<Void> afterCall(Object input, Object output) throws Exception {
        return afterCall(input == null ? null : (IdentificationDataModel) input, output == null ? null : (Boolean) output);
    }

    protected CompletableFuture<Void> afterCall(IdentificationDataModel input, Boolean output) throws Exception {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected final CompletableFuture<Void> beforePostConditionCheck(Object input, Object output) throws Exception {
        return beforePostConditionCheck(input == null ? null : (IdentificationDataModel) input, output == null ? null : (Boolean) output);
    }

    protected CompletableFuture<Void> beforePostConditionCheck(IdentificationDataModel input, Boolean output) throws Exception {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected final CompletableFuture<Void> postConditionCheck(Object input, Object output) throws Exception {
        return postConditionCheck(input == null ? null : (IdentificationDataModel) input, output == null ? null : (Boolean) output);
    }

    protected abstract CompletableFuture<Void> postConditionCheck(IdentificationDataModel input, Boolean output) throws Exception;

    @Override
    protected final CompletableFuture<Void> afterPostConditionCheck(Object input, Object output) throws Exception {
        return afterPostConditionCheck(input == null ? null : (IdentificationDataModel) input, output == null ? null : (Boolean) output);
    }

    protected CompletableFuture<Void> afterPostConditionCheck(IdentificationDataModel input, Boolean output) throws Exception {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected final Object getInputFromJsonWork(String inputJson) {
        return IdentificationDataModel.fromJson(inputJson);
    }
}