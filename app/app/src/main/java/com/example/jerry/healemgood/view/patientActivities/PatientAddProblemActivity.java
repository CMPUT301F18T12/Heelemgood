/*
 *  Class Name: PatientAddProblemActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 16, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.view.patientActivities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Represents a PatientAddProblemActivity
 * allowing a patient to add a problem
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class PatientAddProblemActivity extends AppCompatActivity {

    /**
     * This function will load a previously used instance of the activity
     *
     * @param savedInstanceState
     */

    private static Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add_problem);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProblem();

            }
        });

        Button pickDateButton = findViewById(R.id.pickDateButton);
        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

    }


    /**
     * save the problem user added
     *
     */

    private void saveProblem() {
        EditText titleInput = findViewById(R.id.titleInput);
        EditText descriptionInput = findViewById(R.id.descriptionInput);

        String title = titleInput.getText().toString();
        String description = descriptionInput.getText().toString();
        Problem problem = null;
        try {
            if (date == null){
                date = new Date();
            }

            problem = new Problem(title, date, SharedPreferenceUtil.get(this, AppConfig.USERID),description);
        } catch (LengthOutOfBoundException e) {
            Toast.makeText(this, "Title cannot have more than 30 characters",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        try {


            new ProblemController.CreateProblemTask(this).execute(problem).get();

        } catch (Exception e) {
            Log.d("Error", "Fail to create problem");

        }

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            System.out.println("YEAR"+year);
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            date = cal.getTime();

        }
    }

}
