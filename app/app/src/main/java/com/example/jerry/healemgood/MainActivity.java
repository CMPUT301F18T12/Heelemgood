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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.OfflineTools;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;

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
import com.example.jerry.healemgood.view.UserViews.UserScanQRCodeLogin;
import com.example.jerry.healemgood.view.careProviderActivities.CareProviderAllPatientActivity;
import com.example.jerry.healemgood.view.commonActivities.AccountCreationActivity;
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
    public Button signInQRButton;
    private EditText userNameEditText;
    private ProgressBar progressBar;
    private TextView createTextView;

    /**
     * Reloads an earlier version of the activity if possible
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //set the activity context for offlineTools that will be used by all controllers
        OfflineTools.setContext(getApplicationContext());

        // Get the xml elements paths
        progressBar = findViewById(R.id.loginProgressBar);
        signInButton = findViewById(R.id.signInButton);
        signInQRButton = findViewById(R.id.signInQRButton);
        createTextView = findViewById(R.id.createAccountTextView);
        progressBar.setVisibility(View.INVISIBLE);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the progress bar
                // Get the username from the field
                userNameEditText = findViewById(R.id.userIdEditText);
                try{
                    UserController.SearchPatientTask task = new UserController.SearchPatientTask();
                    task.setProgressBar(progressBar);
                    Patient patient = task.execute(userNameEditText.getText().toString()).get();
                    try{
                        if (!patient.equals(null)){
                            //Store userid other important info into shared preference
                            SharedPreferenceUtil.store(MainActivity.this,AppConfig.USERID, patient.getUserId());
                            SharedPreferenceUtil.store(MainActivity.this,AppConfig.EMAIL, patient.getEmail());
                            SharedPreferenceUtil.store(MainActivity.this,AppConfig.BIRTHDAY, patient.getBirthday().toString());
                            SharedPreferenceUtil.store(MainActivity.this,AppConfig.NAME, patient.getFullName());
                            SharedPreferenceUtil.store(MainActivity.this,AppConfig.PHONE, patient.getPhoneNum());
                            SharedPreferenceUtil.store(MainActivity.this,AppConfig.ISPATIENT, AppConfig.TRUE);
                            // Go to the home page of the patient
                            Intent intent = new Intent(getApplicationContext(), PatientAllProblemActivity.class);
                            startActivity(intent);
                        }
                    }catch (Exception e){
                        //CareProvider careprovider = new UserController.SearchCareProviderTask().execute(userNameEditText.getText().toString()).get();
                        UserController.SearchCareProviderTask task1 = new UserController.SearchCareProviderTask();
                        task1.setProgressBar(progressBar);
                        CareProvider careprovider = task1.execute(userNameEditText.getText().toString()).get();
                        if (careprovider.getUserId().equals(userNameEditText.getText().toString())) {
                            //Store userid and other important info into shared preference
                            SharedPreferenceUtil.store(MainActivity.this, AppConfig.USERID, careprovider.getUserId());
                            SharedPreferenceUtil.store(MainActivity.this, AppConfig.EMAIL, careprovider.getEmail());
                            SharedPreferenceUtil.store(MainActivity.this, AppConfig.BIRTHDAY, careprovider.getBirthday().toString());
                            SharedPreferenceUtil.store(MainActivity.this, AppConfig.NAME, careprovider.getFullName());
                            SharedPreferenceUtil.store(MainActivity.this, AppConfig.PHONE, careprovider.getPhoneNum());
                            SharedPreferenceUtil.store(MainActivity.this,AppConfig.ISPATIENT,AppConfig.FALSE);
                            // Go to the home page of the care provider
                            Intent intent = new Intent(getApplicationContext(), CareProviderAllPatientActivity.class);
                            startActivity(intent);
                        }
                    }
                }catch (Exception e){}
            }
        });

        // Set a listener for when the user decides to login with as QR code
        // Will send the user to the UserScanQRCodeLogin activity
        signInQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserScanQRCodeLogin.class));
            }
        });

        // When the user decides to create a new account
        createTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AccountCreationActivity.class));
            }
        });
    }
}
