/*
 *  Class Name: PatientProblemDetailActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 17, 2018
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

import java.util.Calendar;
import java.util.Date;

/**
 * Represents a PatientProblemDetailActivity
 * displays problem details handles Problems details changes
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class PatientProblemDetailActivity extends AppCompatActivity {


    Button saveButton;
    Button backButton;
    EditText titleInput;
    EditText descriptionInput;
    Problem problem;
    String pId;
    Button changeDateButton;

    static private Date date;


    /**
     * This function will load a previously used instance of the activity
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_problem_detail);
        setTitle("Problem Detail");

        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);
        changeDateButton = findViewById(R.id.changeDateButton);
        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        pId = getIntent().getStringExtra(AppConfig.PID);

        loadProblem();
        fillOutDetail();

        date = problem.getCreatedDate();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProblem();
                finish();
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        changeDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    /**
     * load problem
     *
     */

    private void loadProblem(){
        try{
            problem = new ProblemController.GetProblemByIdsTask().execute(pId).get().get(0);

        }
        catch (Exception e){
            Log.d("Error","Fail to load the problem");
        }
    }


    /**
     * fill problem detail
     *
     */

    private void fillOutDetail(){

        titleInput.setText(problem.getTitle());
        descriptionInput.setText(problem.getDescription());
    }

    /**
     * save problem after changes
     *
     */
    private void saveProblem(){
        // set the title of the record
        try {
            problem.setTitle(titleInput.getText().toString());
        } catch (LengthOutOfBoundException e) {
            Toast.makeText(this,"Your title is too long!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // set the description of the record
        try {
            problem.setDescription(descriptionInput.getText().toString());
        } catch (LengthOutOfBoundException e) {
            Toast.makeText(this,"Your description is too long!",
                    Toast.LENGTH_SHORT).show();
            return;
        }




        problem.setCreatedDate(date);
        try{
            new ProblemController.UpdateProblemTask().execute(problem).get();
        }
        catch (Exception e){
            Log.d("ERROR","FAIL to update problem");
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
