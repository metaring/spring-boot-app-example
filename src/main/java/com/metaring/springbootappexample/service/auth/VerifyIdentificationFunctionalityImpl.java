package com.metaring.springbootappexample.service.auth;

import java.util.concurrent.CompletableFuture;

public class VerifyIdentificationFunctionalityImpl extends VerifyIdentificationFunctionality {

    @Override
    protected CompletableFuture<Void> preConditionCheck(IdentificationDataModel input) throws Exception {
        return end;
    }

    @Override
    protected CompletableFuture<Boolean> call(IdentificationDataModel input) throws Exception {
        return end(true);
    }

    @Override
    protected CompletableFuture<Void> postConditionCheck(IdentificationDataModel input, Boolean output)
            throws Exception {
        return end;
    }

}
