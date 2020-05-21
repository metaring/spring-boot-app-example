package com.metaring.springbootappexample.service.roles;

import com.metaring.framework.sensitiveDataPersistence.SensitiveDataPersistenceFunctionalitiesManager;
import com.metaring.framework.type.DataRepresentation;
import com.metaring.framework.util.ObjectUtil;

import java.util.concurrent.CompletableFuture;

import static com.ea.async.Async.await;

class GetUserRoleFunctionalityImpl extends GetUserRoleFunctionality {

    @Override
    protected CompletableFuture<Void> preConditionCheck(String input) throws Exception {
        return end;
    }

    @Override
    protected CompletableFuture<RoleData> call(String userId) throws Exception {
        System.out.println("GetUserRoleFunctionalityImpl invoked with userId: " + userId);
        DataRepresentation result = await(SensitiveDataPersistenceFunctionalitiesManager.query("db.users.find({},{})"));
        DataRepresentation result1 = await(SensitiveDataPersistenceFunctionalitiesManager.query("db.ff4j_properties.find({},{})"));
        if (ObjectUtil.isNullOrEmpty(result)) {
            throw new RoleLimitationException();
        }
        return end(RoleData.create("ROLE_LICENSE"));
    }

    @Override
    protected CompletableFuture<Void> postConditionCheck(String input, String output) throws Exception {
        return end;
    }
}