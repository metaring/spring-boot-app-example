package com.metaring.springbootappexample.exception;

public class MetaRingException extends Exception {

    private static final long serialVersionUID = 7146591155753657781L;

    public MetaRingException(String message) {
        super(message);
    }
}