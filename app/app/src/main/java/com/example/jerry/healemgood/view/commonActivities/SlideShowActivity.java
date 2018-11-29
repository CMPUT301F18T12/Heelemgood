package com.example.jerry.healemgood.view.commonActivities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewParent;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.photo.Photo;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.view.adapter.SlideShowAdapter;

import java.util.ArrayList;

public class SlideShowActivity extends AppCompatActivity {

    private ArrayList<Record> records;
    private SlideShowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_show);



        ViewPager viewPager = findViewById(R.id.viewPager);
        adapter = new SlideShowAdapter(this);
        viewPager.setAdapter(adapter);

        loadImages();
    }

    /**
     * load the images by Pid
     *
     */

    private void loadImages(){

        RecordController.searchByProblemIds(getIntent().getStringExtra(AppConfig.PID));
        try{
            records = new RecordController.SearchRecordTask().execute().get();
        }
        catch (Exception e){
            records = new ArrayList<Record>();
            Log.d("Error","Fail to get the problem by id");
        }

        for (Record record : records) {
            for (Photo photo : record.getPhotos()) {
                  adapter.addPhoto(photo.getPhoto());
            }
        }
        adapter.notifyDataSetChanged();

    }
}
