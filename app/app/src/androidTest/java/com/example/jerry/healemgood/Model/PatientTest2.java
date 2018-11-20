/*
 *  Class Name: PatientTest2
 *
 *  Version: Version 1.0.3
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.Model;

import android.support.test.runner.AndroidJUnit4;

import com.example.jerry.healemgood.model.request.Request;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Patient Test
 * 1. patientConstructorTest: The class constructors and getters and setters.
 * 2. requestsTest: Tests related to the requests contained in a patient
 * 3. problemsTest: Tests related to the problems contained in a patient
 * 4. careProvidersTest: Tests related to the careProviders contained in a patient
 * @author tw
 * @version 1.0.3
 */
@RunWith(AndroidJUnit4.class)
public class PatientTest2 {
    @Test
    public void userConstructorTest() {
        // constructor and getters
        String userId = "jackb0";
        String password = "12345678";
        String fullName = "Black Jack";
        String phoneNum = "7806425671";
        String email = "jack@realmail.com";
        Date birthday = new Date();
        char gender = 'M';

        try{
            Patient user = new Patient(userId, password, fullName, phoneNum, email, birthday, gender);
            assertEquals(user.getUserId(), userId);
            assertEquals(user.getPassword(), password);
            assertEquals(user.getFullName(), fullName);
            assertEquals(user.getPhoneNum(), phoneNum);
            assertEquals(user.getEmail(), email);
            assertEquals(user.getBirthday(), birthday);
            assertEquals(user.getGender(), gender);

            // setters and getters
            String userId2 = "jackb1";
            String password2 = "12345677";
            String fullName2 = "White Jack";
            String phoneNum2 = "7806425672";
            String email2 = "jack2@realmail.com";
            Date birthday2 = new Date();
            char gender2 = 'F';

            user.setUserId(userId2);
            user.setPassword(password2);
            user.setFullName(fullName2);
            user.setPhoneNum(phoneNum2);
            user.setEmail(email2);
            user.setBirthday(birthday2);
            user.setGender(gender2);

            assertEquals(user.getUserId(), userId2);
            assertEquals(user.getPassword(), password2);
            assertEquals(user.getFullName(), fullName2);
            assertEquals(user.getPhoneNum(), phoneNum2);
            assertEquals(user.getEmail(), email2);
            assertEquals(user.getBirthday(), birthday2);
            assertEquals(user.getGender(), gender2);
        }catch (LengthOutOfBoundException e){}
    }

    /**
     * Tests request lists
     */
    public void requestsTest() {
        // request ArrayList tests
        String userId = "jackb0";
        String password = "12345678";
        String fullName = "Black Jack";
        String phoneNum = "7806425671";
        String email = "jack@realmail.com";
        Date birthday = new Date();
        char gender = 'M';

        try {
            Patient user = new Patient(userId, password, fullName, phoneNum, email, birthday, gender);

            String id = "Blue Jack";
            String msg = "Hey, doctor here!";
            Request request = new Request(id, msg);

            id = "White Jack";
            msg = "Hello? please accept me.";
            Request request2 = new Request(id, msg);

            user.addRequest(request);
            user.addRequest(request2);

            ArrayList<Request> requests = user.getRequests();
            assertTrue(requests.contains(request));
            assertTrue(requests.contains(request2));
            assertEquals(request2, user.getRequestByIndex(1));
            assertEquals(request, user.getRequestByIndex(0));

            user.deleteRequest(0);
            assertEquals(request2,user.getRequestByIndex(0));
        }catch (LengthOutOfBoundException e){}
    }

    /**
     * Tests problems
     */
    public void problemTest() {
        // problem ArrayList test
        String userId = "jackb0";
        String password = "12345678";
        String fullName = "Black Jack";
        String phoneNum = "7806425671";
        String email = "jack@realmail.com";
        Date birthday = new Date();
        char gender = 'M';

        try{
            Patient user = new Patient(userId, password, fullName, phoneNum, email, birthday, gender);

            String prolemId1 = "Test";
            String problemId2 = "Test2";

            user.addProblemId(prolemId1);
            user.addProblemId(problemId2);

            ArrayList<String> problems = user.getProblemIds();
            assertTrue(problems.contains(prolemId1));
            assertTrue(problems.contains(problemId2));
            assertEquals(problemId2, user.getCareProviderUserIdByIndex(1));
            assertEquals(prolemId1, user.getCareProviderUserIdByIndex(0));

            user.deleteProblemId(0);
            assertEquals(problemId2,user.getCareProviderUserIdByIndex(0));
        }catch (LengthOutOfBoundException e){}
    }

    /**
     * Tests care providers
     */
    public void careProvidersTest() {
        // careProvider ArrayList tests
        String userId = "jackb0";
        String password = "12345678";
        String fullName = "Black Jack";
        String phoneNum = "7806425671";
        String email = "jack@realmail.com";
        Date birthday = new Date();
        char gender = 'M';

        try{
            Patient user = new Patient(userId, password, fullName, phoneNum, email, birthday, gender);

            String userId2 = "jackb2";

            user.addCareProviderUserId(userId);
            user.addCareProviderUserId(userId2);

            ArrayList<String> cids = user.getCareProviderUserIds();
            assertTrue(cids.contains(userId));
            assertTrue(cids.contains(userId2));
            assertEquals(userId2, user.getCareProviderUserIdByIndex(1));
            assertEquals(userId, user.getCareProviderUserIdByIndex(0));
        }catch (Exception e){}
    }
}
