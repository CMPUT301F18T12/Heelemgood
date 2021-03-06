/*
 *  Class Name: PatientAllProblemActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 17, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.view.patientActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.controller.SwipeDetector;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.utils.NetworkUtil;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.UserViews.UserGenerateQRCode;
import com.example.jerry.healemgood.view.adapter.ProblemAdapter;
import com.example.jerry.healemgood.view.commonActivities.AllRecordActivity;
import com.example.jerry.healemgood.view.commonActivities.BodyMapModeActivity;
import com.example.jerry.healemgood.view.commonActivities.SearchActivity;
import com.example.jerry.healemgood.view.commonActivities.UserActivity;

import java.util.ArrayList;

/**
 * Represents a PatientAllProblemActivity
 * displays all problems and handles all functions relates to navigate bar
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class PatientAllProblemActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Problem> problems;
    private ProblemAdapter problemAdapter;
    private ProgressBar progressBar;
    private FloatingActionButton refreshfloatingButton;
    private boolean isPatient;

    /**
     * Handles loading an older version of the activity
     *
     * @param savedInstanceState savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_all_problem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.INVISIBLE);
        refreshfloatingButton = findViewById(R.id.refreshProblemsFloatingActionButton);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Problem");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.drawerUserId);
        TextView nav_email = (TextView)hView.findViewById(R.id.drawerEmail);
        nav_user.setText(SharedPreferenceUtil.get(this,AppConfig.USERID));
        nav_email.setText(SharedPreferenceUtil.get(this,AppConfig.EMAIL));


        ListView mListView;
        Button createProblemButton;

        mListView = findViewById(R.id.patientListView);
        createProblemButton = findViewById(R.id.createProblemButton);


        createProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PatientAddProblemActivity.class);
                startActivity(intent);
            }
        });

        refreshfloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isNetworkAvailable(PatientAllProblemActivity.this)) {
                    recreate();
                    recreate();
                }else{
                    problemAdapter.refreshAdapter(problems);
                }
            }
        });

        loadProblems();

        problemAdapter = new ProblemAdapter(this,R.layout.problems_list_view_custom_layout,problems);

        mListView.setAdapter(problemAdapter);
        final SwipeDetector swipeDetector = new SwipeDetector();
        mListView.setOnTouchListener(swipeDetector);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(swipeDetector.swipeDetected()) {
                    if(swipeDetector.getAction() == SwipeDetector.Action.LR){
                        deleteProblem(position);
                    }
                }
                else{
                    String pId = problems.get(position).getpId();
                    Intent intent = new Intent(PatientAllProblemActivity.this,AllRecordActivity.class);
                    intent.putExtra(AppConfig.PID,pId);
                    startActivity(intent);
                }

            }
        });

    }

    /**
     * refresh problems
     *
     */

    @Override
    protected void onResume(){
        super.onResume();
        if (NetworkUtil.isNetworkAvailable(this)){
            loadProblems();
            problemAdapter.refreshAdapter(problems);
        }
    }

    /**
     * load problems
     *
     */

    private void loadProblems(){
        ProblemController.searchByPatientIds(SharedPreferenceUtil.get(this, AppConfig.USERID));
        try{
            //problems = new ProblemController.SearchProblemTask().execute().get();
            ProblemController.SearchProblemTask2 task = new ProblemController.SearchProblemTask2();
            task.setProgressBar(progressBar);
            problems = task.execute().get();
        }
        catch (Exception e){
            Log.d("Error","Fail to get the problems");
            problems = new ArrayList<Problem>();
        }
    }

    /**
     * delete problems
     *
     */

    public void deleteProblem(int i){
        Problem p = problems.get(i);
        problems.remove(i);
        problemAdapter.refreshAdapter(problems);

        try {

            // notify changes
            new ProblemController.DeleteProblemTask().execute(p).get();



        }
        catch (Exception e){
            Log.d("ERROR","FAIL to delete problem");
        }

    }


    /**
     *  close drawer
     *
     */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * adds items to the action bar
     *
     * @param menu Menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_all_problem, menu);
        return true;
    }
    /**
     * Handle action bar item clicks.
     *
     * @param item MenuItem
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handle navigation view item clicks.
     *
     * @param item MenuItem
     */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        switch (id) {
            case R.id.navigation_search:
                startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                break;
            case R.id.navigation_user:
                startActivity(new Intent(getApplicationContext(),UserActivity.class));
                break;
            case R.id.navigation_map:
                startActivity(new Intent(getApplicationContext(),PatientMapModeActivity.class));
                break;
            case R.id.navigation_body:
                Intent intent = new Intent(getApplicationContext(),BodyMapModeActivity.class);
                intent.putExtra(AppConfig.USERID, SharedPreferenceUtil.get(this,AppConfig.USERID));
                startActivity(intent);
                break;
            case R.id.navigation_qrcode:  // Generate a QR code
                startActivity(new Intent(getApplicationContext(),UserGenerateQRCode.class));
                break;
        }

        return true;
    }
}
