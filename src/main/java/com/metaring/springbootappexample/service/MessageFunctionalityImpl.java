package com.metaring.springbootappexample.service;

import java.util.concurrent.CompletableFuture;

import com.metaring.framework.broadcast.BroadcastFunctionalitiesManager;
import com.metaring.framework.broadcast.Event;
import com.metaring.framework.broadcast.SingleCallback;

public class MessageFunctionalityImpl extends MessageFunctionality {

    @Override
    protected CompletableFuture<Void> preConditionCheck(String input) throws Exception {
        return end;
    }

    @Override
    protected CompletableFuture<Void> call(String input) throws Exception {
        return BroadcastFunctionalitiesManager.contact(SingleCallback.create("message", (Event.create("message", dataRepresentationFromObject(input)))));
    }

    @Override
    protected CompletableFuture<Void> postConditionCheck(String input) throws Exception {
        return end;
    }

}
