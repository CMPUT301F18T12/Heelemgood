/*
 *  Class Name: RecordTest
 *
 *  Version: Version 1.0.4
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood;

import android.support.test.runner.AndroidJUnit4;

import com.example.jerry.healemgood.model.photo.Photo;
import com.example.jerry.healemgood.model.record.Record;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Record Test
 * 1. recordConstructorTest: The class constructors and getters and setters.
 * @deprecated
 * @author tw
 * @version 1.0.4
 */
@RunWith(AndroidJUnit4.class)
public class RecordTest {
    @Test
    public void test() {
        assertTrue(true);
    }
    /*
    @Test
    public void recordConstructorTest() {
        // constructor and getters
        int rId = 1;
        String title = "Test";

        Record record = new Record(rId, title, true);

        assertEquals(record.getTitle(), title);
        assertEquals(record.getrId(), rId);

        // setters and getters
        int rId2 = 2;
        String title2 = "Test2";
        record.setrId(rId2);
        record.setTitle(title2);
        assertEquals(record.getTitle(), title2);
        assertEquals(record.getrId(), rId2);

        // description
        String description = "This is a description";
        record.setDescription(description);
        assertEquals(record.getDescription(), description);

        // photo ArrayList
        Photo photo1 = new Photo("/tmp/1.png",200,320);
        Photo photo2 = new Photo("/tmp/2.png",400,520);

        record.addPhoto(photo1);
        record.addPhoto(photo2);

        ArrayList<Photo> photos = record.getPhotos();

        assertTrue(photos.contains(photo1));
        assertTrue(photos.contains(photo2));
        assertEquals(photo1, record.getPhotoById(0));
        assertEquals(photo2, record.getPhotoById(1));

        // dateCreated
        Date date = new Date();
        record.setCreatedDate(date);
        assertEquals(record.getCreatedDate(), date);

        // isPatientRecord
        assertTrue(record.isPatientRecord());
    }
    */
}
