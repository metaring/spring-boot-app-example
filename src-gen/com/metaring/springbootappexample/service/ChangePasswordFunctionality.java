package com.metaring.springbootappexample.service;

import java.util.concurrent.CompletableFuture;
import com.metaring.framework.functionality.AbstractFunctionality;
import com.metaring.framework.functionality.GeneratedFunctionality;
import com.metaring.framework.functionality.FunctionalityInfo;

abstract class ChangePasswordFunctionality extends AbstractFunctionality implements GeneratedFunctionality {

    static final FunctionalityInfo INFO = FunctionalityInfo.create("com.metaring.springbootappexample.service.changePassword", false, false, true, "java.lang.String", "java.lang.Boolean");

    static final ChangePasswordFunctionality INSTANCE = new ChangePasswordFunctionalityImpl();

    protected ChangePasswordFunctionality() {
        super(INFO, Boolean.class);
    }

    @Override
    protected final CompletableFuture<Void> beforePreConditionCheck(Object input) throws Exception {
        CompletableFuture<Void> response = beforePreConditionCheck(input == null ? null : (String) input);
        return response == null ? end : response;
    }

    protected CompletableFuture<Void> beforePreConditionCheck(String input) throws Exception {
        return end;
    }

    @Override
    protected final CompletableFuture<Void> preConditionCheck(Object input) throws Exception {
        CompletableFuture<Void> response = preConditionCheck(input == null ? null : (String) input);
        return response == null ? end : response;
    }

    protected abstract CompletableFuture<Void> preConditionCheck(String input) throws Exception;

    @Override
    protected final CompletableFuture<Void> afterPreConditionCheck(Object input) throws Exception {
        CompletableFuture<Void> response = afterPreConditionCheck(input == null ? null : (String) input);
        return response == null ? end : response;
    }

    protected CompletableFuture<Void> afterPreConditionCheck(String input) throws Exception {
        return end;
    }

    @Override
    protected final CompletableFuture<Void> beforeCall(Object input) throws Exception {
        CompletableFuture<Void> response = beforeCall(input == null ? null : (String) input);
        return response == null ? end : response;
    }

    protected CompletableFuture<Void> beforeCall(String input) throws Exception {
        return end;
    }

    @Override
    protected final CompletableFuture<Object> call(Object input) throws Exception {
        CompletableFuture<Boolean> call = call((String) input);
        if(call == null) {
            return end(null);
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

    protected abstract CompletableFuture<Boolean> call(String input) throws Exception;

    @Override
    protected final CompletableFuture<Void> afterCall(Object input, Object output) throws Exception {
        CompletableFuture<Void> response = afterCall(input == null ? null : (String) input, output == null ? null : (Boolean) output);
        return response == null ? end : response;
    }

    protected CompletableFuture<Void> afterCall(String input, Boolean output) throws Exception {
        return end;
    }

    @Override
    protected final CompletableFuture<Void> beforePostConditionCheck(Object input, Object output) throws Exception {
        CompletableFuture<Void> response = beforePostConditionCheck(input == null ? null : (String) input, output == null ? null : (Boolean) output);
        return response == null ? end : response;
    }

    protected CompletableFuture<Void> beforePostConditionCheck(String input, Boolean output) throws Exception {
        return end;
    }

    @Override
    protected final CompletableFuture<Void> postConditionCheck(Object input, Object output) throws Exception {
        CompletableFuture<Void> response = postConditionCheck(input == null ? null : (String) input, output == null ? null : (Boolean) output);
        return response == null ? end : response;
    }

    protected abstract CompletableFuture<Void> postConditionCheck(String input, Boolean output) throws Exception;

    @Override
    protected final CompletableFuture<Void> afterPostConditionCheck(Object input, Object output) throws Exception {
        CompletableFuture<Void> response = afterPostConditionCheck(input == null ? null : (String) input, output == null ? null : (Boolean) output);
        return response == null ? end : response;
    }

    protected CompletableFuture<Void> afterPostConditionCheck(String input, Boolean output) throws Exception {
        return end;
    }

    @Override
    protected final Object getInputFromJsonWork(String inputJson) {
        return inputJson == null ? null : inputJson.trim().isEmpty() ? null : inputJson.equals("null") ? null : inputJson.substring(1, inputJson.length() - 1);
    }
}