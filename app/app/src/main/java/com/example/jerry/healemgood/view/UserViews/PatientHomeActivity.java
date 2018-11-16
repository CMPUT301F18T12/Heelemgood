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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;

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

    /**
     * Reloads an earlier version of the activity if possible
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_home);


        Button createRecordButton = findViewById(R.id.createRecordButton);

        createRecordButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                Intent intent = new Intent(PatientHomeActivity.this,BodyMapSelectionActivity.class);
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
                                startActivity(new Intent(PatientHomeActivity.this,PatientHistoryActivity.class));
                                break;
                            case R.id.navigation_user:
                                startActivity(new Intent(PatientHomeActivity.this, PatientUserActivity.class));
                                break;
                            case R.id.navigation_search:
                                startActivity(new Intent(PatientHomeActivity.this, PatientSearchActivity.class));
                                break;
                            case R.id.navigation_request:
                                startActivity(new Intent(PatientHomeActivity.this,PatientRequestActivity.class));
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


}
