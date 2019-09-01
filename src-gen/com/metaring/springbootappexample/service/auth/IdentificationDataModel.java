package com.metaring.springbootappexample.service.auth;

import com.metaring.framework.Tools;
import com.metaring.framework.type.DataRepresentation;
import com.metaring.framework.GeneratedCoreType;

public class IdentificationDataModel implements GeneratedCoreType {

    public static final String FULLY_QUALIFIED_NAME = "com.metaring.springbootappexample.service.auth.identificationData";

    private String token;

    private IdentificationDataModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public static IdentificationDataModel create(String token) {
        return new IdentificationDataModel(token);
    }

    public static IdentificationDataModel fromJson(String jsonString) {

        if(jsonString == null) {
            return null;
        }

        jsonString = jsonString.trim();
        if(jsonString.isEmpty()) {
            return null;
        }

        if(jsonString.equalsIgnoreCase("null")) {
            return null;
        }

        DataRepresentation dataRepresentation = Tools.FACTORY_DATA_REPRESENTATION.fromJson(jsonString);

        String token = null;
        if(dataRepresentation.hasProperty("token")) {
            try {
                token = dataRepresentation.getText("token");
            } catch (Exception e) {
            }
        }

        IdentificationDataModel identificationDataModel = create(token);
        return identificationDataModel;
    }

    public static IdentificationDataModel fromObject(Object object) {

        if(object == null) {
            return null;
        }

        DataRepresentation dataRepresentation = Tools.FACTORY_DATA_REPRESENTATION.fromObject(object);

        String token = null;
        if(dataRepresentation.hasProperty("token")) {
            try {
                token = dataRepresentation.getText("token");
            } catch (Exception e) {
            }
        }

        IdentificationDataModel identificationDataModel = create(token);
        return identificationDataModel;
    }

    public DataRepresentation toDataRepresentation() {
        DataRepresentation dataRepresentation = Tools.FACTORY_DATA_REPRESENTATION.create();
        if (token != null) {
            dataRepresentation.add("token", token);
        }

        return dataRepresentation;
    }

    @Override
    public String toJson() {
        return toDataRepresentation().toJson();
    }

    @Override
    public String toString() {
        return this.toJson();
    }
}