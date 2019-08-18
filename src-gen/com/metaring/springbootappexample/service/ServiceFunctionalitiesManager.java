package com.metaring.springbootappexample.service;

import com.metaring.framework.functionality.FunctionalityInfo;
import com.metaring.framework.functionality.FunctionalitiesManager;
import com.metaring.framework.functionality.GeneratedFunctionalitiesManager;
import com.metaring.framework.functionality.Functionality;
import java.util.concurrent.CompletableFuture;
import java.lang.String;
import com.metaring.springbootappexample.service.PersonResponseModelSeries;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class ServiceFunctionalitiesManager extends FunctionalitiesManager implements GeneratedFunctionalitiesManager {

    public static final FunctionalityInfo GET_PERSONS_BY_LAST_NAME = GetPersonsByLastNameFunctionality.INFO;

    public static final FunctionalityInfo MESSAGE = MessageFunctionality.INFO;

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
        return call(MESSAGE, MessageFunctionality.class, getCallingFunctionality(), string, result -> null);
    }

    public static final CompletableFuture<Void> message(Functionality functionality, String string) {
        return call(MESSAGE, MessageFunctionality.class, functionality, string, result -> null);
    }

    public static final CompletableFuture<Void> mesageFromJson(String stringJson) {
        return callFromJson(MESSAGE, MessageFunctionality.class, getCallingFunctionality(), stringJson, result -> null);
    }

    public static final CompletableFuture<Void> messageFromJson(Functionality callingFunctionality, String stringJson) {
        return callFromJson(MESSAGE, MessageFunctionality.class, callingFunctionality, stringJson, result -> null);
    }

    @Bean
    static final GetPersonsByLastNameFunctionality getPersonsByLastNameFunctionality() {
        return GetPersonsByLastNameFunctionality.INSTANCE;
    }

    @Bean
    static final MessageFunctionality getMessageFunctionality() {
        return MessageFunctionality.INSTANCE;
    }
}
