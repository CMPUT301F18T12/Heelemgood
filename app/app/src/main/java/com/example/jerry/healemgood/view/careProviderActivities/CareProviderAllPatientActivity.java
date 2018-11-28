/*
 *  Class Name: PatientAllProblemActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 17, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
//TODO NOT YET FINISHED
package com.example.jerry.healemgood.view.careProviderActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.controller.SwipeDetector;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.adapter.PatientAdapter;
import com.example.jerry.healemgood.view.adapter.ProblemAdapter;
import com.example.jerry.healemgood.view.patientActivities.PatientAddProblemActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientAllRecordActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientMapModeActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientSearchActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientUserActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Represents a PatientAllProblemActivity
 * displays all problems and handles all functions relates to navigate bar
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class CareProviderAllPatientActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Patient> patients;
    private PatientAdapter patientAdapter;

    /**
     * Handles loading an older version of the activity
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.care_provider_patient_list);
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patients");

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
        */


        ListView mListView;
        Button button = findViewById(R.id.addPatientButton);

        mListView = findViewById(R.id.careProviderPatientList);

        loadPatients();


        patientAdapter = new PatientAdapter(this,R.layout.patients_list_view_custom_layout,patients);

        mListView.setAdapter(patientAdapter);
        final SwipeDetector swipeDetector = new SwipeDetector();
        mListView.setOnTouchListener(swipeDetector);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(swipeDetector.swipeDetected()) {
                    if(swipeDetector.getAction() == SwipeDetector.Action.LR){
                        deletePatient(position);
                    }
                }
                else{
                    String uid = patients.get(position).getUserId();
                    //Intent intent = new Intent(CareProviderAllPatientActivity.this,CareProviderAllPatientActivity.class);
                    //intent.putExtra(AppConfig.USERID,uid);
                    //startActivity(intent);
                }

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPatient("xiacijie");
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
        loadPatients();
        patientAdapter.refreshAdapter(patients);

    }

    /**
     * load problems
     *
     */

    private void loadPatients(){
        patients = new ArrayList<>();
        try{
            CareProvider careProvider = (CareProvider) new UserController.SearchCareProviderTask().execute(SharedPreferenceUtil.get(this,AppConfig.USERID)).get();
            for (String p : careProvider.getPatientsUserIds()) {
                Patient patient = (Patient) new UserController.SearchPatientTask().execute(p).get();
                patients.add(patient);
            }
            Log.d("patients",patients.get(0).getUserId());
        }
        catch (Exception e){
            Log.d("Error","Fail to get the patients");
            e.printStackTrace();
            patients = new ArrayList<Patient>();
        }
    }

    private void addPatient(String uid) {
        try {
            CareProvider careProvider = (CareProvider) new UserController.SearchCareProviderTask().execute(SharedPreferenceUtil.get(this,AppConfig.USERID)).get();
            careProvider.addPatientUserId(uid);
            Log.d("careprovider",careProvider.getUserId()+careProvider.getPatientsUserIds().toString());
            new UserController.UpdateUserTask().execute(careProvider);
            loadPatients();
            patientAdapter.refreshAdapter(patients);

        } catch (Exception e) {
            Log.d("error","FAIL to get care provider");
        }
    }

    /**
     * delete problems
     *
     */

    private void deletePatient(int i){

        try {
            CareProvider careProvider = (CareProvider) new UserController.SearchCareProviderTask().execute(SharedPreferenceUtil.get(this,AppConfig.USERID)).get();
            careProvider.removePatientUserId(patients.get(i).getUserId());
            new UserController.UpdateUserTask().execute(careProvider);
            patients.remove(i);
            // notify changes
            patientAdapter.refreshAdapter(patients);
        }
        catch (Exception e){
            Log.d("ERROR","FAIL to delete patient");
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

        if (id == R.id.navigation_search) {
            startActivity(new Intent(getApplicationContext(),PatientSearchActivity.class));

        } else if (id == R.id.navigation_user) {
            startActivity(new Intent(getApplicationContext(),PatientUserActivity.class));
        }
        else if (id == R.id.navigation_map){
            startActivity(new Intent(getApplicationContext(),PatientMapModeActivity.class));
        }


        return true;
    }
}
