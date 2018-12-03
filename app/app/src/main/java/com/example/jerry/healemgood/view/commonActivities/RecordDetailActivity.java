/*
 *  Class Name: PatientRecordDetailActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 17, 2018
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
import android.graphics.Matrix;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.photo.Photo;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.utils.BodyLocation;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.adapter.ImageAdapter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a PatientRecordDetailActivity
 * displays Records details handles Records details changes
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class RecordDetailActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PLACE_PICKER_REQUEST = 2;
    static final int GET_BODY_LOCATION_REQUEST = 3;
    static final int VIEW_PHOTO_REQUEST = 4;


    private Record record;

    private Place place;
    private ImageAdapter imageAdapter;
    private ArrayList<Photo> photoCollection = new ArrayList<Photo>();

    private Button saveButton;

    private Button viewLocationButton;
    private ImageButton photoButton;
    private Button bodyButton;
    private Button editLocationButton;

    private ProgressBar progressBar;


    /**
     * This function will load a previously used instance of the activity
     *
     * @param savedInstanceState savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_record_detail);
        setTitle("Record Detail");
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        loadRecord();

        progressBar = findViewById(R.id.progressBar);

        GridView gridview = (GridView) findViewById(R.id.gridView);
        imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);


        fillOutDetail();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
               showLargePicture(position);
            }
        });

         saveButton = findViewById(R.id.saveButton);

         bodyButton = findViewById(R.id.bodyButton);
         viewLocationButton = findViewById(R.id.viewLocationButton);

        photoButton = findViewById(R.id.photoButton);
        editLocationButton = findViewById(R.id.editLocationButton);

        editLocationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try{
                    startActivityForResult(builder.build(RecordDetailActivity.this), PLACE_PICKER_REQUEST);
                }
                catch (Exception e){
                    Log.d("Error","Place Picker Error");
                }


            }
        });

        bodyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BodyMapViewActivity.class);
                float[] pos = record.getBodyLocationPercent();
                BodyLocation bodyLocation = new BodyLocation(pos[0], pos[1]);
                intent.putExtra(AppConfig.BODYLOCATION,bodyLocation);
                startActivity(intent);
            }
        });


        viewLocationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),ViewLocationActivity.class);
                double[] geoLocation = record.getGeoLocation();
                if (geoLocation != null){
                    intent.putExtra("geoLocation",geoLocation);
                    intent.putExtra("title",record.getTitle());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "No Geo location recorded!"
                            ,Toast.LENGTH_SHORT).show();
                }



            }
        });

        photoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (photoCollection.size() > AppConfig.PHOTO_LIMIT-1){
                    Toast.makeText(RecordDetailActivity.this, "You can take up to "+AppConfig.PHOTO_LIMIT+" photos",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                dispatchTakePictureIntent();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                saveRecord();
                try{
                    Thread.sleep(1500);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finish();
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
            editLocationButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);

        }

        if (!record.isPatientRecord()){
            photoButton.setVisibility(View.GONE);
            editLocationButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
            bodyButton.setVisibility(View.GONE);
            viewLocationButton.setVisibility(View.GONE);
        }
    }

    /**
     * This function shows a bigger picture
     *
     * see  //https://developer.android.com/training/camera/photobasics
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
     * see  //https://developer.android.com/training/camera/photobasics
     *
     */

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        }
        else if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    /**
     * Reloads the activity after doing various intents (ex. taking a picture).
     *
     * @param requestCode requestCode
     * @param resultCode resultCode
     * @param data data
     */
    @Override
    // receive the intent result when the next activity finishes
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Adds the photo just taken to the gallery
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            getLabelInputAndAddPhoto(imageBitmap);
            imageAdapter.addPhoto(imageBitmap);
            imageAdapter.notifyDataSetChanged();

        }


        // Gets the geolocation for the record
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
     * @param imageBitmap the image bitmap
     * @param label the label of the image
     */
    private void addPhoto(Bitmap imageBitmap,String label){
        int bytes = imageBitmap.getRowBytes();
        if (bytes > 65536) {
            Toast.makeText(this,"Your photo is too large (> 65536 bytes)",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        imageBitmap = RotateBitmap(imageBitmap, 90);
        MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap,
                label + new Date().toString(),
                "Taken on: " + new Date().toString());
        photoCollection.add(new Photo(imageBitmap,label));
    }

    private void removePhotoById(int i){
        photoCollection.remove(i);
    }


    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    /**
     * fill record detail
     *
     */

    private void fillOutDetail(){
        EditText titleInput = findViewById(R.id.titleInput);
        EditText descriptionInput = findViewById(R.id.descriptionInput);
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView authorTextView = findViewById(R.id.authorTextView);
        authorTextView.setText(record.getUserId());
        authorTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),ViewContactActivity.class);
                intent.putExtra(AppConfig.USERID,record.getUserId());
                intent.putExtra(AppConfig.ISPATIENT,record.isPatientRecord());
                startActivity(intent);


            }
        });

        titleInput.setText(record.getTitle());
        descriptionInput.setText(record.getDescription());
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateString = simpleDateFormat.format(record.getCreatedDate());
        dateTextView.setText(dateString);

        photoCollection = record.getPhotos();
        for (Photo p: photoCollection){
            imageAdapter.addPhoto(p.getPhoto());
        }
        imageAdapter.notifyDataSetChanged();
    }

    /**
     * load record
     */

    private void loadRecord(){
        String rId = getIntent().getStringExtra(AppConfig.RID);

        try{
            record = new RecordController.GetRecordByIdTask().execute(rId).get();
        }
        catch (Exception e){
            Log.d("ERROR","Fail to load the problem");
            record = null;
        }


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


        record.setPhotos(photoCollection);


        try{
            new RecordController.UpdateRecordTask().execute(record).get();
        }
        catch (Exception e){
            Log.d("Error","Fail to update the record");
        }
    }






}
