package com.metaring.springbootappexample.service;

import com.metaring.framework.Tools;
import com.metaring.framework.type.DataRepresentation;
import com.metaring.framework.GeneratedCoreType;

public class PersonResponseModel implements GeneratedCoreType {

    public static final String FULLY_QUALIFIED_NAME = "com.metaring.springbootappexample.service.personResponse";

    private String firstName;
    private String lastName;

    private PersonResponseModel(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public static PersonResponseModel create(String firstName, String lastName) {
        return new PersonResponseModel(firstName, lastName);
    }

    public static PersonResponseModel fromJson(String jsonString) {

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

        String firstName = null;
        if(dataRepresentation.hasProperty("firstName")) {
            try {
                firstName = dataRepresentation.getText("firstName");
            } catch (Exception e) {
            }
        }

        String lastName = null;
        if(dataRepresentation.hasProperty("lastName")) {
            try {
                lastName = dataRepresentation.getText("lastName");
            } catch (Exception e) {
            }
        }

        PersonResponseModel personResponseModel = create(firstName, lastName);
        return personResponseModel;
    }

    public static PersonResponseModel fromObject(Object object) {

        if(object == null) {
            return null;
        }

        DataRepresentation dataRepresentation = Tools.FACTORY_DATA_REPRESENTATION.fromObject(object);

        String firstName = null;
        if(dataRepresentation.hasProperty("firstName")) {
            try {
                firstName = dataRepresentation.getText("firstName");
            } catch (Exception e) {
            }
        }

        String lastName = null;
        if(dataRepresentation.hasProperty("lastName")) {
            try {
                lastName = dataRepresentation.getText("lastName");
            } catch (Exception e) {
            }
        }

        PersonResponseModel personResponseModel = create(firstName, lastName);
        return personResponseModel;
    }

    public DataRepresentation toDataRepresentation() {
        DataRepresentation dataRepresentation = Tools.FACTORY_DATA_REPRESENTATION.create();
        if (firstName != null) {
            dataRepresentation.add("firstName", firstName);
        }

        if (lastName != null) {
            dataRepresentation.add("lastName", lastName);
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