package com.example.jerry.healemgood.view.commonActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.User;

import org.w3c.dom.Text;

public class ViewContactActivity extends AppCompatActivity {

    private TextView nameText,userIdText,emailText,phoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        setTitle("Contact Info");

        nameText = findViewById(R.id.userFullNameTextView);
        userIdText = findViewById(R.id.userIdTextView);
        emailText = findViewById(R.id.userEmailTextView);
        phoneText = findViewById(R.id.userPhoneNumberTextView);

        fillOutInfo();

    }


    private void fillOutInfo(){
        User user = null;
        try{
            user = new UserController.SearchPatientTask().execute(getIntent().getStringExtra(AppConfig.USERID)).get();
        }
        catch (Exception e){
            Log.d("Error","Fail to load the patient");
        }

        nameText.setText(user.getFullName());
        userIdText.setText(user.getUserId());
        emailText.setText(user.getEmail());
        phoneText.setText(user.getPhoneNum());

    }
}
