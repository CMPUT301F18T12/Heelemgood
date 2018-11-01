package com.example.jerry.healemgood.model.request;

import com.example.jerry.healemgood.model.user.CareProvider;

public class Request {
    private CareProvider sender;
    private String message;

    public Request(CareProvider sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    /* Get the sender*/
    public CareProvider getSender() {
        return sender;
    }

    /* Set the sender*/
    public void setSender(CareProvider sender) {
        this.sender = sender;
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
