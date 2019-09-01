package com.metaring.springbootappexample.service.auth;

import com.metaring.framework.functionality.FunctionalityContext;

public class IdentificationHelper {

    private static final String IDENTIFICATION_DATA = "IDENTIFICATION_DATA";

    public static final IdentificationDataModel getIdentificationData(FunctionalityContext functionalityContext) {
        return (IdentificationDataModel) functionalityContext.getData().get(IDENTIFICATION_DATA);
    }
}