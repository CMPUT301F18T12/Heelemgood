package com.example.jerry.healemgood.Controller;

import android.test.ActivityInstrumentationTestCase2;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.controller.UserCreationController;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;
import com.robotium.solo.Solo;

import java.util.Date;

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

    // Creates a test patient for the following tests
    private Patient patientConstructor(){
        String userid = "userid";
        String password = "password";
        String name = "Joey";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        return new Patient(userid,password,name,phoneNum,email,birthday,gender);
    }

    // Create a new patient, push it onto elastic search.
    // Test using postman or an http request to see if the patient is successfully created and stored
    public void testCreateUser(){
        Patient patient = patientConstructor();
        new UserCreationController.addUserTask().execute(patient);
        // Add a breakpoint on the next line to view the object before it is deleted
        new UserCreationController.deleteUserTask().execute(patient);
    }

    // Search for a patient that has been created
    public void testSearchUser(){
        Patient patient = patientConstructor();
        new UserCreationController.addUserTask().execute(patient);
    }
}
