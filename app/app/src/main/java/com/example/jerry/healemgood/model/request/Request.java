package com.example.jerry.healemgood.model.request;

import com.example.jerry.healemgood.model.user.CareProvider;

public class Request {
    private String senderUserId;
    private String message;

    public Request(String senderUserId, String message) {
        this.senderUserId = senderUserId;
        this.message = message;
    }

    /* Get the sender user id*/
    public String getSenderUserId() {
        return senderUserId;
    }

    /* Set the sender*/
    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }

    /* Get the message*/
    public String getMessage() {
        return message;
    }

    /*Set the message*/
    public void setMessage(String message) {
        this.message = message;
    }
}
