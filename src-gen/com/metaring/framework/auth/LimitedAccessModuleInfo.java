package com.metaring.framework.auth;

import com.metaring.framework.functionality.FunctionalityInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LimitedAccessModuleInfo {

    public static final FunctionalityInfo INFO = FunctionalityInfo.create("com.metaring.springbootappexample.service.auth.verifyEnable", true, false, false, "com.metaring.springbootappexample.service.auth.EnableDataModel", "java.lang.Boolean");

    @Bean
    static final com.metaring.springbootappexample.service.auth.VerifyEnableFunctionality verifyEnableFunctionality() {
        return com.metaring.springbootappexample.service.auth.VerifyEnableFunctionality.INSTANCE;
    }
}