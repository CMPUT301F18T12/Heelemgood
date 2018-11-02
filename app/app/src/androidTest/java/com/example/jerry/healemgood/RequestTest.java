package com.example.jerry.healemgood;

import android.support.test.runner.AndroidJUnit4;

import com.example.jerry.healemgood.model.photo.Photo;
import com.example.jerry.healemgood.model.request.Request;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Request Test
 * 1. requestConstructorTest: The class constructors and getters and setters.
 * @author tw
 * @version 1.0.1
 */
@RunWith(AndroidJUnit4.class)
public class RequestTest {
    @Test
    public void requestConstructorTest() {
        String id = "Black Jack";
        String msg = "Hello, accept me!";

        Request request = new Request(id, msg);
        assertEquals(request.getMessage(), msg);
        assertEquals(request.getSenderUserId(), id);

        id = "White Jack";
        msg = "I am a real doctor";
        request.setSenderUserId(id);
        request.setMessage(msg);
        assertEquals(request.getMessage(), msg);
        assertEquals(request.getSenderUserId(), id);
    }
}
