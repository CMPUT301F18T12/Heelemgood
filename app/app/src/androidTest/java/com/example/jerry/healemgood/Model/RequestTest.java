/*
 *  Class Name: RequestTest
 *
 *  Version: Version 1.0.3
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.Model;

import android.support.test.runner.AndroidJUnit4;

import com.example.jerry.healemgood.model.request.Request;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Request Test
 * 1. requestConstructorTest: The class constructors and getters and setters.
 * @author tw
 * @version 1.0.3
 */
@RunWith(AndroidJUnit4.class)
public class RequestTest {
    /**
     * Creates a RequestTest
     *
     */
    @Test
    public void requestConstructorTest() {
        // constructor and getters
        String id = "Black Jack";
        String msg = "Hello, accept me!";

        Request request = new Request(id, msg);
        assertEquals(request.getMessage(), msg);
        assertEquals(request.getSenderUserId(), id);

        // setters and getters
        String id2 = "White Jack";
        String msg2 = "I am a real doctor";
        request.setSenderUserId(id2);
        request.setMessage(msg2);
        assertEquals(request.getMessage(), msg2);
        assertEquals(request.getSenderUserId(), id2);
    }
}
