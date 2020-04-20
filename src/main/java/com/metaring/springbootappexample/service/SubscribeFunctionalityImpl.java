package com.metaring.springbootappexample.service;

import java.util.concurrent.CompletableFuture;

import com.metaring.framework.broadcast.BroadcastController;
import com.metaring.framework.broadcast.Event;
import com.metaring.framework.broadcast.SingleCallback;

public class SubscribeFunctionalityImpl extends SubscribeFunctionality {

    @Override
    protected CompletableFuture<Void> preConditionCheck(String input) throws Exception {
        return end;
    }

    @Override
    protected CompletableFuture<Void> call(String input) throws Exception {
        final String address = getContextData(BroadcastController.BROADCAST_KEY);
        if (address.equals(input)) {
            return end;
        }

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("Loop " + i + ": " + Thread.currentThread().getName());
                try {
                    Thread.sleep(10000);
                    BroadcastController.callback(SingleCallback.create(address, Event.create("push", dataRepresentationFromObject("Server Responding from Subscriber Functionality Impl."))), EXECUTOR);
                } catch (InterruptedException e) {
                    System.out.printf("Interrupted Exception happened %s", e);
                }
            }
            System.out.println("Test Finished for: " + Thread.currentThread().getName());
        }).start();

        return end;
    }

    @Override
    protected CompletableFuture<Void> postConditionCheck(String input) throws Exception {
        return end;
    }
}