/*
 *  Class Name: PatientProblemActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 14, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.view.UserViews;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.controller.SwipeDetector;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.UserViews.adapter.ProblemAdapter;

import java.util.ArrayList;

/**
 * Represents a PatientProblemActivity
 * Handles all functions relating to the homepage of the patient account type
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class PatientProblemActivity extends AppCompatActivity {

    //https://developer.android.com/training/implementing-navigation/nav-drawer
    private DrawerLayout mDrawerLayout;
    private ArrayList<Problem> problems;
    private ProblemAdapter problemAdapter;
    private final int CREATE_PROBLEM = 1;

    /**
     * Reloads an earlier version of the activity if possible
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_problem);

        ListView mListView;
        Button createProblemButton;

        mListView = findViewById(R.id.patientProblemListView);
        createProblemButton = findViewById(R.id.createProblemButton);

        loadProblems();


        problemAdapter = new ProblemAdapter(this,R.layout.problems_list_view_custom_layout,problems);

        mListView.setAdapter(problemAdapter);
        final SwipeDetector swipeDetector = new SwipeDetector();
        mListView.setOnTouchListener(swipeDetector);


        createProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PatientAddProblemActivity.class);
                startActivity(intent);
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(swipeDetector.swipeDetected()) {
                    if(swipeDetector.getAction() == SwipeDetector.Action.RL) {
                        String pId = problems.get(position).getpId();
                        Intent intent = new Intent(PatientProblemActivity.this,PatientRecordActivity.class);
                        intent.putExtra(AppConfig.PID,pId);
                        startActivity(intent);
                    } else if(swipeDetector.getAction() == SwipeDetector.Action.LR){
                        deleteProblem(position);
                    }
                }

            }
        });



        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.navigation_history:
                                startActivity(new Intent(PatientProblemActivity.this, PatientHistoryActivity.class));
                                break;
                            case R.id.navigation_user:
                                startActivity(new Intent(PatientProblemActivity.this, PatientUserActivity.class));
                                break;
                            case R.id.navigation_search:
                                startActivity(new Intent(PatientProblemActivity.this, PatientSearchActivity.class));
                                break;
                            case R.id.navigation_request:
                                startActivity(new Intent(PatientProblemActivity.this, PatientRequestActivity.class));
                                break;

                        }

                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
    }

    @Override
    protected void onResume(){

        super.onResume();
        loadProblems();
        problemAdapter.refreshAdapter(problems);

    }

    private void loadProblems(){
        ProblemController.searchByPatientIds(SharedPreferenceUtil.get(this,AppConfig.USERID));
        try{
            problems = new ProblemController.SearchProblemTask().execute().get();


        }
        catch (Exception e){
            Log.d("Error","Fail to get the problems");
            problems = new ArrayList<Problem>();
        }



    }

    private void deleteProblem(int i){

        try {
            new ProblemController.DeleteProblemTask().execute(problems.get(i)).get();
            problems.remove(i);
            // notify changes
            problemAdapter.refreshAdapter(problems);
        }
        catch (Exception e){
            Log.d("ERROR","FAIL to delete problem");
        }

    }








}
