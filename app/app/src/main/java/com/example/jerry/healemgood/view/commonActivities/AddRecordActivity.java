/*
 *  Class Name: PatientAddRecordActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 14, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.view.commonActivities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.photo.Photo;
import com.example.jerry.healemgood.model.record.CareProviderRecord;
import com.example.jerry.healemgood.model.record.PatientRecord;
import com.example.jerry.healemgood.utils.BodyLocation;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.adapter.ImageAdapter;
//import com.example.jerry.healemgood.view.patientActivities.BodyMapViewActivity;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class AddRecordActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PLACE_PICKER_REQUEST = 2;
    static final int GET_BODY_LOCATION_REQUEST = 3;
    static final int VIEW_PHOTO_REQUEST = 4;

    // for display the collection of photos
    private ImageAdapter imageAdapter;
    private ArrayList<Photo> photoCollection = new ArrayList<Photo>();
    private Place place;
    private ImageButton photoButton;

    //buttons
    private Button addLocationButton;
    private Button bodyButton;
    private Button saveButton;


    /**
     * Handles loading an older version of the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_add_record);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        addLocationButton =  findViewById(R.id.addLocationButton);
        saveButton = findViewById(R.id.saveButton);
        bodyButton = findViewById(R.id.bodyButton);
        photoButton = findViewById(R.id.photoButton);

        addLocationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try{
                    startActivityForResult(builder.build(AddRecordActivity.this), PLACE_PICKER_REQUEST);
                }
                catch (Exception e){
                    Log.d("Error","Place Picker Error");
                }


            }
        });

        bodyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BodyMapViewActivity.class);
                intent.putExtra(AppConfig.PID,getIntent().getStringExtra(AppConfig.PID));
                intent.putExtra(AppConfig.BODYLOCATION, getIntent().getSerializableExtra(AppConfig.BODYLOCATION));
                startActivityForResult(intent, GET_BODY_LOCATION_REQUEST);
            }

        });

        photoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (photoCollection.size() > AppConfig.PHOTO_LIMIT-1){
                    Toast.makeText(AddRecordActivity.this, "You can take up to "+AppConfig.PHOTO_LIMIT+" photos",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                dispatchTakePictureIntent();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (SharedPreferenceUtil.get(getApplicationContext(),AppConfig.ISPATIENT).equals(AppConfig.TRUE)){
                    savePatientRecord();
                }
                else {
                    saveCareProviderRecord();
                }

                try{
                    Thread.sleep(1000);
                }
                catch (Exception e){

                }
                finish();

            }
        });


        GridView gridview = (GridView) findViewById(R.id.gridView);
        imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                showLargePicture(position);
            }
        });

        differentiateUserType();


    }

    /**
     * hide the layout based on user type
     */
    private void differentiateUserType(){
        if (SharedPreferenceUtil.get(this,AppConfig.ISPATIENT).equals(AppConfig.FALSE)){
            photoButton.setVisibility(View.GONE);
            bodyButton.setVisibility(View.GONE);
            addLocationButton.setVisibility(View.GONE);
        }
    }

    /**
     * This functio shows a bigger picture
     *
     * @see  //https://developer.android.com/training/camera/photobasics
     *
     */
    private void showLargePicture(int position){
        Intent intent = new Intent(getApplicationContext(),ViewPhotoActivity.class);
        intent.putExtra(AppConfig.BITMAP, photoCollection.get(position).getPhoto());
        intent.putExtra(AppConfig.LABEL,photoCollection.get(position).getLabel());
        intent.putExtra(AppConfig.DATE,photoCollection.get(position).getDate().toString());
        startActivityForResult(intent,VIEW_PHOTO_REQUEST);

    }

    /**
     * This function allows users to take a picture with their devices camera.
     *
     * @see  //https://developer.android.com/training/camera/photobasics
     *
     */

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        }
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

        /* Adds the photo just taken to the gallery */
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            getLabelInputAndAddPhoto(imageBitmap);
            imageAdapter.addPhoto(imageBitmap);
            imageAdapter.notifyDataSetChanged();
        }


        /* Gets the geolocation for the record */
        else if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK){
            place = PlacePicker.getPlace(data, this);
            String toastMsg = String.format("Place: %s", place.getName());
            Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
        }
        else if (requestCode == VIEW_PHOTO_REQUEST && resultCode == AppConfig.DELETE){
            int position = data.getIntExtra("position",0);
            removePhotoById(position);
            imageAdapter.removePhotoByIndex(position);
            imageAdapter.notifyDataSetChanged();

        }


    }

    private void removePhotoById(int i){
        photoCollection.remove(i);
    }

    private void getLabelInputAndAddPhoto(final Bitmap b){
        //get the label

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Label");
        final EditText input = new EditText(this);

        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String label = input.getText().toString();
                addPhoto(b,label);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    /**
     * Adds a photo bitmap to the gallery
     *
     * @param imageBitmap
     */
    private void addPhoto(Bitmap imageBitmap,String label){

        int bytes = imageBitmap.getRowBytes();
        if (bytes > 65536) {
            Toast.makeText(this,"Your photo is too large (> 65536 bytes)",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, label ,
                "Taken on: " + new Date().toString());
        photoCollection.add(new Photo(imageBitmap,label));
    }


    /*private void saveImage(Bitmap finalBitmap, String image_name) {

        //String root = Environment.getExternalStorageDirectory().toString();
        //File myDir = new File(getGalleryPath());
        String appDirectoryName = "XYZ";
        File myDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), appDirectoryName);
        myDir.mkdirs();
        String fname = "Image-" + image_name+ ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/Heal");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/Heal/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File("/sdcard/Heal/"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getGalleryPath() {
        String photoDir = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/" + "HealEmGood/";
        return photoDir;
    }*/

    /**
     * Saves everything recorded in the record to the problem in the form of a new record.
     *
     */
    private void savePatientRecord(){
        EditText recordTitleInput = findViewById(R.id.titleInput);
        String recordTitle = recordTitleInput.getText().toString();


        EditText descriptionInput = findViewById(R.id.descriptionInput);
        String descriptionString = descriptionInput.getText().toString();

        // make a new patient record
        PatientRecord patientRecord;
        try {
            patientRecord = new PatientRecord(getIntent().getStringExtra(AppConfig.PID), SharedPreferenceUtil.get(this,AppConfig.USERID),recordTitle);
        } catch (LengthOutOfBoundException e) {
            Toast.makeText(this,"Your title is too long!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // set body location
        BodyLocation bodyLocation = (BodyLocation) getIntent().getSerializableExtra(AppConfig.BODYLOCATION);

        patientRecord.setBodyLocation(bodyLocation.getPart().toString());
        patientRecord.setBodyLocationPercent(bodyLocation.getX(),bodyLocation.getY());

        // set the description of the record
        try {
            patientRecord.setDescription(descriptionString);
        } catch (LengthOutOfBoundException e) {
            Toast.makeText(this,"Your description is too long!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // set the photos of the record
        for (Photo photo: photoCollection){
            patientRecord.addPhoto(photo);
        }

        // set the geolocation

        if (place != null){
            patientRecord.setGeoLocation(place.getLatLng().latitude,place.getLatLng().longitude);
        }

        // save the record
        try{

            new RecordController.CreateRecordTask().execute(patientRecord);

        }
        catch (Exception e){
            Log.d("ERROR","Fail to create the record");
        }



    }

    private void saveCareProviderRecord(){
        EditText recordTitleInput = findViewById(R.id.titleInput);
        String recordTitle = recordTitleInput.getText().toString();
        EditText descriptionInput = findViewById(R.id.descriptionInput);
        String descriptionString = descriptionInput.getText().toString();

        CareProviderRecord careProviderRecord;
        try {
            careProviderRecord = new CareProviderRecord(getIntent().getStringExtra(AppConfig.PID), SharedPreferenceUtil.get(this,AppConfig.USERID),recordTitle);
        } catch (LengthOutOfBoundException e) {
            Toast.makeText(this,"Your title is too long!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // set the description of the record
        try {
            careProviderRecord.setDescription(descriptionString);
        } catch (LengthOutOfBoundException e) {
            Toast.makeText(this,"Your description is too long!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // save the record
        try{
            new RecordController.CreateRecordTask().execute(careProviderRecord);
        }
        catch (Exception e){
            Log.d("ERROR","Fail to create the record");
        }
    }
}
