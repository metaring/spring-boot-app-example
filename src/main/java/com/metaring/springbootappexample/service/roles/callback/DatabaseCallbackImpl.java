package com.metaring.springbootappexample.service.roles.callback;

public class DatabaseCallbackImpl implements DatabaseCallback {
    @Override
    public void invoke(Runnable runnable) {
        runnable.run();
    }
}