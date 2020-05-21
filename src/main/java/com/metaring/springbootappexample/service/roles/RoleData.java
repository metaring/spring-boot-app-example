package com.metaring.springbootappexample.service.roles;

import com.metaring.framework.GeneratedCoreType;
import com.metaring.framework.Tools;
import com.metaring.framework.type.DataRepresentation;

public class RoleData implements GeneratedCoreType {

    public static final String FULLY_QUALIFIED_NAME = "com.metaring.springbootappexample.service.roles.roleData";

    private String roleId;


    private RoleData(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return this.roleId;
    }


    public static RoleData create(String roleId) {
        return new RoleData(roleId);
    }

    public static RoleData fromJson(String jsonString) {

        if (jsonString == null) {
            return null;
        }

        jsonString = jsonString.trim();
        if (jsonString.isEmpty()) {
            return null;
        }

        if (jsonString.equalsIgnoreCase("null")) {
            return null;
        }

        DataRepresentation dataRepresentation = Tools.FACTORY_DATA_REPRESENTATION.fromJson(jsonString);

        String roleId = null;
        if (dataRepresentation.hasProperty("roleId")) {
            try {
                roleId = dataRepresentation.getText("roleId");
            } catch (Exception e) {
            }
        }


        RoleData roleData = create(roleId);
        return roleData;
    }

    public static RoleData fromObject(Object object) {

        if (object == null) {
            return null;
        }

        DataRepresentation dataRepresentation = Tools.FACTORY_DATA_REPRESENTATION.fromObject(object);

        String roleId = null;
        if (dataRepresentation.hasProperty("roleId")) {
            try {
                roleId = dataRepresentation.getText("roleId");
            } catch (Exception e) {
            }
        }


        RoleData roleData = create(roleId);
        return roleData;
    }

    public DataRepresentation toDataRepresentation() {
        DataRepresentation dataRepresentation = Tools.FACTORY_DATA_REPRESENTATION.create();
        if (roleId != null) {
            dataRepresentation.add("id", roleId);
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