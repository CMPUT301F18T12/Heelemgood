package com.example.jerry.healemgood.view.patientActivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.utils.BodyLocation;

import org.w3c.dom.Text;

public class PatientViewPhotoActivity extends AppCompatActivity {

//    private Record record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_photo);

        Button deleteButton = findViewById(R.id.deleteButton);
        Button backButton = findViewById(R.id.backButton);

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("position",getIntent().getIntExtra("position",0));
                setResult(AppConfig.DELETE,intent);
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });





        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra(AppConfig.BITMAP);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(bitmap);

        TextView labelText = findViewById(R.id.labelTextView);
        labelText.setText(getIntent().getStringExtra(AppConfig.LABEL));

        TextView dateText = findViewById(R.id.dateTextView);
        dateText.setText(getIntent().getStringExtra(AppConfig.DATE));



    }


//    private void loadRecord(){
//        try{
//            record = new RecordController.GetRecordByIdTask().execute(getIntent().getStringExtra(AppConfig.RID)).get();
//        }
//        catch (Exception e){
//            Log.d("ERROR","Fail to load the record");
//        }
//    }
}
