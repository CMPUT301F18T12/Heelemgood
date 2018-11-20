/*
 *  Class Name: PatientRecordDetailActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 17, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.view.UserViews;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;
import com.example.jerry.healemgood.view.UserViews.adapter.ImageAdapter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

/**
 * Represents a PatientRecordDetailActivity
 * displays Records details handles Records details changes
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class PatientRecordDetailActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PLACE_PICKER_REQUEST = 2;
    static final int GET_BODY_LOCATION_REQUEST = 3;

    Problem problem;
    Record record;

    private Place place;
    private ImageAdapter imageAdapter;
    private ArrayList<Bitmap> photoBitmapCollection = new ArrayList<Bitmap>();

    /**
     * This function will load a previously used instance of the activity
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_record_detail);

        loadRecord();

        GridView gridview = (GridView) findViewById(R.id.gridView);
        imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);



        fillOutDetail();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(PatientRecordDetailActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        Button backButton = findViewById(R.id.backButton);
        Button bodyButton = findViewById(R.id.bodyButton);

        ImageButton photoButton = findViewById(R.id.photoButton);
        Button addLocationButton = findViewById(R.id.editLocationButton);

        addLocationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try{
                    startActivityForResult(builder.build(PatientRecordDetailActivity.this), PLACE_PICKER_REQUEST);
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
                intent.putExtra(AppConfig.BODYLOCATION, record.getBodyLocation());
                startActivityForResult(intent, GET_BODY_LOCATION_REQUEST);
            }

        });


        photoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dispatchTakePictureIntent();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRecord();
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

        // Adds the photo just taken to the gallery
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            addPhoto(imageBitmap);
            imageAdapter.addPhoto(imageBitmap);
            imageAdapter.notifyDataSetChanged();

        }


        // Gets the geolocation for the record
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
     * fill record detail
     *
     */

    private void fillOutDetail(){
        EditText titleInput = findViewById(R.id.titleInput);
        EditText descriptionInput = findViewById(R.id.descriptionInput);
        titleInput.setText(record.getTitle());
        descriptionInput.setText(record.getDescription());

        photoBitmapCollection = record.getPhotos();
        for (Bitmap b:photoBitmapCollection){
            imageAdapter.addPhoto(b);
        }
        imageAdapter.notifyDataSetChanged();
    }

    /**
     * load record
     */

    private void loadRecord(){
        String pId = getIntent().getStringExtra(AppConfig.PID);
        int index = getIntent().getIntExtra(AppConfig.INDEX,0);
        try{
            problem = new ProblemController.GetProblemByIdTask().execute(pId).get();
        }
        catch (Exception e){
            Log.d("ERROR","Fail to load the problem");
            problem = null;
        }

        record = problem.getAllRecords().get(index);


    }

    /**
     * save record
     */
    private void saveRecord(){
        EditText titleInput = findViewById(R.id.titleInput);
        EditText description = findViewById(R.id.descriptionInput);
        try {
            record.setDescription(description.getText().toString());
        } catch (LengthOutOfBoundException e) {
            return;
        }
        try {
            record.setTitle(titleInput.getText().toString());
        } catch (LengthOutOfBoundException e) {
            return;
        }

        if (place != null){
            record.setGeoLocation(place.getLatLng().latitude,place.getLatLng().longitude);

        }

        try {
            record.setPhotos(photoBitmapCollection);
        } catch (LengthOutOfBoundException e) {
            Toast.makeText(this,"Your photo is too large (> 65536 bytes)",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        problem.updateRecordByIndex(getIntent().getIntExtra(AppConfig.INDEX,0),record);
        try{
            new ProblemController.UpdateProblemTask().execute(problem).get();
        }
        catch (Exception e){
            Log.d("Error","Fail to update the record");
        }
    }






}
