/*
 *  Class Name: PatientHomeActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 14, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.view.UserViews;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.UserViews.adapter.ProblemAdapter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a PatientHomeActivity
 * Handles all functions relating to the homepage of the patient account type
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class PatientHomeActivity extends AppCompatActivity {

    //https://developer.android.com/training/implementing-navigation/nav-drawer
    private DrawerLayout mDrawerLayout;
    private ArrayList<Problem> problems;
    private ProblemAdapter problemAdapter;

    /**
     * Reloads an earlier version of the activity if possible
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_home);

        ListView mListView;
        Button createProblemButton;

        mListView = findViewById(R.id.patientProblemListView);
        createProblemButton = findViewById(R.id.createProblemButton);

        loadProblems();


//        problemAdapter = new ProblemAdapter(this,R.layout.problems_list_view_custom_layout,problems);
//
//        mListView.setAdapter(problemAdapter);

        createProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PatientAddProblemActivity.class);
                startActivity(intent);
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
                                startActivity(new Intent(PatientHomeActivity.this, PatientHistoryActivity.class));
                                break;
                            case R.id.navigation_user:
                                startActivity(new Intent(PatientHomeActivity.this, PatientUserActivity.class));
                                break;
                            case R.id.navigation_search:
                                startActivity(new Intent(PatientHomeActivity.this, PatientSearchActivity.class));
                                break;
                            case R.id.navigation_request:
                                startActivity(new Intent(PatientHomeActivity.this, PatientRequestActivity.class));
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
//        problemAdapter.notifyDataSetChanged();
    }

    private void loadProblems(){
        ProblemController.searchByPatientIds(SharedPreferenceUtil.get(this,AppConfig.USERID));
        try{
            problems = new ProblemController.SearchProblemTask().execute().get();
            Log.d("COUNT",""+problems.size());

        }
        catch (Exception e){
            Log.d("Error","Fail to get the problems");
            problems = new ArrayList<Problem>();
        }


    }








}
