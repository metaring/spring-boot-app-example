package com.metaring.springbootappexample.service.auth;

import com.metaring.framework.Tools;
import com.metaring.framework.type.DataRepresentation;
import com.metaring.framework.GeneratedCoreType;

public class EnableDataModel implements GeneratedCoreType {

    public static final String FULLY_QUALIFIED_NAME = "com.metaring.springbootappexample.service.auth.enableData";

    private String role;

    private EnableDataModel(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public static EnableDataModel create(String role) {
        return new EnableDataModel(role);
    }

    public static EnableDataModel fromJson(String jsonString) {

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

        String role = null;
        if(dataRepresentation.hasProperty("role")) {
            try {
                role = dataRepresentation.getText("role");
            } catch (Exception e) {
            }
        }

        EnableDataModel enableDataModel = create(role);
        return enableDataModel;
    }

    public static EnableDataModel fromObject(Object object) {

        if(object == null) {
            return null;
        }

        DataRepresentation dataRepresentation = Tools.FACTORY_DATA_REPRESENTATION.fromObject(object);

        String role = null;
        if(dataRepresentation.hasProperty("role")) {
            try {
                role = dataRepresentation.getText("role");
            } catch (Exception e) {
            }
        }

        EnableDataModel enableDataModel = create(role);
        return enableDataModel;
    }

    public DataRepresentation toDataRepresentation() {
        DataRepresentation dataRepresentation = Tools.FACTORY_DATA_REPRESENTATION.create();
        if (role != null) {
            dataRepresentation.add("role", role);
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