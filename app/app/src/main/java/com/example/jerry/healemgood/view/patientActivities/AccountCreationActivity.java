/*
 *  Class Name: AccountCreationActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 15, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.view.patientActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;

import java.util.Date;

/**
 * This class handles account creation
 *
 * @author WeakMill98
 * @version 1.0
 * @since 1.0
 */
public class AccountCreationActivity extends AppCompatActivity {

    private EditText userName;
    private EditText firstName;
    private EditText lastName;
    private EditText birthDay;
    private EditText emailAddress;
    private EditText phoneNumber;
    private EditText password;
    private EditText confirmationPassword;
    private Button createButton;
    private RadioButton patientRadioButton;
    private RadioButton careProviderRadioButton;

    /**
     * Loads older instance if possible
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);


        // Find references to the XML path
        // This includes all user attributes, such as username, password, etc.
        // TODO: Remove duplicate user names
        // TODO: Add in a calendar picker widget

        getAllXML();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userName.getText().toString();
                String p = password.getText().toString();
                String na =  firstName.getText().toString();
                String ph =  phoneNumber.getText().toString();
                String ema = emailAddress.getText().toString();

                if (patientRadioButton.isChecked() && careProviderRadioButton.isChecked()){
                    Toast.makeText(getApplicationContext(),
                            "Please Select If You Are a Patient or Care Provider"
                            ,Toast.LENGTH_SHORT).show();
                }
                if (patientRadioButton.isChecked()){
                    Patient patient = null;
                    try {
                        patient = new Patient(
                                username, p, na, ph, ema,
                                new Date(),
                                'M'
                        );
                    } catch (LengthOutOfBoundException e) {
                        Toast.makeText(getApplicationContext(),
                                "Your userId is too short (at least 8 characters)"
                                ,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    patientRadioButton.setChecked(false);
                    new UserController.AddUserTask().execute(patient);
                    clearText();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                if (careProviderRadioButton.isChecked()){
                    CareProvider careProvider = null;
                    try {
                        careProvider = new CareProvider(
                                username, p, na, ph, ema,
                                new Date(),
                                'M'
                        );
                    } catch (LengthOutOfBoundException e) {
                        Toast.makeText(getApplicationContext(),
                                "Your userId is too short (at least 8 characters)"
                                ,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    careProviderRadioButton.setChecked(false);
                    new UserController.AddUserTask().execute(careProvider);
                    clearText();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Please Select If You Are a Patient or Care Provider"
                            ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Gets all the xml elements
     *
     */
    private void getAllXML(){
        userName = findViewById(R.id.userIdEditText);
        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        emailAddress = findViewById(R.id.emailEditText);
        phoneNumber = findViewById(R.id.phoneEditText);
        password = findViewById(R.id.passwordEditText);
        confirmationPassword = findViewById(R.id.confirmPasswordEditText);
        createButton = findViewById(R.id.createAccountButton);
        patientRadioButton = findViewById(R.id.patientRadioButton);
        careProviderRadioButton = findViewById(R.id.careProviderRadioButton);
    }

    /**
     * Clears the text
     *
     */
    private void clearText(){
        userName.getText().clear();
        firstName.getText().clear();
        lastName.getText().clear();
        emailAddress.getText().clear();
        phoneNumber.getText().clear();
        password.getText().clear();
        confirmationPassword.getText().clear();
    }
}
