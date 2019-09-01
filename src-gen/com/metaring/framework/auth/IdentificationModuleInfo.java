package com.metaring.framework.auth;

import com.metaring.framework.functionality.FunctionalityInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdentificationModuleInfo {

    public static final FunctionalityInfo INFO = FunctionalityInfo.create("com.metaring.springbootappexample.service.auth.verifyIdentification", true, false, false, "com.metaring.springbootappexample.service.auth.IdentificationDataModel", "java.lang.Boolean");

    @Bean
    static final com.metaring.springbootappexample.service.auth.VerifyIdentificationFunctionality verifyIdentificationFunctionality() {
        return com.metaring.springbootappexample.service.auth.VerifyIdentificationFunctionality.INSTANCE;
    }
}