/*
 *  Class Name: Problem_Record_ActivityTest
 *
 *  Version: Version 1.0
 *
 *  Date: November 19, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.Intent;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.view.patientActivities.AccountCreationActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientAddProblemActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientAllProblemActivity;
import com.example.jerry.healemgood.view.commonActivities.AllRecordActivity;
import com.robotium.solo.Solo;
// Source: https://www.youtube.com/watch?v=T_8euppCz3k Accessed 2018-11-18

/**
 * Handles Problems and records creation
 * Creates a problem and adds a record to it
 * Tests Problem, Record, Body Maps
 * @author WeakMill98
 * @version 1.0
 * @see ActivityInstrumentationTestCase2
 * @see PatientAddProblemActivity
 * @see AllRecordActivity
 * @see PatientAllProblemActivity
 * @since 1.0
 */

public class Problem_Record_ActivityTest extends ActivityInstrumentationTestCase2<com.example.jerry.healemgood.MainActivity> {
    private Solo solo;

    /**
    * Handles creating the instance
    */
    public Problem_Record_ActivityTest(){
        super("com.example.jerry.healemgood.Intent",
                com.example.jerry.healemgood.MainActivity.class);
    }

    /**
    * Handles errors
    */
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
    * Shuts down if there's an error
    */
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    /**
     * Tests Account creation
     *
     */
    public void testCreateAccount(){
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
    }

    /**
     * Tests Problem Creation
     *
     */
    public void testProblemCreation() {
        solo.assertCurrentActivity("Check on login", MainActivity.class);

        // Enter the credentials and enter the application
        EditText loginCredentials = (EditText) solo.getView(R.id.userIdEditText);
        solo.enterText(loginCredentials, "TestGUY12345");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Check on login", PatientAllProblemActivity.class);

        // Create a new problem
        Button createProblemButton = (Button) solo.getView(R.id.createProblemButton);
        solo.clickOnView(createProblemButton);
        solo.assertCurrentActivity("Check on Problem", PatientAddProblemActivity.class);

        // Get the title and description edit texts
        EditText titleInput = (EditText) solo.getView(R.id.titleInput);
        EditText descriptionInput = (EditText) solo.getView(R.id.descriptionInput);

        // Populate the two fields
        solo.enterText(titleInput, "Arm Broke");
        solo.enterText(descriptionInput, "Yeah this hurts");

        // Save the Entry and leave
        Button saveButton = (Button) solo.getView(R.id.saveButton);
        solo.clickOnView(saveButton);

        // Assert the activity
        solo.assertCurrentActivity("Check on Problem", PatientAddProblemActivity.class);
    }

    /**
     * Tests Record Creation
     *
     */
    public void testcreateRecord(){
        solo.assertCurrentActivity("Check on login", MainActivity.class);

        // Enter the credentials and enter the application
        EditText loginCredentials = (EditText) solo.getView(R.id.userIdEditText);
        solo.enterText(loginCredentials, "TestGUY12345");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Check on login", PatientAllProblemActivity.class);

        // Click on a problem, assuming there exists one
        ListView ListView=(ListView)solo.getView(R.id.patientListView);
        View problemOne = ListView.getChildAt(0);
        solo.clickOnView(problemOne);
        solo.assertCurrentActivity("Check on Record", AllRecordActivity.class);

        // Create a new record
        Button createRecord = (Button) solo.getView(R.id.createRecordButton);
        solo.clickOnView(createRecord);

        // Click on a location on the body map
        solo.clickOnScreen(400, 400);
        solo.clickOnScreen(500, 300);
        solo.clickOnScreen(600, 600);
        solo.clickOnScreen(100, 100);
        solo.clickOnScreen(200, 200);
        solo.clickOnScreen(300, 300);
        solo.clickOnScreen(900, 100);
        solo.clickOnScreen(100, 900);

        ImageView imageView = (ImageView) solo.getView(R.id.bodyMap);
        solo.clickOnView(imageView);

        ImageView imageView2 = (ImageView) solo.getView(R.id.colorMap);
        solo.clickOnView(imageView2);

        //TODO: Map needs to be clicked properly, works 100% with human intervention

        /*Button continueButton = (Button) solo.getView(R.id.continueButton);
        solo.clickOnView(continueButton);

        // Get fields
        EditText title = (EditText) solo.getView(R.id.titleInput);
        EditText description = (EditText) solo.getView(R.id.descriptionInput);

        // Populate the two fields
        solo.enterText(title, "Test Title");
        solo.enterText(description, "Test Description");

        // Save the record, click on the button
        Button saveButton = (Button) solo.getView(R.id.saveButton);
        solo.clickOnView(saveButton);*/
    }
}
