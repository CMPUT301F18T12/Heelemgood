package com.example.jerry.healemgood;

import android.location.Location;
import android.support.test.runner.AndroidJUnit4;

import com.example.jerry.healemgood.model.photo.Photo;
import com.example.jerry.healemgood.model.record.PatientRecord;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * PatientRecord Test
 * 1. patientRecordConstructorTest: The class constructors and getters and setters.
 * 2. locationTest: Tests related to the geo-location and body location contained by a patientRecord
 * @author tw
 * @version 1.0.4
 */
@RunWith(AndroidJUnit4.class)
public class PatientRecordTest {
    @Test
    public void patientRecordConstructorTest() {
        // constructor and getters
        int rId = 1;
        String title = "Test";

        PatientRecord record = new PatientRecord(rId, title);

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
        String description = "Test record";
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

    public void locationTest() {
        // geo-location and body location tests
        int rId = 1;
        String title = "Test";

        PatientRecord record = new PatientRecord(rId, title);

        Location location = new Location("");
        location.setLatitude(0.0d);
        location.setLongitude(0.0d);

        record.setGeoLocation(location);
        assertEquals(record.getGeoLocation(), location);

        int body = 1;
        record.setBodyLocation(body);
        assertEquals(record.getBodyLocation(), body);

        // Deletion ?
    }
}
