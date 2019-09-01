package com.metaring.springbootappexample.service.auth;

import com.metaring.framework.functionality.FunctionalityContext;

public class LimitedAccessHelper {

    private static final String ENABLE_DATA = "ENABLE_DATA";

    public static final EnableDataModel getEnableData(FunctionalityContext functionalityContext) {
        return (EnableDataModel) functionalityContext.getData().get(ENABLE_DATA);
    }
}