package com.example.jerry.healemgood;

import android.support.test.runner.AndroidJUnit4;

import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.CareProviderRecord;
import com.example.jerry.healemgood.model.record.PatientRecord;
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
 * @version 1.0.3
 */
@RunWith(AndroidJUnit4.class)
public class ProblemTest {
    @Test
    public void problemConstructorTest() {
        // constructor and getters
        int pid = 1;
        String title = "Test";
        Date date = new Date();

        Problem problem = new Problem(title, date,":sdsdasdasd");
        assertEquals(pid,problem.getpId());
        assertEquals(title,problem.getTitle());
        assertEquals(date,problem.getCreatedDate());

        // setters and getters
        int pid2 = 2;
        String title2 = "Test2";
        Date date2 = new Date();
        problem.setTitle(title2);
        problem.setCreatedDate(date2);
        assertEquals(pid2,problem.getpId());
        assertEquals(title2,problem.getTitle());
        assertEquals(date2,problem.getCreatedDate());
    }


}
