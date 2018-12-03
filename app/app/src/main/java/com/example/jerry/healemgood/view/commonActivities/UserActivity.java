/*
 *  Class Name: PatientUserActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 14, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.view.commonActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.User;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;

/**
 * Represents a PatientUserActivity
 * Handles all functions relating to allowing a patient to post a record for a problem
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class UserActivity extends AppCompatActivity {
    TextView nameText;
    TextView userIdText;

    EditText emailInput;
    EditText phoneInput;
    ProgressBar progressBar;
    /**
     * Reloads an earlier version of the activity if possible
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_user);
        setTitle("User Info");

         nameText = findViewById(R.id.userFullName);
         userIdText = findViewById(R.id.userId);

         emailInput = findViewById(R.id.userEmail);
         phoneInput = findViewById(R.id.userPhone);

         progressBar = findViewById(R.id.progressBar);
         progressBar.setVisibility(View.INVISIBLE);

        final Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                finish();
            }
        });

        Button logOutButton = findViewById(R.id.logoutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this,MainActivity.class));
            }
        });

        fillOutInfo();

    }

    /**
     * Fill out the info in the fields
     *
     */

    private void fillOutInfo(){
        nameText.setText(SharedPreferenceUtil.get(this,AppConfig.NAME));
        userIdText.setText(SharedPreferenceUtil.get(this,AppConfig.USERID));

        emailInput.setText(SharedPreferenceUtil.get(this,AppConfig.EMAIL));
        phoneInput.setText(SharedPreferenceUtil.get(this,AppConfig.PHONE));

    }

    /**
     * Fill out the info in the fields
     *
     */

    private void save(){
        User user = null;
        // if it is a patient
        if (SharedPreferenceUtil.get(getApplicationContext(),AppConfig.ISPATIENT).equals(AppConfig.TRUE)){
            try{
                UserController.SearchPatientTask task = new UserController.SearchPatientTask();
                task.setProgressBar(progressBar);
                user = task.execute(userIdText.getText().toString()).get();
                //user = new UserController.SearchPatientTask().execute(userIdText.getText().toString()).get();
            }
            catch (Exception e){
                Log.d("ERROR","Fail to get the patient");
            }

        }
        else{
            try{
                UserController.SearchCareProviderTask task1 = new UserController.SearchCareProviderTask();
                task1.setProgressBar(progressBar);
                user = task1.execute(userIdText.getText().toString()).get();
                //user = new UserController.SearchCareProviderTask().execute(userIdText.getText().toString()).get();
            }
            catch (Exception e){
                Log.d("ERROR","Fail to get the patient");
            }
        }

        // update the info
        try{
            // Access the user object, and change the object's parameters to what was inputted

            user.setEmail(emailInput.getText().toString());
            user.setPhoneNum(phoneInput.getText().toString());
            new UserController.UpdateUserTask().execute(user);

            // Set the new values into shared preferences
            SharedPreferenceUtil.store(getApplicationContext(), AppConfig.EMAIL, emailInput.getText().toString());
            SharedPreferenceUtil.store(getApplicationContext(), AppConfig.PHONE, phoneInput.getText().toString());
        }catch (Exception e){
            Log.d("Error", "Error with saving");
        }
    }
}
