/*
 *  Class Name: LoginActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 17, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.view.accountActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.view.patientActivities.PatientAllProblemActivity;

/**
 * A controller that handles logging into the app
 *
 * @author WeakMill98
 * @version 1.0
 * @since 1.0
 */


public class LoginActivity extends AppCompatActivity {
    private EditText userNameEditText;
    private String userName;
    private Button signInButton;
    private TextView createAccountTextView;

    /**
     * Loads a previous version of the activity if possible
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getAllXML();
        //onCreateAccount();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PatientAllProblemActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Gets all xml elements in the view
     *
     */
    public void getAllXML(){
        userNameEditText = findViewById(R.id.userIdEditText);
        signInButton = findViewById(R.id.signInButton);
        createAccountTextView = findViewById(R.id.createAccountTextView);
    }


    /**
     * Switches to the create account page
     *
     */
    public void onCreateAccount(){
        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, AccountCreationActivity.class));
            }
        });
    }
}
