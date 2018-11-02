package com.example.jerry.healemgood;

import android.support.test.runner.AndroidJUnit4;

import com.example.jerry.healemgood.model.photo.Photo;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Problem Test
 * 1. problemConstructorTest: The class constructors and getters and setters
 * 2. problemRecordTest: Tests related with records contained in a problem
 * @author tw
 * @version 1.0.0
 */
@RunWith(AndroidJUnit4.class)
public class ProblemTest {
    @Test
    public void problemConstructorTest() {
        int pid = 1;
        String title = "Test";
        Date date = new Date();

        Problem problem = new Problem(pid, title, date);
        assertEquals(pid,problem.getpId());
        assertEquals(title,problem.getTitle());
        assertEquals(date,problem.getCreatedDate());

        pid = 2;
        title = "Test2";
        date = new Date();
        problem.setpId(pid);
        problem.setTitle(title);
        problem.setCreatedDate(date);
        assertEquals(pid,problem.getpId());
        assertEquals(title,problem.getTitle());
        assertEquals(date,problem.getCreatedDate());
    }

    @Test
    public void problemRecordTest() {
        int pid = 1;
        String title = "Test";
        Date date = new Date();
        Problem problem = new Problem(pid, title, date);

        Record record = new Record(pid,title,true);
        Record record2 = new Record(pid+1,"Test2",false);
        problem.addRecord(record);
        problem.addRecord(record2);

        ArrayList<Record> records = problem.getRecords();
        assertTrue(records.contains(record));
        assertTrue(records.contains(record2));

        assertEquals(record2, problem.getRecordByIndex(1));

        problem.deleteRecord(0);
        assertEquals(record2, problem.getRecordByIndex(0));
    }
}