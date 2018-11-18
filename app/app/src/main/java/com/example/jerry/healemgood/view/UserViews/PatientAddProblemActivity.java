package com.example.jerry.healemgood.view.UserViews;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.Date;

public class PatientAddProblemActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add_problem);

        final Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProblem();

            }
        });
    }



    private void saveProblem() {
        EditText titleInput = findViewById(R.id.titleInput);
        EditText descriptionInput = findViewById(R.id.descriptionInput);

        String title = titleInput.getText().toString();
        String description = descriptionInput.getText().toString();
        Problem problem = new Problem(title, new Date(), SharedPreferenceUtil.get(this, AppConfig.USERID),description);


        try {


            new ProblemController.CreateProblemTask(this).execute(problem).get();

        } catch (Exception e) {
            Log.d("Error", "Fail to create problem");

        }






    }
}
