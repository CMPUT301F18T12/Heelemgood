package com.example.jerry.healemgood.view.careProviderActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.UserViews.CareProviderAddPatientQRCode;

import java.util.ArrayList;

public class CareProviderAddPatientActivity extends AppCompatActivity {
    private EditText patientInput;
    private CareProvider careProvider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_add_patient);

        patientInput = findViewById(R.id.patientIdInput);

        Button addButton = findViewById(R.id.addButton);
        Button saveButton = findViewById(R.id.saveButton);
        Button scanQrCodeButton = findViewById(R.id.scanQRCodeButton);

        loadCareProvider();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPatient();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                try{
                    Thread.sleep(1000);
                }
                catch (Exception e){

                }
                finish();
            }
        });

        scanQrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to the home page of the care provider
                Intent intent = new Intent(getApplicationContext(), CareProviderAddPatientQRCode.class);
                startActivity(intent);
            }
        });


    }

    private void addPatient() {
        String patientIdSearch = patientInput.getText().toString();
        Patient patient = null;
        try{
            patient = (Patient)new UserController.SearchPatientTask().execute(patientIdSearch).get();
        }
        catch (Exception e){
            Log.d("Error","Fail to get the patient");
        }

        if (patient == null){
            Toast.makeText(this, "Patient does not exist!", Toast.LENGTH_LONG).show();
            return;
        }

        ArrayList<String> patientIdsList = careProvider.getPatientsUserIds();
        if (patientIdsList.contains(patientIdSearch)){
            Toast.makeText(this, "Patient had already added!", Toast.LENGTH_LONG).show();
            return;

        }

        careProvider.addPatientUserId(patientIdSearch);
        Toast.makeText(this, " Patient successfully added!", Toast.LENGTH_LONG).show();
    }

    private void save() {
        try{
            new UserController.UpdateUserTask().execute(careProvider).get();
        }
        catch (Exception e){
            Log.d("Error","Fail to save the care provider");
        }
    }


    private void loadCareProvider() {
        String userId = SharedPreferenceUtil.get(getApplicationContext(), AppConfig.USERID);
        try {
            careProvider = (CareProvider) new UserController.SearchCareProviderTask().execute(userId).get();
        } catch (Exception e) {
            Log.d("Error", "fail to load the care provider");
        }
    }
}
