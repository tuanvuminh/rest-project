package com.backend.helper.loader;

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

    SUCCESSFUL_SEARCH_EMPLOYEES,
    SUCCESSFUL_DETAIL_EMPLOYEE,
    SUCCESSFUL_CREATION_EMPLOYEE,
    SUCCESSFUL_UPDATE_EMPLOYEE,
    SUCCESSFUL_DELETE,
    UNSUCCESSFUL_SEARCH_EMPLOYEES,
    UNSUCCESSFUL_DETAIL_EMPLOYEE,
    UNSUCCESSFUL_CREATION_EMPLOYEE,
    UNSUCCESSFUL_UPDATE_EMPLOYEE,
    UNSUCCESSFUL_DELETE;

    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_MESSAGES, Locale.ENGLISH);

    /**
     * Retrieves the message associated with the enum from the resource bundle.
     *
     * @return The message associated with the enum constant.
     */
    public String getMessage() {
        return bundle.getString(this.name());
    }
}
