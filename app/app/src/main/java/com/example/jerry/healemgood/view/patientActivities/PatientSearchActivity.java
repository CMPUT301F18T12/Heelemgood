/*
 *  Class Name: PatientSearchActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 15, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.view.patientActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jerry.healemgood.R;

/**
 * Represents a PatientSearchActivity
 * handles all friends search
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class PatientSearchActivity extends AppCompatActivity {

    /**
     * Reloads an earlier version of the activity if possible
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_search);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.titleRadioButton:
                        // do operations specific to this selection

                        break;
                    case R.id.geoRadioButton:
                        // do operations specific to this selection
                        break;
                    case R.id.bodyRadioButton:
                        // do operations specific to this selection
                        break;
                }
            }
        });

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PatientSearchRecordResultActivity.class);
                EditText searchText = findViewById(R.id.searchText);
                String query = searchText.getText().toString();
                if (query.length() == 0){
                    Toast.makeText(getApplicationContext(),
                            "Searching query cannot be empty"
                            ,Toast.LENGTH_SHORT).show();

                    return;
                }
                intent.putExtra("query",query);
                startActivity(intent);
            }
        });



    }

}
