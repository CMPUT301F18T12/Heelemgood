package com.example.jerry.healemgood.view.commonActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;

import org.w3c.dom.Text;

/**
 * Represents a ViewContactActivity
 * display user contact info
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class ViewContactActivity extends AppCompatActivity {

    private TextView nameText, userIdText, emailText, phoneText;
    private ProgressBar progressBar;

    /**
     * Reloads an earlier version of the activity if possible
     *
     * @param savedInstanceState Bundle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        setTitle("Contact Info");

        nameText = findViewById(R.id.userFullNameTextView);
        userIdText = findViewById(R.id.userIdTextView);
        emailText = findViewById(R.id.userEmailTextView);
        phoneText = findViewById(R.id.userPhoneNumberTextView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        fillOutInfo();

    }

    /**
     * fill out user info
     */

    private void fillOutInfo() {
        User user = null;
        try {
            if (getIntent().getBooleanExtra(AppConfig.ISPATIENT, true)) {
                UserController.SearchPatientTask task = new UserController.SearchPatientTask();
                task.setProgressBar(progressBar);
                user = task.execute(getIntent().getStringExtra(AppConfig.USERID)).get();
                //user = new UserController.SearchPatientTask().execute(getIntent().getStringExtra(AppConfig.USERID)).get();
            }
            else{
                try {
                    UserController.SearchCareProviderTask task1 = new UserController.SearchCareProviderTask();
                    task1.setProgressBar(progressBar);
                    user = task1.execute(getIntent().getStringExtra(AppConfig.USERID)).get();
                    //user = new UserController.SearchCareProviderTask().execute(getIntent().getStringExtra(AppConfig.USERID)).get();
                } catch (Exception e) {
                    try {
                        UserController.SearchCareProviderTask task1 = new UserController.SearchCareProviderTask();
                        task1.setProgressBar(progressBar);
                        user = task1.execute(getIntent().getStringExtra(AppConfig.USERID)).get();
                        //user = new UserController.SearchCareProviderTask().execute(getIntent().getStringExtra(AppConfig.USERID)).get();
                    } catch (Exception ee) {
                        Log.d("Error", "Fail to load the user");
                    }

                    nameText.setText(user.getFullName());
                    userIdText.setText(user.getUserId());
                    emailText.setText(user.getEmail());
                    phoneText.setText(user.getPhoneNum());

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
