package com.example.jerry.healemgood.view.UserActivities;

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

import java.util.Date;

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
                    Patient patient = new Patient(
                            username, p, na, ph, ema,
                            new Date(),
                            'M'
                    );
                    patientRadioButton.setChecked(false);
                    new UserController.AddUserTask().execute(patient);
                    clearText();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                if (careProviderRadioButton.isChecked()){
                    CareProvider careProvider = new CareProvider(
                            username, p, na, ph, ema,
                            new Date(),
                            'M'
                    );
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
