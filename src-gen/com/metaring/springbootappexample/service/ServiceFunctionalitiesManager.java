package com.metaring.springbootappexample.service;

import com.metaring.framework.functionality.FunctionalityInfo;
import com.metaring.framework.functionality.FunctionalitiesManager;
import com.metaring.framework.functionality.GeneratedFunctionalitiesManager;
import com.metaring.framework.functionality.Functionality;
import java.util.concurrent.CompletableFuture;
import java.lang.String;
import java.lang.Boolean;
import com.metaring.springbootappexample.service.PersonResponseModelSeries;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class ServiceFunctionalitiesManager extends FunctionalitiesManager implements GeneratedFunctionalitiesManager {

    public static final FunctionalityInfo CHANGE_PASSWORD = ChangePasswordFunctionality.INFO;

    public static final FunctionalityInfo GET_PERSONS_BY_LAST_NAME = GetPersonsByLastNameFunctionality.INFO;

    public static final FunctionalityInfo MESSAGE = MessageFunctionality.INFO;

    public static final FunctionalityInfo SUBSCRIBE = SubscribeFunctionality.INFO;

    public static final CompletableFuture<Boolean> changePassword(String string) {
        return call(CHANGE_PASSWORD, ChangePasswordFunctionality.class, getCallingFunctionality(), string, result -> result.asTruth());
    }

    public static final CompletableFuture<Boolean> changePassword(Functionality functionality, String string) {
        return call(CHANGE_PASSWORD, ChangePasswordFunctionality.class, functionality, string, result -> result.asTruth());
    }

    public static final CompletableFuture<Boolean> changePasswordFromJson(String stringJson) {
        return callFromJson(CHANGE_PASSWORD, ChangePasswordFunctionality.class, getCallingFunctionality(), stringJson, result -> result.asTruth());
    }

    public static final CompletableFuture<Boolean> changePasswordFromJson(Functionality callingFunctionality, String stringJson) {
        return callFromJson(CHANGE_PASSWORD, ChangePasswordFunctionality.class, callingFunctionality, stringJson, result -> result.asTruth());
    }

    public static final CompletableFuture<PersonResponseModelSeries> getPersonsByLastName(String string) {
        return call(GET_PERSONS_BY_LAST_NAME, GetPersonsByLastNameFunctionality.class, getCallingFunctionality(), string, result -> result.as(PersonResponseModelSeries.class));
    }

    public static final CompletableFuture<PersonResponseModelSeries> getPersonsByLastName(Functionality functionality, String string) {
        return call(GET_PERSONS_BY_LAST_NAME, GetPersonsByLastNameFunctionality.class, functionality, string, result -> result.as(PersonResponseModelSeries.class));
    }

    public static final CompletableFuture<PersonResponseModelSeries> getPersonsByLastNameFromJson(String stringJson) {
        return callFromJson(GET_PERSONS_BY_LAST_NAME, GetPersonsByLastNameFunctionality.class, getCallingFunctionality(), stringJson, result -> result.as(PersonResponseModelSeries.class));
    }

    public static final CompletableFuture<PersonResponseModelSeries> getPersonsByLastNameFromJson(Functionality callingFunctionality, String stringJson) {
        return callFromJson(GET_PERSONS_BY_LAST_NAME, GetPersonsByLastNameFunctionality.class, callingFunctionality, stringJson, result -> result.as(PersonResponseModelSeries.class));
    }

    public static final CompletableFuture<Void> message(String string) {
        return call(MESSAGE, MessageFunctionality.class, getCallingFunctionality(), string, null);
    }

    public static final CompletableFuture<Void> message(Functionality functionality, String string) {
        return call(MESSAGE, MessageFunctionality.class, functionality, string, null);
    }

    public static final CompletableFuture<Void> messageFromJson(String stringJson) {
        return callFromJson(MESSAGE, MessageFunctionality.class, getCallingFunctionality(), stringJson, null);
    }

    public static final CompletableFuture<Void> messageFromJson(Functionality callingFunctionality, String stringJson) {
        return callFromJson(MESSAGE, MessageFunctionality.class, callingFunctionality, stringJson, null);
    }

    public static final CompletableFuture<Void> subscribe(String string) {
        return call(SUBSCRIBE, SubscribeFunctionality.class, getCallingFunctionality(), string, null);
    }

    public static final CompletableFuture<Void> subscribe(Functionality functionality, String string) {
        return call(SUBSCRIBE, SubscribeFunctionality.class, functionality, string, null);
    }

    public static final CompletableFuture<Void> subscribeFromJson(String stringJson) {
        return callFromJson(SUBSCRIBE, SubscribeFunctionality.class, getCallingFunctionality(), stringJson, null);
    }

    public static final CompletableFuture<Void> subscribeFromJson(Functionality callingFunctionality, String stringJson) {
        return callFromJson(SUBSCRIBE, SubscribeFunctionality.class, callingFunctionality, stringJson, null);
    }

    @Bean
    static final ChangePasswordFunctionality changePasswordFunctionality() {
        return ChangePasswordFunctionality.INSTANCE;
    }

    @Bean
    static final GetPersonsByLastNameFunctionality getPersonsByLastNameFunctionality() {
        return GetPersonsByLastNameFunctionality.INSTANCE;
    }

    @Bean
    static final MessageFunctionality messageFunctionality() {
        return MessageFunctionality.INSTANCE;
    }

    @Bean
    static final SubscribeFunctionality subscribeFunctionality() {
        return SubscribeFunctionality.INSTANCE;
    }

}
