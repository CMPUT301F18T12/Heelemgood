package com.example.jerry.healemgood.model.request;

import com.example.jerry.healemgood.model.user.CareProvider;

/**
 * This is the class for request
 * A request is a message that care provider sends to patients in order to get the permission to treat the patient
 * Patients can accept it or reject a request
 * */

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
