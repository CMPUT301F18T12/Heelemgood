/*
 *  Class Name: PatientControllerTest
 *
 *  Version: Version 1.0
 *
 *  Date: November 13, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.Controller;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;
import com.robotium.solo.Solo;

import java.util.Date;

import static org.junit.Assert.assertNotEquals;

/**
 * Patient Test
 * 1. testCreateUser: Tests the add user functionality of the patient controller
 * 2. testSearchUser: Tests the search user functionality of the patient controller
 * 3. testDeleteUser: Tests the add user functionality of the patient controller
 * 4. testUpdateUser: Tests the update user functionality of the patient controller
 * @author Weakmill98
 * @version 1.0.3
 */

// A class used to test the methods of the UserController class
public class PatientControllerTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public PatientControllerTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    /**
     * Tests storing users and searching for users
     *
     */
    public void testCreateUser() {
        try {
            Patient patient = patientConstructor();
            User usera = new UserController.SearchPatientTask().execute("update12345678").get();
            if (!usera.getUserId().equals("update12345678")){
                new UserController.AddUserTask().execute(patient);
                // Add a breakpoint on the next line to view the object before it is deleted
                User user2 = new UserController.SearchPatientTask().execute("update12345678").get();
                assertNull(user2);
            }
            User user = new UserController.SearchPatientTask().execute("update12345678").get();
            new UserController.DeleteUserTask().execute(user);
        } catch (Exception e) {
            Log.d("Test", "Testing Error Add");
        }
    }

    /**
     * Tests searching for the user
     *
     */
    public void testSearchUser() {
        User user;
        Patient patient = patientConstructorHeb();
        try {
            // Search for a user that exists
            new UserController.AddUserTask().execute(patient);
            user = new UserController.SearchPatientTask().execute("heb12345678").get();
            assertEquals("heb12345678", user.getUserId());

            // Search for a user that does not exist
            // The user object returned should not have a name
            user = new UserController.SearchPatientTask().execute("jeb12345678").get();
            assertNotEquals("jeb12345678", user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests deleting users
     *
     */
    public void testDeleteUser() {
        Patient patient = patientConstructor();
        try {
            User user;
            // Create a test user, add them to the DB
            new UserController.AddUserTask().execute(patient);
            // Check that the user is in the DB, before we delete them
            user = new UserController.SearchPatientTask().execute("Patient12345678").get();
            assertEquals(user.getClass().getSimpleName(), "Patient12345678");
            // Delete the user and check that the reference is now null
            new UserController.DeleteUserTask().execute(patient).get();
            user = new UserController.SearchPatientTask().execute("Patient12345678").get();
            assertNull(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests updating a user
     *
     */
    public void testUpdateUser() {
        User user;
        try {
            user = new UserController.SearchPatientTask().execute("userid12345678").get();
            String email = user.getEmail();
            assertEquals(email, user.getEmail());

            user.setEmail("jnjn Email");
            new UserController.UpdateUserTask().execute(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests various functions of patients
     *
     * @return the patient created
     */
    private Patient patientConstructor() {
        String userId = "update12345678";
        String password = "password";
        String name = "Joey";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        try {
            return new Patient(userId, password, name, phoneNum, email, birthday, gender);
        } catch (LengthOutOfBoundException e) {
            return null;
        }
    }

    /**
     * Creates a test patient
     *
     * @return the patient object
     */
    private Patient patientConstructorHeb() {
        String userId = "heb12345678";
        String password = "password";
        String name = "Joey";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        try {
            return new Patient(userId, password, name, phoneNum, email, birthday, gender);
        } catch (LengthOutOfBoundException e) {
            return null;
        }
    }

    /*public void testDeleteSingleUser(){
        //Delete a single user, will not change
        User user;
        try{
            user = new UserController.SearchPatientTask().execute("update12345678").get();
            new UserController.DeleteUserTask().execute(user).get();
        }catch (Exception e){}
    }
*/

   /* public void testAddSingleUser(){
        //Delete a single user, will not change
        User user = patientConstructor();
        try{
            new UserController.AddUserTask().execute(user).get();
        }catch (Exception e){}
    }*/
}
