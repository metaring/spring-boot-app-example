package com.metaring.springbootappexample.service.roles;

import com.metaring.framework.functionality.FunctionalitiesManager;
import com.metaring.framework.functionality.Functionality;
import com.metaring.framework.functionality.FunctionalityInfo;
import com.metaring.framework.functionality.GeneratedFunctionalitiesManager;
import com.metaring.framework.type.DataRepresentation;

import java.util.concurrent.CompletableFuture;

public class RoleFunctionalitiesManager extends FunctionalitiesManager implements GeneratedFunctionalitiesManager {

    public static final FunctionalityInfo GET_USER_ROLE = GetUserRoleFunctionality.INFO;

    public static final CompletableFuture<RoleData> getUserRole(String string) {
        return call(GET_USER_ROLE, GetUserRoleFunctionality.class, getCallingFunctionality(), string, result -> result.as(RoleData.class));
    }

    public static final CompletableFuture<RoleData> getUserRole(Functionality functionality, String string) {
        return call(GET_USER_ROLE, GetUserRoleFunctionality.class, functionality, string, result -> result.as(RoleData.class));
    }

    public static final CompletableFuture<RoleData> getUserRoleFromJson(String stringJson) {
        return callFromJson(GET_USER_ROLE, GetUserRoleFunctionality.class, getCallingFunctionality(), stringJson, result -> result.as(RoleData.class));
    }

    public static final CompletableFuture<RoleData> getUserRoleFromJson(Functionality callingFunctionality, String stringJson) {
        return callFromJson(GET_USER_ROLE, GetUserRoleFunctionality.class, callingFunctionality, stringJson, result -> result.as(RoleData.class));
    }
}
