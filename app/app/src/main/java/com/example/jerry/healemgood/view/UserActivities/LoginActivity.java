package com.example.jerry.healemgood.view.UserActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.view.UserViews.PatientProblemActivity;


public class LoginActivity extends AppCompatActivity {
    private EditText userNameEditText;
    private String userName;
    private Button signInButton;
    private TextView createAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getAllXML();
        //onCreateAccount();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PatientProblemActivity.class);
                startActivity(intent);
            }
        });
    }

    // Get all XML elements on the view
    public void getAllXML(){
        userNameEditText = findViewById(R.id.userIdEditText);
        signInButton = findViewById(R.id.signInButton);
        createAccountTextView = findViewById(R.id.createAccountTextView);
    }


    public void onCreateAccount(){
        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, AccountCreationActivity.class));
            }
        });
    }
}
