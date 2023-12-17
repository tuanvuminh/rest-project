package com.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class RESTResponse {

    @JsonProperty("messageText")
    private String messageText = null;

    @JsonProperty("data")
    List<Employee> data = null;

    public void addDataItem(Employee dataItem) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.add(dataItem);
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public List<Employee> getData() {
        return data;
    }

    public void setData(List<Employee> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RESTResponse {" +
                "messageText = \"" + messageText +
                "\", data = " + data +
                '}';
    }

    public String toErrorResponse(){
        return "ERROR: " + messageText;
    }
}
