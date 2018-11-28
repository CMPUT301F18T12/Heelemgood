/*
 *  Class Name: Photo
 *
 *  Version: Version 1.0
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.model.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.jerry.healemgood.utils.LengthOutOfBoundException;

import java.io.ByteArrayOutputStream;

/**
 * Represents a photo
 *
 * @author xiacijie
 * @version 1.0
 * @since 1.0
 */

public class Photo {

    private String image;
    private String label;

    /**
     * Add photo, then converting it into base64 format and add it to the photo
     *
     * See : https://stackoverflow.com/questions/4830711/how-to-convert-a-image-into-base64-string
     *
     */
    public Photo(Bitmap src,String label) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        this.image = encodedImage;
        this.label = label;
    }

    /**
     *  Get photo collection
     *
     *  @See https://stackoverflow.com/questions/4837110/how-to-convert-a-base64-string-into-a-bitmap-image-to-show-it-in-a-imageview
     *
     * @return decodeByte
     */
    public Bitmap getPhoto(){
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    /**
     * get label
     *
     */
    public String getLabel(){
        return label;
    }
}
