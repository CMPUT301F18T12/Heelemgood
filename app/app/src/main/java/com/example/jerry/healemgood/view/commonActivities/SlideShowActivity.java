package com.example.jerry.healemgood.view.commonActivities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.photo.Photo;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.view.adapter.SlideShowAdapter;

import java.util.ArrayList;

public class SlideShowActivity extends AppCompatActivity {

    private SlideShowAdapter adapter;  // the adapter using for the slide show

    /**
     * On create nothing special
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_show);

        ViewPager viewPager = findViewById(R.id.viewPager);  // the ViewPager for displaying slide show
        adapter = new SlideShowAdapter(this);
        viewPager.setAdapter(adapter);

        loadImages();  // load the images and put in the slide show
    }

    /**
     * load the bitmaps (images) by Pid (Problem id)
     */
    private void loadImages(){
        RecordController.searchByProblemIds(getIntent().getStringExtra(AppConfig.PID));
        ArrayList<Record> records;
        try{
            records = new RecordController.SearchRecordTask().execute().get();
        }
        catch (Exception e){
            records = new ArrayList<>();
            Log.d("Error","Fail to get the problem by id");
        }

        for (Record record : records) {  // get a single record from records
            for (Photo photo : record.getPhotos()) {  // get a photo from all the photo objects
                  adapter.addPhoto(photo.getPhoto());  // get the bitmap and add it to the data set
            }
        }
        adapter.notifyDataSetChanged();  // refresh the page

    }
}
