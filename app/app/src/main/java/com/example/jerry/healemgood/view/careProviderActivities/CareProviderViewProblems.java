package com.example.jerry.healemgood.view.careProviderActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.view.adapter.ProblemAdapter;
import com.example.jerry.healemgood.view.commonActivities.AllRecordActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientAllProblemActivity;

import java.util.ArrayList;

public class CareProviderViewProblems extends AppCompatActivity {

    private String userId;
    private ArrayList<Problem> problems = new ArrayList<Problem>();
    private ProblemAdapter problemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_view_problems);

        userId = getIntent().getStringExtra(AppConfig.USERID);
        setTitle(userId +" 's Problems");

        loadProblems();

        ListView listView = findViewById(R.id.listView);
        problemAdapter = new ProblemAdapter(this,R.layout.problems_list_view_custom_layout,problems);

        listView.setAdapter(problemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pId = problems.get(position).getpId();
                Intent intent = new Intent(CareProviderViewProblems.this,AllRecordActivity.class);
                intent.putExtra(AppConfig.PID,pId);
                startActivity(intent);

            }
        });

    }


    /**
     * load problems
     *
     */

    private void loadProblems(){
        ProblemController.searchByPatientIds(userId);
        try{
            problems = new ProblemController.SearchProblemTask().execute().get();


        }
        catch (Exception e){
            Log.d("Error","Fail to get the problems");
            problems = new ArrayList<Problem>();
        }



    }
}
