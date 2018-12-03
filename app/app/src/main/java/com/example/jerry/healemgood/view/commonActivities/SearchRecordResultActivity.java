package com.example.jerry.healemgood.view.commonActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.adapter.RecordAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Represents a RecordSearchActivity
 * handles all Record search
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class SearchRecordResultActivity extends AppCompatActivity {

    private ArrayList<Record> records;
    private RecordAdapter recordAdapter;
    private ProgressBar progressBar;
    private CareProvider careProvider; // if the user is a care provider

    /**
     * Reloads an earlier version of the activity if possible
     * @param savedInstanceState Bundle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Search Record");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_search_record_result);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        if (!isPatient()){
            loadCareProvider();
        }
        loadRecords();

        ListView listView = findViewById(R.id.listView);
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
     * load a CareProvider
     */

    private void loadCareProvider(){
        try{
            UserController.SearchCareProviderTask task = new UserController.SearchCareProviderTask();
            task.setProgressBar(progressBar);
            careProvider = task.execute(SharedPreferenceUtil.get(this,AppConfig.USERID)).get();
            //careProvider = (CareProvider)new UserController.SearchCareProviderTask().execute(SharedPreferenceUtil.get(this,AppConfig.USERID)).get();
        }
        catch (Exception e){
            Log.d("Error","Fail to load the care provider");
        }
    }

    /**
     * handle different user type to get the patient problem list
     */

    private void differentiateUserType(){

        if (isPatient()){
            RecordController.searchByPatientIds(SharedPreferenceUtil.get(this,AppConfig.USERID));
        }
        else{
            ArrayList<String> patientIdsList = careProvider.getPatientsUserIds();
            RecordController.searchByPatientIds(patientIdsList.toArray(new String[patientIdsList.size()]));
        }
    }

    /**
     * check is the user is a patient
     */

    private boolean isPatient(){
        return SharedPreferenceUtil.get(this,AppConfig.ISPATIENT).equals(AppConfig.TRUE);
    }

    /**
     * show Detail Record
     *
     * @param position int
     */

    private void showDetailRecord(int position){
        Intent intent = new Intent(SearchRecordResultActivity.this, RecordDetailActivity.class);
        intent.putExtra(AppConfig.RID,records.get(position).getrId());
        startActivity(intent);
    }

    /**
     * load patient record list
     */

    private void loadRecords(){
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

    /**
     * search problem by keyword
     * @param  query String
     */

    private void searchByKeyword(String query){
        try{

            RecordController.searchByKeyword(query);
            records = new RecordController.SearchRecordTask().execute().get();
            System.out.println(records.size());
        }
        catch (Exception e){
            Log.d("Error","Fail to search by keyword");
            records = new ArrayList<Record>();
        }
    }

    /**
     * search record by GeoLocation
     * @param geoLocation double[]
     */

    private void searchByGeoLocation(double[] geoLocation){
        try{

            RecordController.searchByGeoLocation(new LatLng(geoLocation[1],geoLocation[0]),2);
            records = new RecordController.SearchRecordTask().execute().get();

        }
        catch (Exception e){
            Log.d("Error","Fail to search by geo location");
            records = new ArrayList<Record>();
        }
    }

    /**
     * search record by BodyLocation
     * @param query String
     */

    private void searchByBodyLocation(String query){
        try{

            RecordController.searchByBodyLocation(query);
            records = new RecordController.SearchRecordTask().execute().get();
        }
        catch (Exception e){
            Log.d("Error","Fail to search by body location");
            records = new ArrayList<Record>();
        }
    }
}
