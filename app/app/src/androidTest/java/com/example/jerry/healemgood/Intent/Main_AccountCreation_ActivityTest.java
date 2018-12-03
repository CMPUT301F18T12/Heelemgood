/*
 *  Class Name: Main_AccountCreation_ActivityTest
 *
 *  Version: Version 1.0
 *
 *  Date: November 19, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.Intent;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.User;
import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.view.commonActivities.AccountCreationActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientAllProblemActivity;
import com.robotium.solo.Solo;
// Source: https://www.youtube.com/watch?v=T_8euppCz3k Accessed 2018-11-18

/**
 * Handles logging in
 * Creates an account, uses it to log in, and deletes it afterwards
 * Tests Main activity, AccountCreation Activity
 * @author WeakMill98
 * @version 1.0
 * @see ActivityInstrumentationTestCase2
 * @see AccountCreationActivity
 * @see MainActivity
 * @since 1.0
 */

public class Main_AccountCreation_ActivityTest extends ActivityInstrumentationTestCase2<com.example.jerry.healemgood.MainActivity> {
    private Solo solo;

    /**
     * Creates a Main_AccountCreation_ActivityTest
     *
     */
    public Main_AccountCreation_ActivityTest(){
        super("com.example.jerry.healemgood.Intent",
                com.example.jerry.healemgood.MainActivity.class);
    }

    /**
     * Handles set up
     *
     * @throws Exception
     */
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
     * Handles clean up
     *
     * @throws Exception
     */
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    /**
     * Tests logging in with no username entered
     *
     */
    public void testNoUserNameLogin(){
        // Clicking on sign in without a username will not sign in
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Check on login", MainActivity.class);
    }

    /**
     * Tests logging in
     *
     */
    public void testLogin() {
        solo.assertCurrentActivity("Check on login", MainActivity.class);
        // Just to check that the code is running
        // fail()

        // Click on create a new user
        // Assert that you go to the right page
        TextView createAccount = (TextView) solo.getView(R.id.createAccountTextView);
        solo.clickOnView(createAccount);
        solo.assertCurrentActivity("Check on login", AccountCreationActivity.class);

        // Create a new account and try to login with it
        // Get all the xml attributes
        EditText userIdEditText = (EditText) solo.getView(R.id.userIdEditText);
        EditText firstNameEditText = (EditText) solo.getView(R.id.firstNameEditText);
        EditText lastNameEditText = (EditText) solo.getView(R.id.lastNameEditText);
        EditText emailEditText = (EditText) solo.getView(R.id.emailEditText);
        EditText phoneEditText = (EditText) solo.getView(R.id.phoneEditText);
        RadioButton radioButton = (RadioButton) solo.getView(R.id.patientRadioButton);

        // Skip passwords for now
        // Enter in values for all other fields
        solo.enterText(userIdEditText, "TestGUY12345");
        solo.enterText(firstNameEditText, "test");
        solo.enterText(lastNameEditText, "GUY");
        solo.enterText(emailEditText, "test@GUY.com");
        solo.enterText(phoneEditText, "780");
        solo.clickOnView(radioButton);

        // Create the account
        solo.clickOnButton("Create");

        // Enter the credentials and enter the application
        EditText loginCredentials = (EditText) solo.getView(R.id.userIdEditText);
        solo.enterText(loginCredentials, "TestGUY12345");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Check on login", PatientAllProblemActivity.class);

        // Delete the test user
        User user;
        try {
            user = new UserController.SearchPatientTask().execute("TestGUY12345").get();
            new UserController.DeleteUserTask().execute(user);
        }catch (Exception e){}
    }
}
