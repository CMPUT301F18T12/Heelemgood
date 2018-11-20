package com.example.jerry.healemgood.Intent;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.model.user.User;
import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.view.UserActivities.AccountCreationActivity;
import com.robotium.solo.Solo;

public class MainActivityTest extends ActivityInstrumentationTestCase2<com.example.jerry.healemgood.MainActivity> {
    private Solo solo;

    public MainActivityTest(){
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

    public void testNoUserNameLogin(){
        // Clicking on sign in without a username will not sign in
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Check on login", MainActivity.class);
    }

    public void testLogin() {
        solo.assertCurrentActivity("Check on login", MainActivity.class);
        // Just to check that the code is running
        // fail()

        // Click on create a new user
        // Assert that you go to the right page
        TextView tv = (TextView)solo.getView(R.id.createAccountTextView);
        solo.clickOnView(tv);
        solo.assertCurrentActivity("Check on login", AccountCreationActivity.class);
    }
}
