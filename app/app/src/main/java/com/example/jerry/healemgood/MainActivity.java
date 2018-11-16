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
import com.example.jerry.healemgood.view.UserActivities.CareProviderAdapter;
import com.example.jerry.healemgood.view.UserActivities.accountCreationActivity;

public class MainActivity extends AppCompatActivity {
    private Button signInButton;
    private EditText userNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.onCreateAccount();

        signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userNameEditText = findViewById(R.id.userNameEditText);
                try{
                    User user = new UserCreationController.searchUserTask().execute(userNameEditText.getText().toString()).get();
                    if (user.getUserId().equals(userNameEditText.getText().toString())){
                        Intent intent = new Intent(getApplicationContext(), accountCreationActivity.class);
                        startActivity(intent);
                    }
                }catch (Exception e){ }
            }
        });
    }
    public void onCreateAccount(){
        TextView createTextView = findViewById(R.id.createAccountTextView);
        createTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CareProviderAdapter.class));
            }
        });
    }
}
