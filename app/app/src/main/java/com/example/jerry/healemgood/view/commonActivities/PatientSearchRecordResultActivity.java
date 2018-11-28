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
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.adapter.RecordAdapter;
import com.example.jerry.healemgood.view.patientActivities.PatientRecordDetailActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class PatientSearchRecordResultActivity extends AppCompatActivity {

    private ArrayList<Record> records;
    private RecordAdapter recordAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Search Record");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_search_record_result);
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
     * show Detail Record
     *
     * @param position int
     */

    private void showDetailRecord(int position){
        Intent intent = new Intent(PatientSearchRecordResultActivity.this, PatientRecordDetailActivity.class);
        intent.putExtra(AppConfig.RID,records.get(position).getrId());
        startActivity(intent);
    }



    private void loadRecords(){
        Intent intent = getIntent();
        String query = intent.getStringExtra(AppConfig.QUERY);
        double[] geoLocation = intent.getDoubleArrayExtra(AppConfig.GEOLOCATION);
        String bodyLocation = intent.getStringExtra(AppConfig.BODYLOCATION);

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
        try{
            RecordController.searchByPatientIds(SharedPreferenceUtil.get(this,AppConfig.USERID));
            RecordController.searchByKeyword(query);
            records = new RecordController.SearchRecordTask().execute().get();
            System.out.println(records.size());
        }
        catch (Exception e){
            Log.d("Error","Fail to search by keyword");
            records = new ArrayList<Record>();
        }
    }

    private void searchByGeoLocation(double[] geoLocation){
        try{
            RecordController.searchByPatientIds(SharedPreferenceUtil.get(this,AppConfig.USERID));
            RecordController.searchByGeoLocation(new LatLng(geoLocation[1],geoLocation[0]),2);
            records = new RecordController.SearchRecordTask().execute().get();

        }
        catch (Exception e){
            Log.d("Error","Fail to search by geo location");
            records = new ArrayList<Record>();
        }
    }

    private void searchByBodyLocation(String query){
        try{
            RecordController.searchByPatientIds(SharedPreferenceUtil.get(this,AppConfig.USERID));
            RecordController.searchByBodyLocation(query);
            records = new RecordController.SearchRecordTask().execute().get();
        }
        catch (Exception e){
            Log.d("Error","Fail to search by body location");
            records = new ArrayList<Record>();
        }
    }
}
