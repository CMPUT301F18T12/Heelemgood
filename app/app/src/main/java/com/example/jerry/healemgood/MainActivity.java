package com.example.jerry.healemgood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.jerry.healemgood.view.UserViews.accountCreationActivity;
import com.example.jerry.healemgood.view.UserViews.loginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.onCreateAccount();
    }
    public void onCreateAccount(){
        TextView createTextView = findViewById(R.id.createAccountTextView);
        createTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, accountCreationActivity.class));
            }
        });

    }
}
