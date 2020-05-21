package com.metaring.springbootappexample.service.roles.callback;

@FunctionalInterface
public interface DatabaseCallback {
    void invoke(Runnable runnable);
}