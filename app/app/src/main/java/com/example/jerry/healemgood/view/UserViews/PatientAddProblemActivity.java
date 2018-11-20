package com.example.jerry.healemgood.view.UserViews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;

import java.util.Date;

/**
 * Represents a PatientAddProblemActivity
 * allowing a patient to add a problem
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class PatientAddProblemActivity extends AppCompatActivity {

    /**
     * This function will load a previously used instance of the activity
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add_problem);

        final Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProblem();

            }
        });
    }


    /**
     * save the problem user added
     *
     */

    private void saveProblem() {
        EditText titleInput = findViewById(R.id.titleInput);
        EditText descriptionInput = findViewById(R.id.descriptionInput);

        String title = titleInput.getText().toString();
        String description = descriptionInput.getText().toString();
        Problem problem = null;
        try {
            problem = new Problem(title, new Date(), SharedPreferenceUtil.get(this, AppConfig.USERID),description);
        } catch (LengthOutOfBoundException e) {
            Toast.makeText(this, "Title cannot have more than 30 characters",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        try {


            new ProblemController.CreateProblemTask(this).execute(problem).get();

        } catch (Exception e) {
            Log.d("Error", "Fail to create problem");

        }






    }
}
