package com.example.jerry.healemgood.view.UserViews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.view.UserViews.adapter.ImageAdapter;

public class PatientAddRecordActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

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
                Intent intent = new Intent(PatientAddRecordActivity.this,PatientMapSelection.class);
                startActivity(intent);
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

                finish();
            }
        });



        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(PatientAddRecordActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    //https://developer.android.com/training/camera/photobasics
    // Method that handles camera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    // get the photo taken just now
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            ImageView photoView = findViewById(R.id.imageView);
//            photoView.setImageBitmap(imageBitmap);
        }
    }


    private void save(){
        EditText titleInput = findViewById(R.id.titleInput);
        String titleString = titleInput.getText().toString();

        EditText problemInput = findViewById(R.id.problemInput);
        String problemString = problemInput.getText().toString();

        EditText descriptionInput = findViewById(R.id.descriptionInput);
        String description = descriptionInput.getText().toString();


    }
}
