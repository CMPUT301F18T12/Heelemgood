package com.example.jerry.healemgood.view.UserViews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.jerry.healemgood.R;

public class accountCreationActivity extends AppCompatActivity {

    private EditText userName;
    private EditText firstName;
    private EditText lastName;
    private EditText birthDay;
    private EditText emailAddress;
    private EditText phoneNumber;
    private EditText password;
    private EditText confirmationPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);


        // Find references to the XML path
        // This includes all user attributes, such as username, password, etc.
        // TODO: Remove duplicate user names
        // TODO: Add in a calendar picker widget


    }
}
