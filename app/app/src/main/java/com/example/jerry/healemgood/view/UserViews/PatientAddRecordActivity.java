/*
 *  Class Name: PatientAddRecordActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 14, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.view.UserViews;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.PatientRecord;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.UserViews.adapter.ImageAdapter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a PatientAddRecordActivity
 * Handles all functions relating to allowing a patient to post a record for a problem
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class PatientAddRecordActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PLACE_PICKER_REQUEST = 2;

    // for display the collection of photos
    private ImageAdapter imageAdapter;
    private ArrayList<Bitmap> photoBitmapCollection = new ArrayList<Bitmap>();
    private Place place;

    /**
     * Handles loading an older version of the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_add_record);

        Button addLocationButton =  findViewById(R.id.addLocationButton);
        Button saveButton = findViewById(R.id.saveButton);
        ImageButton photoButton = findViewById(R.id.photoButton);

        addLocationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try{
                    startActivityForResult(builder.build(PatientAddRecordActivity.this), PLACE_PICKER_REQUEST);
                }
                catch (Exception e){
                    Log.d("Error","Place Picker Error");
                }


            }
        });


        photoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dispatchTakePictureIntent();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                save();
                finish();
            }
        });


        GridView gridview = (GridView) findViewById(R.id.gridView);
        imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(PatientAddRecordActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }





    /**
     * This function allows users to take a picture with their devices camera.
     *
     * @see  //https://developer.android.com/training/camera/photobasics
     *
     */

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * Reloads the activity after doing various intents (ex. taking a picture).
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    // receive the intent result when the next activity finishes
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /**
         * Adds the photo just taken to the gallery
         *
         */
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            addPhoto(imageBitmap);
            imageAdapter.addPhoto(imageBitmap);
            imageAdapter.notifyDataSetChanged();

        }


        /**
         * Gets the geolocation for the record
         *
         */
        else if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK){
            place = PlacePicker.getPlace(data, this);
            String toastMsg = String.format("Place: %s", place.getName());
            Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Adds a photo bitmap to the gallery
     *
     * @param imageBitmap
     */
    private void addPhoto(Bitmap imageBitmap){
        photoBitmapCollection.add(imageBitmap);

    }

    /**
     * Saves everything recorded in the record to the problem in the form of a new record.
     *
     */
    private void save(){
        EditText recordTitleInput = findViewById(R.id.titleInput);
        String recordTitle = recordTitleInput.getText().toString();

        EditText problemTitleInput = findViewById(R.id.problemInput);
        String problemTitle = problemTitleInput.getText().toString();

        EditText descriptionInput = findViewById(R.id.descriptionInput);
        String descriptionString = descriptionInput.getText().toString();

        // make a new patient record
        PatientRecord patientRecord;

        //Search if the problem title already exists
        ProblemController.initSearchQuery();
        ProblemController.searchByKeyword(problemTitle);
        ProblemController.finalizeSearchQuery();

        ArrayList<Problem> problems; //searching result

        try{
            problems = new ProblemController.SearchProblemTask().execute().get();

        }
        catch (Exception e){
            problems = new ArrayList<Problem>();
        }

        if (problems.size() > 0){ // The problem has already exists
            patientRecord = new PatientRecord(problems.get(0).getpId(),recordTitle);
        }

        else{ // The problem title does not exist, create a new one!
            Problem problem = new Problem(problemTitle,new Date(),SharedPreferenceUtil.get(this,AppConfig.USERID));
            try{
                new ProblemController.CreateProblemTask().execute(problem).get();
            }
            catch (Exception e){
                Log.d("Error","Fail to send problem to elastic search");
            }

            patientRecord = new PatientRecord(problem.getpId(),recordTitle);

        }

        // set the description of the record
        patientRecord.setDescription(descriptionString);

        //set the photos of the record
        for (Bitmap photo: photoBitmapCollection){
            patientRecord.addPhoto(photo);
        }

        //set the geolocation
        patientRecord.setGeoLocation(place.getLatLng().latitude,place.getLatLng().longitude);

        // send patient record to the server
        try{
            new RecordController.CreateRecordTask().execute(patientRecord).get();
        }
        catch (Exception e){
            Log.d("Error","Fail to send record to elastic search");
        }










    }
}
