package com.example.jerry.healemgood.view.commonActivities;

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
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.adapter.ProblemAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchProblemResultActivity extends AppCompatActivity {

    private ArrayList<Problem> problems = new ArrayList<Problem>();
    private ArrayList<Record> records = new ArrayList<Record>();
    private ProblemAdapter problemAdapter;
    private CareProvider careProvider; // if the user is a care provider

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Search Problem");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_search_problem_result);

        if (!isPatient()){
            loadCareProvider();
        }

        loadProblems();

        ListView listView = findViewById(R.id.listView);
        problemAdapter = new ProblemAdapter(this,R.layout.problems_list_view_custom_layout,problems);
        listView.setAdapter(problemAdapter);

        // set the list view click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetainProblem(position);
            }
        });
    }

    private void loadCareProvider(){
        try{
            careProvider = (CareProvider)new UserController.SearchCareProviderTask().execute(SharedPreferenceUtil.get(this,AppConfig.USERID)).get();
        }
        catch (Exception e){
            Log.d("Error","Fail to load the care provider");
        }
    }

    private void differentiateUserType(){

        if (isPatient()){
            ProblemController.searchByPatientIds(SharedPreferenceUtil.get(this,AppConfig.USERID));
            RecordController.searchByPatientIds(SharedPreferenceUtil.get(this,AppConfig.USERID));
        }
        else{
            ArrayList<String> patientIdsList = careProvider.getPatientsUserIds();
            ProblemController.searchByPatientIds(patientIdsList.toArray(new String[patientIdsList.size()]));
            RecordController.searchByPatientIds(patientIdsList.toArray(new String[patientIdsList.size()]));
        }
    }

    // check is the user is a patient
    private boolean isPatient(){
        return SharedPreferenceUtil.get(this,AppConfig.ISPATIENT).equals(AppConfig.TRUE);
    }


    private void loadProblems(){
        Intent intent = getIntent();
        String query = intent.getStringExtra(AppConfig.QUERY);
        double[] geoLocation = intent.getDoubleArrayExtra(AppConfig.GEOLOCATION);
        String bodyLocation = intent.getStringExtra(AppConfig.BODYLOCATION);

        differentiateUserType();

        if (query != null){ // search by keyword
            searchByKeyword(query);
        }
        else if (geoLocation != null){
            searchByGeoLocation(geoLocation);
        }
        else if (bodyLocation != null){
            searchByBodyLocation(bodyLocation);
        }
    }

    private void searchByKeyword(String query){

        ProblemController.searchByKeyword(query);
        try{
            problems = new ProblemController.SearchProblemTask().execute().get();
        }
        catch (Exception e){
            Log.d("Error","Fail to search problems by keyword");
        }
    }

    private void searchByGeoLocation(double[] geoLocation){
        try{
            RecordController.searchByGeoLocation(new LatLng(geoLocation[1],geoLocation[0]),2);
            records = new RecordController.SearchRecordTask().execute().get();

        }
        catch (Exception e){
            Log.d("Error","Fail to search by geo location");
            records = new ArrayList<Record>();
        }
        getProblemsByPids();

    }

    private void searchByBodyLocation(String query){
        try{
            RecordController.searchByBodyLocation(query);
            records = new RecordController.SearchRecordTask().execute().get();
        }
        catch (Exception e){
            Log.d("Error","Fail to search by body location");
            records = new ArrayList<Record>();
        }

        getProblemsByPids();

    }

    private void getProblemsByPids(){
        Set<String> pIds = new HashSet<String>();
        for (Record r: records){
            pIds.add(r.getpId());
        }

        try{
            problems = new ProblemController.GetProblemByIdsTask().execute(pIds.toArray(new String[pIds.size()])).get();
        }
        catch (Exception e){
            Log.d("Error","Fail to get problems by pids");
        }
    }


    private void showDetainProblem(int position){
        Intent intent = new Intent(getApplicationContext(),AllRecordActivity.class);
        intent.putExtra(AppConfig.PID,problems.get(position).getpId());
        startActivity(intent);

    }
}
