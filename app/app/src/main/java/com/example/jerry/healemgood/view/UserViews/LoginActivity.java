//package com.example.jerry.healemgood.view.UserViews;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.example.jerry.healemgood.R;
//
//public class LoginActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        this.onCreateAccount();
//    }
//
//    public void onCreateAccount(){
//        TextView createTextView = findViewById(R.id.createAccountTextView);
//
//
//        createTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, AccountCreationActivity.class));
//            }
//        });
//
//
//
//
//
//    }
//
//    public void onSignIn() {
//        Button button = findViewById(R.id.signInButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, BodyMapSelectionActivity.class));
//            }
//        });
//    }
//}
