/*
 *  Class Name: Request
 *
 *  Version: Version 1.0
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.model.request;

import com.example.jerry.healemgood.model.user.CareProvider;

/**
 * Represents a Request
 *
 * @author xiacijie
 * @version 1.0
 * @since 1.0
 */

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
