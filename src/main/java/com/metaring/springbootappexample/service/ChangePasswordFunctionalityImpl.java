package com.metaring.springbootappexample.service;

import java.util.concurrent.CompletableFuture;

public class ChangePasswordFunctionalityImpl extends ChangePasswordFunctionality {

    @Override
    protected CompletableFuture<Void> preConditionCheck(String input) throws Exception {
        return end;
    }

    @Override
    protected CompletableFuture<Boolean> call(String input) throws Exception {
        System.out.println("If you can read this, you passed the VerifyIdentification and VerifyEnable");
        return end(true);
    }

    @Override
    protected CompletableFuture<Void> postConditionCheck(String input, Boolean output) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
