package com.metaring.springbootappexample.service.roles;

import com.metaring.framework.exception.ManagedException;

public final class RoleLimitationException extends ManagedException {

    private static final long serialVersionUID = -550335134731027410L;

    public RoleLimitationException() {
        super();
    }
}