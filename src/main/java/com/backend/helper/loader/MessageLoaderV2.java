package com.backend.helper.loader;

import com.backend.consts.RESTMessages;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.backend.consts.Constants.BUNDLE_MESSAGES;

/**
 * Utility class for loading messages from a resource bundle.
 *
 * This class provides methods to retrieve messages based on keys from a resource bundle. It uses the provided
 * `RESTMessages` enum to identify the keys and fetches the corresponding messages from the "messages" resource bundle.
 */
@Default
@RequestScoped
public class MessageLoaderV2 {

    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_MESSAGES, Locale.ENGLISH);

    /**
     * Retrieves the message associated with the given key from the resource bundle.
     *
     * @param key The key representing the message in the resource bundle
     * @return The message associated with the key, or an empty string if the key is not found
     */
    public String get(RESTMessages key){
        return bundle.getString(String.valueOf(key));
    }
}
