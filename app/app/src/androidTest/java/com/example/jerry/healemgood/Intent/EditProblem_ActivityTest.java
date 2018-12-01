/*
 *  Class Name: EditProblem_ActivityTest
 *
 *  Version: Version 1.0
 *
 *  Date: November 20, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.Intent;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.view.patientActivities.AccountCreationActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientAddProblemActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientAllProblemActivity;
import com.example.jerry.healemgood.view.commonActivities.AllRecordActivity;
import com.robotium.solo.Solo;

/**
 * Handles Editing Problems
 * Creates a problem
 * Tests Problem, and edit
 * @author WeakMill98
 * @version 1.0
 * @see ActivityInstrumentationTestCase2
 * @see PatientAddProblemActivity
 * @see AllRecordActivity
 * @see PatientAllProblemActivity
 * @since 1.0
 */

public class EditProblem_ActivityTest extends ActivityInstrumentationTestCase2<com.example.jerry.healemgood.MainActivity> {
    private Solo solo;

    /**
    * Creates an instance of the class
    */
    public EditProblem_ActivityTest() {
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
    * Shuts down if there's an error.
    */
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    /**
     * Tests Creating an account
     *
     */
    // Create a testing account
    public void testCreateAccount() {
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
     * Tests Creating a problem and editing it
     *
     */
    // Create a new problem and be able to edit it
    public void testProblemCreationAndEdit() {

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

        solo.sleep(1000);

        // Click on the first problem in the list view
        ListView ListView=(ListView)solo.getView(R.id.patientListView);
        View problemOne = ListView.getChildAt(0);
        solo.clickOnView(problemOne);
        solo.assertCurrentActivity("Check on Record", AllRecordActivity.class);

        // Get and click on the detail button
        Button detailsButton = (Button)solo.getView(R.id.detailButton);
        solo.clickOnView(detailsButton);

        // Get the title and description edit texts
        EditText titleInput2 = (EditText) solo.getView(R.id.titleInput);
        EditText descriptionInput2 = (EditText) solo.getView(R.id.descriptionInput);

        solo.sleep(1000);

        // Clear the text boxes
        //titleInput2.getText().clear();
        //descriptionInput2.getText().clear();

        // Get the original text
        String originalName = titleInput2.getText().toString();
        String originalDesc = descriptionInput2.getText().toString();

        // Populate the two fields
        solo.enterText(titleInput2, "Arm Broken (Again)");
        solo.enterText(descriptionInput2, "Yeah this hurts even more");

        // Assert that the changes have taken place
        assertEquals(titleInput2.getText().toString(), originalName + "Arm Broken (Again)");
        assertEquals(descriptionInput2.getText().toString(),originalDesc + "Yeah this hurts even more" );

        // Save the changes, and assert the new activity is correct
        Button saveButton2 = (Button)solo.getView(R.id.saveButton);
        solo.clickOnView(saveButton2);
        solo.assertCurrentActivity("Check on Record", AllRecordActivity.class);

        // Click on the first problem in the list view
        ListView ListView2 =(ListView)solo.getView(R.id.patientListView);
        View problemOne2 = ListView2.getChildAt(0);
        solo.clickOnView(problemOne2);
        solo.assertCurrentActivity("Check on Record", AllRecordActivity.class);

        // Get and click on the detail button
        Button detailsButton2 = (Button)solo.getView(R.id.detailButton);
        solo.clickOnView(detailsButton2);

        // Get the title and description edit texts
        EditText titleInput3 = (EditText) solo.getView(R.id.titleInput);
        EditText descriptionInput3 = (EditText) solo.getView(R.id.descriptionInput);

        // Assert the changes have actually been saved
        assertEquals(titleInput3.getText().toString(), originalName + "Arm Broken (Again)");
        assertEquals(descriptionInput3.getText().toString(), originalDesc + "Yeah this hurts even more");
    }
}
