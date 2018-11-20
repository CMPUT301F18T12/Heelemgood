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

/**
 * Represents a PatientProblemDetailActivity
 * displays problem details handles Problems details changes
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class PatientProblemDetailActivity extends AppCompatActivity {


    Button saveButton;
    Button backButton;
    EditText titleInput;
    EditText descriptionInput;
    Problem problem;
    String pId;

    /**
     * This function will load a previously used instance of the activity
     *
     * @param savedInstanceState
     */

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

    /**
     * load problem
     *
     */

    private void loadProblem(){
        try{
            problem = new ProblemController.GetProblemByIdTask().execute(pId).get();

        }
        catch (Exception e){
            Log.d("Error","Fail to load the problem");
        }
    }


    /**
     * fill problem detail
     *
     */

    private void fillOutDetail(){

        titleInput.setText(problem.getTitle());
        descriptionInput.setText(problem.getDescription());
    }

    /**
     * save problem after changes
     *
     */
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
