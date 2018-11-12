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
        String pid = "ThisisAPid";
        String title = "Test";

        PatientRecord record = new PatientRecord(pid, title);

        assertEquals(record.getTitle(), title);
        assertEquals(record.getpId(), pid);

        // setters and getters
        String pid2 = "ThisisAPid2";
        String title2 = "Test2";
        record.setrId(pid2);
        record.setTitle(title2);
        assertEquals(record.getTitle(), title2);
        assertEquals(record.getpId(), pid2);

        // description
        String description = "Test record";
        record.setDescription(description);
        assertEquals(record.getDescription(), description);

        // photo ArrayList


        // dateCreated
        Date date = new Date();
        record.setCreatedDate(date);
        assertEquals(record.getCreatedDate(), date);

        // isPatientRecord
        assertTrue(record.isPatientRecord());
    }

    public void locationTest() {
        // geo-location and body location tests

    }
}
