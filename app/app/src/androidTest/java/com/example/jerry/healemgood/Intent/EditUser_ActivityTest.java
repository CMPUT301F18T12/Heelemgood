package com.example.jerry.healemgood.Intent;

import android.test.ActivityInstrumentationTestCase;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.User;
import com.example.jerry.healemgood.view.UserActivities.AccountCreationActivity;
import com.example.jerry.healemgood.view.UserViews.PatientAllProblemActivity;
import com.example.jerry.healemgood.view.UserViews.PatientUserActivity;
import com.robotium.solo.Solo;

public class EditUser_ActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    Solo solo;

    public EditUser_ActivityTest(){
        super("com.example.jerry.healemgood.Intent",
                MainActivity.class);
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

    public void testEditUser(){
        // Click on create a new user
        // Assert that you go to the right page
        /*TextView createAccount = (TextView) solo.getView(R.id.createAccountTextView);
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
        solo.clickOnButton("Create");*/

        // Enter the credentials and enter the application
        EditText loginCredentials = (EditText) solo.getView(R.id.userIdEditText);
        solo.enterText(loginCredentials, "TestGUY12345");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Check on login", PatientAllProblemActivity.class);

        // Edit a field of the user class
        solo.sleep(5000);
        ViewGroup tabs = (ViewGroup) solo.getView(R.id.nav_view);
        View userIcon = tabs.getChildAt(0);
        solo.clickOnView(userIcon);

        //solo.("PatientUserActivity");

        // Delete the test user
        User user;
        try {
            user = new UserController.SearchUserTask().execute("TestGUY12345").get();
            new UserController.DeleteUserTask().execute(user);
        }catch (Exception e){}
    }
}
