/*
 *  Class Name: PhotoTest
 *
 *  Version: Version 1.0.3
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.Model;

import android.support.test.runner.AndroidJUnit4;

import com.example.jerry.healemgood.model.photo.Photo;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Photo Test
 * 1. photoConstructorTest: The class constructors and getters and setters.
 * @author tw
 * @version 1.0.3
 */
@RunWith(AndroidJUnit4.class)
public class PhotoTest {
    @Test
    public void photoConstructorTest() {
        // constructor and getters
        String path = "/local/example.png";
        int width = 120;
        int height = 240;

        Photo photo = new Photo(path, width, height);
        assertEquals(path,photo.getPath());
        assertEquals(width, photo.getWidth());
        assertEquals(height, photo.getHeight());

        // setters and getters
        photo.setPath("/new/example2.png");
        photo.setHeight(64);
        photo.setWidth(128);

        assertEquals("/new/example2.png",photo.getPath());
        assertEquals(64, photo.getHeight());
        assertEquals(128, photo.getWidth());
    }
}
