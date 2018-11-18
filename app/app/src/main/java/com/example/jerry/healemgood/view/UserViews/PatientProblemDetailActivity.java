package com.example.jerry.healemgood.view.UserViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.model.problem.Problem;

public class PatientProblemDetailActivity extends AppCompatActivity {


    Button saveButton;
    Button backButton;
    EditText titleInput;
    EditText descriptionInput;
    Problem problem;
    String pId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_problem_detail);
        setTitle("Problem Detail");

        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);
        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        pId = getIntent().getStringExtra(AppConfig.PID);

        loadProblem();
        fillOutDetail();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProblem();
                finish();
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void loadProblem(){
        try{
            problem = new ProblemController.GetProblemByIdTask().execute(pId).get();

        }
        catch (Exception e){
            Log.d("Error","Fail to load the problem");
        }
    }



    private void fillOutDetail(){

        titleInput.setText(problem.getTitle());
        descriptionInput.setText(problem.getDescription());
    }


    private void saveProblem(){
        problem.setTitle(titleInput.getText().toString());
        problem.setDescription(descriptionInput.getText().toString());
        try{
            new ProblemController.UpdateProblemTask().execute(problem).get();
        }
        catch (Exception e){
            Log.d("ERROR","FAIL to update problem");
        }
    }
}
