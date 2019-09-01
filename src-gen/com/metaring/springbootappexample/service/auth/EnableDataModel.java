package com.metaring.springbootappexample.service.auth;

import com.metaring.framework.Tools;
import com.metaring.framework.type.DataRepresentation;
import com.metaring.framework.GeneratedCoreType;

public class EnableDataModel implements GeneratedCoreType {

    public static final String FULLY_QUALIFIED_NAME = "com.metaring.springbootappexample.service.auth.enableData";

    private String role;
    private String functionalityName;
    private DataRepresentation functionalityParam;

    private EnableDataModel(String role, String functionalityName, DataRepresentation functionalityParam) {
        this.role = role;
        this.functionalityName = functionalityName;
        this.functionalityParam = functionalityParam;
    }

    public String getRole() {
        return this.role;
    }

    public String getFunctionalityName() {
        return this.functionalityName;
    }

    public DataRepresentation getFunctionalityParam() {
        return this.functionalityParam;
    }

    public static EnableDataModel create(String role, String functionalityName, DataRepresentation functionalityParam) {
        return new EnableDataModel(role, functionalityName, functionalityParam);
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

        String functionalityName = null;
        if(dataRepresentation.hasProperty("functionalityName")) {
            try {
                functionalityName = dataRepresentation.getText("functionalityName");
            } catch (Exception e) {
            }
        }

        DataRepresentation functionalityParam = null;
        if(dataRepresentation.hasProperty("functionalityParam")) {
            try {
                functionalityParam = dataRepresentation.get("functionalityParam");
            } catch (Exception e) {
            }
        }

        EnableDataModel enableDataModel = create(role, functionalityName, functionalityParam);
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

        String functionalityName = null;
        if(dataRepresentation.hasProperty("functionalityName")) {
            try {
                functionalityName = dataRepresentation.getText("functionalityName");
            } catch (Exception e) {
            }
        }

        DataRepresentation functionalityParam = null;
        if(dataRepresentation.hasProperty("functionalityParam")) {
            try {
                functionalityParam = dataRepresentation.get("functionalityParam");
            } catch (Exception e) {
            }
        }

        EnableDataModel enableDataModel = create(role, functionalityName, functionalityParam);
        return enableDataModel;
    }

    public DataRepresentation toDataRepresentation() {
        DataRepresentation dataRepresentation = Tools.FACTORY_DATA_REPRESENTATION.create();
        if (role != null) {
            dataRepresentation.add("role", role);
        }

        if (functionalityName != null) {
            dataRepresentation.add("functionalityName", functionalityName);
        }

        if (functionalityParam != null) {
            dataRepresentation.add("functionalityParam", functionalityParam);
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