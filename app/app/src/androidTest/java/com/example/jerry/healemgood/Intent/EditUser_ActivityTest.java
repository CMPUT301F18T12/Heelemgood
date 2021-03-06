/*
 *  Class Name: EditUser_ActivityTest
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
import android.widget.Toast;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;
import com.example.jerry.healemgood.view.commonActivities.AccountCreationActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientAllProblemActivity;
import com.example.jerry.healemgood.view.commonActivities.UserActivity;
import com.robotium.solo.Solo;

import java.util.Date;

/**
 * Handles editing the user account
 * Creates an account, uses it to log in, and edits it
 * @author WeakMill98
 * @version 1.0
 * @see ActivityInstrumentationTestCase2
 * @see AccountCreationActivity
 * @see MainActivity
 * @see UserActivity
 * @since 1.0
 */

public class EditUser_ActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    Solo solo;

    // Call the constructor
    public EditUser_ActivityTest(){
        super("com.example.jerry.healemgood.Intent",
                MainActivity.class);
    }

    /**
    * Handles errors
    */
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
    * Shuts down if ther's an error
    */
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    /**
     * Tests Editing the user
     *
     */
    public void testEditUser(){
        User user;
        try {
            user = new Patient("TestGUY12345",".","sd",",","sd",new Date(),'M');
            new UserController.DeleteUserTask().execute(user);
        }catch (Exception e){}
        solo.sleep(2000);

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
        solo.sleep(2000);
        solo.clickOnButton("Sign In");
        solo.sleep(2000);
//        solo.assertCurrentActivity("Check on login", PatientAllProblemActivity.class);

        // Edit a field of the user class
        solo.sleep(5000);/*
        ViewGroup tabs = (ViewGroup) solo.getView(R.id.nav_view);
        View userIcon = tabs.getChildAt(0);
        solo.clickOnView(userIcon);*/

        solo.pressMenuItem(3);

        //solo.("PatientUserActivity");
    }
}
