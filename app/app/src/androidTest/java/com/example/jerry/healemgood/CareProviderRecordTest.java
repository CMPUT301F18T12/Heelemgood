package com.example.jerry.healemgood;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import com.example.jerry.healemgood.model.photo.Photo;
import com.example.jerry.healemgood.model.record.CareProviderRecord;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * CareProviderRecord Test
 * 1. CareProviderRecordConstructorTest: The class constructors and getters and setters.
 * @author tw
 * @version 1.0.4
 */
@RunWith(AndroidJUnit4.class)
public class CareProviderRecordTest {
    @Test
    public void careProviderRecordConstructorTest() {
        // constructor and getters
        String pid = "sdasdsafasdasdgcx";
        String title = "Test";

        CareProviderRecord record = new CareProviderRecord(pid, title);

        assertEquals(record.getTitle(), title);
        assertEquals(record.getpId(), pid);

        // setters
        String title2 = "Test2";
        record.setTitle(title2);
        assertEquals(record.getTitle(), title2);

        // description
        String description = "Test care provider record";
        record.setDescription(description);
        assertEquals(record.getDescription(), description);

        // photo ArrayList
        String imgPath = "/tmp/1.png";
        String imgPath2 = "/tmp/2.png";
        record.addPhoto(imgPath);
        record.addPhoto(imgPath2);

        ArrayList<Bitmap> photos = record.getPhotos();

        assertEquals(photos.size(),2);

        // dateCreated
        Date date = new Date();
        record.setCreatedDate(date);
        assertEquals(record.getCreatedDate(), date);

        // isPatientRecord
        assertFalse(record.isPatientRecord());
    }

}
