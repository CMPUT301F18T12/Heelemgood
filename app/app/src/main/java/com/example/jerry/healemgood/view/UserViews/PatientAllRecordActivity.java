/*
 *  Class Name: PatientAllRecordActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 17, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.view.UserViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.view.UserViews.adapter.RecordAdapter;

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

public class PatientAllRecordActivity extends AppCompatActivity {

    private RecordAdapter recordAdapter;
    private ArrayList<Record> records;

    /**
     * Handles loading an older version of the activity
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_all_record);
        setTitle("Record");

        Button createRecordButton = findViewById(R.id.createRecordButton);

        createRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BodyMapSelectionActivity.class);
                intent.putExtra(AppConfig.PID,getIntent().getStringExtra(AppConfig.PID));
                startActivity(intent);
            }
        });

        Button detailButton = findViewById(R.id.detailButton);
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PatientProblemDetailActivity.class);
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


    }

    /**
     * refresh records
     *
     */

    @Override
    protected void onResume(){

        super.onResume();
        loadRecords();
        recordAdapter.refreshAdapter(records);

    }

    /**
     * show Detail Record
     *
     * @param position int
     */

    private void showDetailRecord(int position){
        Intent intent = new Intent(PatientAllRecordActivity.this, PatientRecordDetailActivity.class);
        intent.putExtra(AppConfig.PID,getIntent().getStringExtra(AppConfig.PID));
        intent.putExtra(AppConfig.INDEX,position);
        startActivity(intent);
    }

    /**
     * load the problem by Pid
     *
     */

    // TODO: THE records are not loaded as expected ( nothing is loaded)
    private void loadRecords(){
        // load the problem by Pid
        Problem problem;
        try{
            problem = new ProblemController.GetProblemByIdTask().execute(getIntent().getStringExtra(AppConfig.PID)).get();
        }
        catch (Exception e){
            problem = null;
            Log.d("Error","Fail to get the problem by id");
        }

        records = problem.getAllRecords();

    }
}
