package com.example.jerry.healemgood.view.UserViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.view.UserViews.adapter.ProblemAdapter;
import com.example.jerry.healemgood.view.UserViews.adapter.RecordAdapter;

import java.util.ArrayList;

public class PatientRecordActivity extends AppCompatActivity {

    private RecordAdapter recordAdapter;
    private ArrayList<Record> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record);

        Button createRecordButton = findViewById(R.id.createRecordButton);
        createRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BodyMapSelectionActivity.class);
                intent.putExtra(AppConfig.PID,getIntent().getStringExtra(AppConfig.PID));
                startActivity(intent);
            }
        });

        ListView listView = findViewById(R.id.listView);

        loadRecords();

        recordAdapter = new RecordAdapter(this,R.layout.records_list_view_custom_layout,records);

        listView.setAdapter(recordAdapter);


    }
    @Override
    protected void onResume(){

        super.onResume();
        loadRecords();
        recordAdapter.refreshAdapter(records);

    }


    // TODO: THE records are not loaded as expected ( nothing is loaded)
    private void loadRecords(){
        //load the problem by Pid
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
