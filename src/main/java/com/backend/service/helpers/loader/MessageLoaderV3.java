package com.backend.service.helpers.loader;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.backend.consts.Constants.BUNDLE_MESSAGES;

/**
 * Enum representing messages for REST operations.
 *
 * This enum contains constant values representing messages for successful and unsuccessful outcomes
 * of various REST operations related to employee management.
 */
public enum MessageLoaderV3 {
    SUCCESSFUL_SEARCH_EMPLOYEES("List of employees."),
    SUCCESSFUL_DETAIL_EMPLOYEE("The employee was found by the given ID."),
    SUCCESSFUL_CREATION_EMPLOYEE("Employee was successfully created."),
    SUCCESSFUL_UPDATE_EMPLOYEE("Employee was successfully updated."),
    SUCCESSFUL_DELETE("The specified resource data was successfully deleted."),
    UNSUCCESSFUL_SEARCH_EMPLOYEES("Could not get list of employees."),
    UNSUCCESSFUL_DETAIL_EMPLOYEE("The employee was not found by the given ID."),
    UNSUCCESSFUL_CREATION_EMPLOYEE("Employee was not created."),
    UNSUCCESSFUL_UPDATE_EMPLOYEE("Employee was not updated."),
    UNSUCCESSFUL_DELETE("The specified resource data could not be deleted."),

    ;
    MessageLoaderV3() {
    }

    MessageLoaderV3(String description) {
    }

    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_MESSAGES, Locale.ENGLISH);

    /**
     * Retrieves the message associated with the enum from the resource bundle.
     *
     * @return The message associated with the enum constant.
     */
    public String get() {
        return bundle.getString(this.name());
    }
}
