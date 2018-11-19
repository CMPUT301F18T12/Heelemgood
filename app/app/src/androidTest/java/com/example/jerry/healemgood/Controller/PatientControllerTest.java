package com.example.jerry.healemgood.Controller;

import android.test.ActivityInstrumentationTestCase2;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;
import com.robotium.solo.Solo;

import java.util.Date;

// A class used to test the methods of the UserControllerDeprecated class
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

    // Create a new patient, push it onto elastic search.
    // Test using postman or an http request to see if the patient is successfully created and stored
    public void testCreateUser(){
        Patient patient = patientConstructor();
        new UserController.AddUserTask().execute(patient);
        // Add a breakpoint on the next line to view the object before it is deleted
        try {
            User user = new UserController.SearchUserTask().execute("test").get();
            new UserController.DeleteUserTask().execute(user);
            User user2 = new UserController.SearchUserTask().execute("test").get();
            assertNull(user2);
        }catch (Exception e){}
    }

    // Search for a patient that has been created
    public void testSearchUser() {
        User user;
        Patient patient = patientConstructorHeb();
        try {
            // Search for a user that exists
            new UserController.AddUserTask().execute(patient);
            user = new UserController.SearchUserTask().execute("heb").get();
            assertTrue(user.getUserId().equals("heb"));

            // Search for a user that does not exist
            // The user object returned should not have a name
            user = new UserController.SearchUserTask().execute("jeb").get();
            assertFalse(user.getUserId().equals("jeb"));
        }catch (Exception e){}
    }

    // Test to delete a user
    public void testDeleteUser(){
        Patient patient = patientConstructor();
        try{
            User user;
            // Create a test user, add them to the DB
            new UserController.AddUserTask().execute(patient);
            // Check that the user is in the DB, before we delete them
            user = new UserController.SearchUserTask().execute("Patient").get();
            assertEquals(user.getClass().getSimpleName(), "Patient");
            // Delete the user and check that the reference is now null
            new UserController.DeleteUserTask().execute(patient).get();
            user = new UserController.SearchUserTask().execute("Patient").get();
            assertNull(user);
        }catch (Exception e){}
    }

/*    public void testDeleteSingleUser(){
        //Delete a single user, will not change
        User user;
        try{
            user = new UserController.SearchUserTask().execute("Jerryb").get();
            new UserController.DeleteUserTask().execute(user).get();
        }catch (Exception e){}
    }*/


   /* public void testAddSingleUser(){
        //Delete a single user, will not change
        User user = patientConstructor();
        try{
            new UserController.AddUserTask().execute(user).get();
        }catch (Exception e){}
    }*/


    // Test to update a user
    public void testUpdateUser(){
        User user;
        try{
            user = new UserController.SearchUserTask().execute("userid").get();
            String email = user.getEmail();
            assertEquals(email, user.getEmail());

            user.setEmail("jnjn Email");
            new UserController.UpdateUserTask().execute(user);
        }catch (Exception e){}
    }

    // Creates a test patient for the following tests
    private Patient patientConstructor(){
        String userId = "update";
        String password = "password";
        String name = "Joey";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        return new Patient(userId,password,name,phoneNum,email,birthday,gender);
    }

    // Creates a test patient for the following tests
    private Patient patientConstructorHeb(){
        String userId = "heb";
        String password = "password";
        String name = "Joey";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        return new Patient(userId,password,name,phoneNum,email,birthday,gender);
    }
}
