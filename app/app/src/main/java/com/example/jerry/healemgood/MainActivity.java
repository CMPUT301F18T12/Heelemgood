/*
 *  Class Name: MainActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;

/**
 * Represents a MainActivity
 * Handles all functions relating to signing in to an already created account
 * Also gives the option to create a new account
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.patientActivities.AccountCreationActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientAllProblemActivity;

/**
 * Represents the main activity
 *
 * @author WeakMill98
 * @version 1.0
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {
    public Button signInButton;
    public Button scanQRcodeButton;
    private EditText userNameEditText;

    /**
     * Reloads an earlier version of the activity if possible
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.onCreateAccount();

        signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userNameEditText = findViewById(R.id.userIdEditText);
                try{
                    User patient = new UserController.SearchPatientTask().execute(userNameEditText.getText().toString()).get();
                    try{
                        if (!patient.equals(null)){

                            //Store userid into shared preference
                            SharedPreferenceUtil.store(MainActivity.this,AppConfig.USERID,patient.getUserId());
                            SharedPreferenceUtil.store(MainActivity.this,AppConfig.EMAIL,patient.getEmail());
                            SharedPreferenceUtil.store(MainActivity.this,AppConfig.BIRTHDAY,patient.getBirthday().toString());
                            SharedPreferenceUtil.store(MainActivity.this,AppConfig.NAME,patient.getFullName());
                            SharedPreferenceUtil.store(MainActivity.this,AppConfig.PHONE,patient.getPhoneNum());

                            // Go to the home page of the patient
                            Intent intent = new Intent(getApplicationContext(), PatientAllProblemActivity.class);
                            startActivity(intent);
                        }
                    }catch (Exception e){
                        User careprovider = new UserController.SearchCareProviderTask().execute(userNameEditText.getText().toString()).get();
                        if (careprovider.getUserId().equals(userNameEditText.getText().toString())) {
                            //Store userid into shared preference
                            SharedPreferenceUtil.store(MainActivity.this, AppConfig.USERID, careprovider.getUserId());
                            SharedPreferenceUtil.store(MainActivity.this, AppConfig.EMAIL, careprovider.getEmail());
                            SharedPreferenceUtil.store(MainActivity.this, AppConfig.BIRTHDAY, careprovider.getBirthday().toString());
                            SharedPreferenceUtil.store(MainActivity.this, AppConfig.NAME, careprovider.getFullName());
                            SharedPreferenceUtil.store(MainActivity.this, AppConfig.PHONE, careprovider.getPhoneNum());

                            // Go to the home page of the care provider
                            Intent intent = new Intent(getApplicationContext(), PatientAllProblemActivity.class);
                            startActivity(intent);
                        }
                    }
                }catch (Exception e){}
            }
        });
    }

    /**
     * Handles everything relating to switching to the account creation page.
     */
    public void onCreateAccount(){
        TextView createTextView = findViewById(R.id.createAccountTextView);
        createTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AccountCreationActivity.class));
            }
        });
    }
}
