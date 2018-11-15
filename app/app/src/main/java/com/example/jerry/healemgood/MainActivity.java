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

import com.example.jerry.healemgood.controller.UserCreationController;
import com.example.jerry.healemgood.model.user.User;
import com.example.jerry.healemgood.view.UserViews.AccountCreationActivity;
import com.example.jerry.healemgood.view.UserViews.PatientHomeActivity;

import java.util.ArrayList;

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

public class MainActivity extends AppCompatActivity {

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
    }

    /**
     * Handles everything relating to switching to the account creation page.
     */
    public void onCreateAccount(){
        TextView createTextView = findViewById(R.id.createAccountTextView);
        Button signInButton = findViewById(R.id.signInButton);
        createTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AccountCreationActivity.class));
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String userId = ((EditText)findViewById(R.id.userIdEditText)).getText().toString();

                Intent intent = new Intent(MainActivity.this,PatientHomeActivity.class);
                startActivity(intent);


            }
        });

    }
}
