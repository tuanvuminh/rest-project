package com.backend.helper;

import com.backend.consts.RESTMessages;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;

import java.util.Locale;
import java.util.ResourceBundle;

@Default
@RequestScoped
public class MessageLoader {

    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);

    public String get(RESTMessages key){
        return bundle.getString(String.valueOf(key));
    }
}
