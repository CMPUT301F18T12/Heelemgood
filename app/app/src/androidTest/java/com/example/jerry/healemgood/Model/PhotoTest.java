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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.model.photo.Photo;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Photo Test
 * 1. photoConstructorTest: The class constructors and getters and setters.
 * @author tw
 * @version 1.0.3
 */
@RunWith(AndroidJUnit4.class)
public class PhotoTest {
    /**
     * Creates a photoConstructorTest
     *
     */
    @Test
    public void photoConstructorTest() {
        // constructor and getters
        //String path = "/local/icon.png";
        int width = 120;
        int height = 240;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(R.color.colorPrimaryOrange);
        canvas.drawRect(0F, 0F, width, height, paint);

        Photo photo = new Photo(bitmap, "Hello");
        assertEquals("Hello",photo.getLabel());
        assertNotNull(photo.getDate());
    }
}
