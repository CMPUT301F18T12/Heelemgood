/*
 *  Class Name: PatientAllRecordActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 17, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.view.commonActivities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.utils.NetworkUtil;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.adapter.RecordAdapter;
import com.example.jerry.healemgood.view.patientActivities.BodyMapSelectionActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientProblemDetailActivity;

import java.util.ArrayList;

/**
 * Represents a PatientAllProblemActivity
 * displays all records and handles all functions relates to records
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class AllRecordActivity extends AppCompatActivity {

    private RecordAdapter recordAdapter;
    private ArrayList<Record> records;
    private Button createRecordButton;
    private Button detailButton;
    private Button slideShowButton;
    private FloatingActionButton refreshRecordsFloatingActionButton;
    private ProgressBar progressBar;

    /**
     * Handles loading an older version of the activity
     *
     * @param savedInstanceState savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_all_record);
        setTitle("Record");

        createRecordButton = findViewById(R.id.createRecordButton);
        refreshRecordsFloatingActionButton = findViewById(R.id.refreshRecordsFloatingActionButton);
        progressBar = findViewById(R.id.recordprogressBar);
        // progressBar.setVisibility(View.INVISIBLE);

        // For when the user wants to create a record
        createRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (SharedPreferenceUtil.get(getApplicationContext(),AppConfig.ISPATIENT).equals(AppConfig.TRUE)){
                     intent = new Intent(getApplicationContext(), BodyMapSelectionActivity.class);
                }
                else{
                     intent = new Intent(getApplicationContext(),AddRecordActivity.class);
                }

                intent.putExtra(AppConfig.PID,getIntent().getStringExtra(AppConfig.PID));
                startActivity(intent);
            }
        });

        // For when the user clicks on the button to refresh
        refreshRecordsFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isNetworkAvailable(AllRecordActivity.this)){
                    recreate();
                    recreate();
                }
                else {
                    recordAdapter.refreshAdapter(records);
                }
            }
        });

        detailButton = findViewById(R.id.detailButton);
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PatientProblemDetailActivity.class);
                intent.putExtra(AppConfig.PID,getIntent().getStringExtra(AppConfig.PID));
                startActivity(intent);
            }
        });

        slideShowButton = findViewById(R.id.slideShowButton);
        slideShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SlideShowActivity.class);
                intent.putExtra(AppConfig.PID,getIntent().getStringExtra(AppConfig.PID));
                startActivity(intent);
            }
        });


        ListView listView = findViewById(R.id.listView);

        loadRecords();

        recordAdapter = new RecordAdapter(this,R.layout.records_list_view_custom_layout,records);

        listView.setAdapter(recordAdapter);

        // set the list view click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetailRecord(position);
            }
        });

        differentiateUserType();
    }

    /**
     * Refresh the activity
     */
    protected void restart() {
        Intent i = new Intent(AllRecordActivity.this, AllRecordActivity.class);
        startActivity(i);
        finish();
    }

    /**
     * hide the layout based on user type
     */
    private void differentiateUserType(){
        if (SharedPreferenceUtil.get(this,AppConfig.ISPATIENT).equals(AppConfig.FALSE)){
            detailButton.setVisibility(View.GONE);
            slideShowButton.setVisibility(View.GONE);
            createRecordButton.setText("Add Comment");
            refreshRecordsFloatingActionButton.setVisibility(View.GONE);
        }
    }

    /**
     * refresh records
     *
     */

    @Override
    protected void onResume(){

        super.onResume();
        if (NetworkUtil.isNetworkAvailable(this)){
            loadRecords();
            recordAdapter.refreshAdapter(records);
        }
    }

    /**
     * show Detail Record
     *
     * @param position int
     */

    private void showDetailRecord(int position){
        Intent intent = new Intent(AllRecordActivity.this, RecordDetailActivity.class);
        intent.putExtra(AppConfig.RID,records.get(position).getrId());
        startActivity(intent);
    }

    /**
     * load the problem by Pid
     *
     */
    private void loadRecords(){

        RecordController.searchByProblemIds(getIntent().getStringExtra(AppConfig.PID));
        System.out.println("---------"+getIntent().getStringExtra(AppConfig.PID));
        try{
            //records = new RecordController.SearchRecordTask().execute().get();
            RecordController.SearchRecordTaskRefresh task = new RecordController.SearchRecordTaskRefresh();
            task.setProgressBar(progressBar);
            records = task.execute().get();
        }
        catch (Exception e){
            records = new ArrayList<Record>();
            Log.d("Error","Fail to get the problem by id");
        }

    }
}
