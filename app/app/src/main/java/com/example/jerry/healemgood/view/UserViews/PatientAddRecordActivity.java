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

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.audiofx.LoudnessEnhancer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.PatientRecord;
import com.example.jerry.healemgood.utils.BodyLocation;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;
import com.example.jerry.healemgood.view.UserViews.adapter.ImageAdapter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

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
    static final int GET_BODY_LOCATION_REQUEST = 3;
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
        Button bodyButton = findViewById(R.id.bodyButton);
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

        bodyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BodyMapSelectionActivity.class);
                intent.putExtra(AppConfig.PID,getIntent().getStringExtra(AppConfig.PID));
                intent.putExtra(AppConfig.BODYLOCATION, getIntent().getSerializableExtra(AppConfig.BODYLOCATION));
                startActivityForResult(intent, GET_BODY_LOCATION_REQUEST);
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



        EditText descriptionInput = findViewById(R.id.descriptionInput);
        String descriptionString = descriptionInput.getText().toString();

        // make a new patient record
        PatientRecord patientRecord;
        try {
            patientRecord = new PatientRecord(getIntent().getStringExtra(AppConfig.PID), recordTitle);
        } catch (LengthOutOfBoundException e) {
            return;
        }
        // set bodyLocation
        BodyLocation bodyLocation = (BodyLocation) getIntent().getSerializableExtra(AppConfig.BODYLOCATION);

        patientRecord.setBodyLocation(bodyLocation.getPart().toString());
        patientRecord.setBodyLocationPercent(bodyLocation.getX(),bodyLocation.getY());

        // set the description of the record
        try {
            patientRecord.setDescription(descriptionString);
        } catch (LengthOutOfBoundException e) {
            return;
        }

        //set the photos of the record
        for (Bitmap photo: photoBitmapCollection){
            patientRecord.addPhoto(photo);
        }

        //set the geolocation
        if (place != null){
            patientRecord.setGeoLocation(place.getLatLng().latitude,place.getLatLng().longitude);
        }




        //load the problem by Pid
        Problem problem;
        try{
            problem = new ProblemController.GetProblemByIdTask().execute(getIntent().getStringExtra(AppConfig.PID)).get();
        }
        catch (Exception e){
            problem = null;
            Log.d("Error","Fail to get the problem by id");
        }

        problem.addRecord(patientRecord);


        // update the problem
        try{
            new ProblemController.UpdateProblemTask().execute(problem).get();
        }
        catch (Exception e){
            Log.d("Error","Fail to update problem");
        }

    }
}
