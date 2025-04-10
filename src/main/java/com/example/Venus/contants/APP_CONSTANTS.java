package com.example.Venus.contants;

import jakarta.persistence.PreUpdate;

/*
    @created 02/12/2024 10:49 AM
    @project hrmis
    @author biplaw.chaudhary
*/
public class APP_CONSTANTS {
    public static final String SELECT_QUERY = "select * from ";
    public static final String COMMA = ",";
    public static final String SINGLE_QUOTE = "'";
    public static final String START_SMALL_BRACKET = "(";
    public static final String CLOSE_SMALL_BRACKET = ")";

    public static final String DATA_CREATED = "data.created.success";
    public static final String DATA_FETCHED ="data.fetch.success";
    public static final String DATA_TOGGLED = "data.toggled.success";

    public static final String DATA_UPDATED = "data.updated.success";
    public static final String DATA_DELETED = "data.delete.success";

    public static final String RESOURCE_NOT_FOUND = "resource.not.found";
    public static final String RESOURCE_ALREADY_EXISTS = "resource.already.exists";
    public static final String DATA_NOT_FOUND = "resource.not.found";

    public static final String USER_LOGOUT = "User has logged out. Please reauthenticate.";
    public static final String INVALID_TOKEN = "Invalid JWT token.";
    public static final String ERROR_OCCUR = "An error occurred: ";


    public static final String BAD_REQUEST = "400";
    public static final String UNAUTHORIZED = "401";
    public static final String SERVICE_NAME = "vbm-montessori";


}
