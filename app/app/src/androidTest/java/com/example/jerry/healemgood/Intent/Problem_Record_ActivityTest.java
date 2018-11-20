package com.example.jerry.healemgood.Intent;

import android.support.v7.app.AppCompatActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.User;
import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.view.UserActivities.AccountCreationActivity;
import com.example.jerry.healemgood.view.UserViews.PatientAddProblemActivity;
import com.example.jerry.healemgood.view.UserViews.PatientAllProblemActivity;
import com.example.jerry.healemgood.view.UserViews.PatientAllRecordActivity;
import com.robotium.solo.Solo;

import java.sql.BatchUpdateException;
// Source: https://www.youtube.com/watch?v=T_8euppCz3k Accessed 2018-11-18

/**
 * Handles Problems and records creation
 * Creates a problem and adds a record to it
 * Tests Problem, Record, Body Maps
 * @author WeakMill98
 * @version 1.0
 * @see ActivityInstrumentationTestCase2
 * @see PatientAddProblemActivity
 * @see PatientAllRecordActivity
 * @see PatientAllProblemActivity
 * @since 1.0
 */

public class Problem_Record_ActivityTest extends ActivityInstrumentationTestCase2<com.example.jerry.healemgood.MainActivity> {
    private Solo solo;

    public Problem_Record_ActivityTest(){
        super("com.example.jerry.healemgood.Intent",
                com.example.jerry.healemgood.MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }


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
        solo.enterText(titleInput, "Test Title");
        solo.enterText(descriptionInput, "Test Description");

        // Save the Entry and leave
        Button saveButton = (Button) solo.getView(R.id.saveButton);
        solo.clickOnView(saveButton);

        // Assert the activity
        solo.assertCurrentActivity("Check on Problem", PatientAddProblemActivity.class);
    }

    public void testcreateRecord(){
        solo.assertCurrentActivity("Check on login", MainActivity.class);

        // Enter the credentials and enter the application
        EditText loginCredentials = (EditText) solo.getView(R.id.userIdEditText);
        solo.enterText(loginCredentials, "TestGUY12345");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Check on login", PatientAllProblemActivity.class);

        // Click on a problem, assuming there exists one
        ListView ListView=(ListView)solo.getView(R.id.patientProblemListView);
        View problemOne = ListView.getChildAt(0);
        solo.clickOnView(problemOne);
        solo.assertCurrentActivity("Check on Record", PatientAllRecordActivity.class);

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
