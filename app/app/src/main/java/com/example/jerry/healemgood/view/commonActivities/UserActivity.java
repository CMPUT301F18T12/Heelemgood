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

    /**
     * Reloads an earlier version of the activity if possible
     *
     * @param savedInstanceState
     */
    TextView nameText;
    TextView userIdText;
    TextView birthdayText;
    EditText emailInput;
    EditText phoneInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_user);
        setTitle("User Info");

         nameText = findViewById(R.id.userFullName);
         userIdText = findViewById(R.id.userId);
         birthdayText = findViewById(R.id.userBirthday);
         emailInput = findViewById(R.id.userEmail);
         phoneInput = findViewById(R.id.userPhone);

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
        birthdayText.setText(SharedPreferenceUtil.get(this,AppConfig.BIRTHDAY));
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
                user = new UserController.SearchPatientTask().execute(userIdText.getText().toString()).get();
            }
            catch (Exception e){
                Log.d("ERROR","Fail to get the patient");
            }

        }
        else{
            try{
                user = new UserController.SearchCareProviderTask().execute(userIdText.getText().toString()).get();
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
