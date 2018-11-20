/*
 *  Class Name: PatientHistoryActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 15, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.view.UserViews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jerry.healemgood.R;

/**
 * Represents a PatientRequestActivity
 * handles all friends requests
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class PatientHistoryActivity extends AppCompatActivity {

    /**
     * Reloads an earlier version of the activity if possible
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_history);
    }
}
