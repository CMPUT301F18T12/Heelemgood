package com.example.jerry.healemgood.view.patientActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Represents a PatientMapModeActivity
 * display all geolocations on the map and allows user to view the locations
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class PatientMapModeActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private ArrayList<Problem> problems;
    private ArrayList<Record> records = new ArrayList<Record>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Map Mode");

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_patient_view_location);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // Include the OnCreate() method here too, as described above.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        loadAllRecords();
        for (Record r:records){
            if (r.getGeoLocation() != null){
                double[] geoLocation = r.getGeoLocation();
                String title = r.getTitle();
                LatLng place = new LatLng(geoLocation[1],geoLocation[0]);
                googleMap.addMarker(new MarkerOptions().position(place).title(title));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(place));
            }
        }


    }


    /**
     *     load all records from the elastic search
     */

    private void loadAllRecords(){

        RecordController.searchByPatientIds(SharedPreferenceUtil.get(this,AppConfig.USERID));
        try{
            records = new RecordController.SearchRecordTask().execute().get();
            Log.d("=+++++++++++++",records.size()+"");

        }
        catch (Exception e){

            Log.d("Error","Fail to get the problems");
            records = new ArrayList<Record>();
        }


    }
}