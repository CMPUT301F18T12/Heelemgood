/*  AccountCreationActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 15, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.view.commonActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
 * A controller that handles creating a new account
 *
 * @author WeakMill98
 * @version 1.0
 * @since 1.0
 */
public class AccountCreationActivity extends AppCompatActivity {

    private EditText userName;
    private EditText firstName;
    private EditText lastName;
    private EditText emailAddress;
    private EditText phoneNumber;
    private Button createButton;
    private RadioButton patientRadioButton;
    private RadioButton careProviderRadioButton;
    private ProgressBar progressBar;
    private Boolean check_email;
    private Boolean check_phone;

    /**
     * Loads older instance if possible
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        setTitle("Create Account");

        // Find references to the XML path
        // This includes all user attributes, such as username, password, etc.
        getAllXML();
        emailAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (emailAddress.getText().toString().contains("@") && emailAddress.getText().toString().contains(".com")){
                    check_email = true;
                }
                else{
                    emailAddress.setError("Invalid Email Address");
                    check_email = false;
                }
            }
        });
        phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (phoneNumber.getText().toString().trim().length() < 10){
                    phoneNumber.setError("Invalid Phone number");
                    check_phone = false;
                }
                else{
                    check_phone = true;
                }
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get basic user parameters
                String username = userName.getText().toString();
                String na =  firstName.getText().toString();
                String ph =  phoneNumber.getText().toString();
                String ema = emailAddress.getText().toString();

                if (check_email && check_phone){
                    // If one of the radio buttons is selected, we can create an account
                    if (patientRadioButton.isChecked() || careProviderRadioButton.isChecked()){

                        if (patientRadioButton.isChecked()){
                            Patient patient;
                            try {
                                // Search to see if the account already exists
                                UserController.SearchPatientTask task = new UserController.SearchPatientTask();
                                task.setProgressBar(progressBar);
                                Patient patient1 = task.execute(username).get();

                                if (patient1.getUserId().equals(username)){
                                    Toast.makeText(getApplicationContext(),
                                            "User ID already exists"
                                            ,Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }catch (Exception e) {
                                try {
                                    patient = new Patient(
                                            username, "Password", na, ph, ema,
                                            new Date(),
                                            'M'
                                    );
                                } catch (LengthOutOfBoundException e1) {
                                    Toast.makeText(getApplicationContext(),
                                            "Your userId is too short (at least 8 characters)"
                                            , Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                patientRadioButton.setChecked(false);
                                new UserController.AddUserTask().execute(patient);
                                clearText();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        }
                        if (careProviderRadioButton.isChecked()) {
                            CareProvider careProvider;
                            // Search to see if the account already exists

                            try {
                                UserController.SearchCareProviderTask task1 = new UserController.SearchCareProviderTask();
                                task1.setProgressBar(progressBar);
                                CareProvider careProvider1 = task1.execute(username).get();

                                if (careProvider1.getUserId().equals(username)) {
                                    Toast.makeText(getApplicationContext(),
                                            "User ID already exists"
                                            , Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                try {
                                    careProvider = new CareProvider(
                                            username, "Password", na, ph, ema,
                                            new Date(),
                                            'M'
                                    );
                                } catch (LengthOutOfBoundException e2) {
                                    Toast.makeText(getApplicationContext(),
                                            "Your userId is too short (at least 8 characters)"
                                            , Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                careProviderRadioButton.setChecked(false);
                                new UserController.AddUserTask().execute(careProvider);
                                clearText();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            }
                        }
                    }
                }
                else if(!check_phone && !check_email){
                    Toast.makeText(getApplicationContext(),
                            "Invalid Email Address and Invalid Phone Number"
                            , Toast.LENGTH_SHORT).show();
                }
                else if(!check_email) {
                    emailAddress.getText().clear();
                    Toast.makeText(getApplicationContext(),
                            "Invalid Email Address"
                            , Toast.LENGTH_SHORT).show();
                }
                else if(!check_phone) {
                    //phoneNumber.getText().clear();
                    Toast.makeText(getApplicationContext(),
                            "Invalid Phone Number"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Gets all the XML elements on the =screen
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
        createButton = findViewById(R.id.createAccountButton);
        patientRadioButton = findViewById(R.id.patientRadioButton);
        careProviderRadioButton = findViewById(R.id.careProviderRadioButton);
        progressBar = findViewById(R.id.creationprogressBar);
    }

    // Clears all the text from the fields
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

    }
}
