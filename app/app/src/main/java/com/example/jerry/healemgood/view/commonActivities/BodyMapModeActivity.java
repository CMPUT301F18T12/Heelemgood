/*
 *  Class Name: BodyMapSelectionActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 14, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.view.commonActivities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.utils.BodyColor;
import com.example.jerry.healemgood.utils.BodyLocation;
import com.example.jerry.healemgood.utils.BodyPart;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;

import java.util.ArrayList;

/**
 * Represents a BodyMapModeActivity
 * This activity shows all the body parts of a patient on a body map
 *
 * @author TianqiCS
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */


public class BodyMapModeActivity extends AppCompatActivity{
    private float lastTouchX;
    private float lastTouchY;
    private ArrayList<Float> posX = new ArrayList<>();  // position x
    private ArrayList<Float> posY = new ArrayList<>();  // position y
    private BodyColor bodyColor = new BodyColor();  // color under the hood
    //private String bodyString;
    //private BodyLocation body;
    private ArrayList<Record> records;

    /**
     * This function will load a previously used instance of the activity
     *
     * @param savedInstanceState savedInstanceState
     */

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_body_selection);
        setTitle("Body Map Mode");

        ImageView imageView = findViewById (R.id.bodyMap);

        imageView.setOnTouchListener(touchListener);
        imageView.setOnClickListener(clickListener);

        Button continueButton = findViewById(R.id.continueButton);
        continueButton.setVisibility(View.INVISIBLE);

        loadAllRecords();
        displayDots();

    }

    /**
     * draw all the recorded body parts from records
     */
    private void displayDots() {
        for (Record record : records) {
            float[] pos = record.getBodyLocationPercent();
            posX.add(pos[0]);
            posY.add(pos[1]);
        }
        drawDots();
    }

    // This is a listener that deals with clicking on the body part map
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent e) {

            // save the X,Y coordinates
            if (e.getActionMasked() == MotionEvent.ACTION_UP) {

                int colorId = getHotspotColor(R.id.colorMap,(int) e.getX(),(int) e.getY());  // an integer represents the color

                Log.i("Click", "X = "+ e.getX() + " Y = " + e.getY());

                if (bodyColor.getBodyPart(colorId) == BodyPart.NULL) {  // the position is invalid
                    return false;
                }

                Rect rect = new Rect();
                ImageView map = findViewById(R.id.bodyMap);
                // For coordinates location relative to the parent
                map.getLocalVisibleRect(rect);

                // For coordinates location relative to the screen/display
                map.getGlobalVisibleRect(rect);

                lastTouchX = (e.getX()) / rect.width();
                lastTouchY = (e.getY()) / rect.height();

                // body = new BodyLocation(bodyColor.getBodyPart(colorId), lastTouchX, lastTouchY);

                Log.d("x:", ""+lastTouchX);
                Log.d("y:", ""+lastTouchY);


                Toast toast = Toast.makeText(getApplicationContext(), ""+bodyColor.getBodyPart(colorId), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();


                return true;
            }

            // let the touch event pass on to whoever needs it
            return false;
        }
    };

    // This is a listener that records the values entered.
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // retrieve the stored coordinates
            // use the coordinates for whatever
        }
    };


    /**
     * This gets the color of the pixel clicked on
     *
     * @param hotspotId int
     * @param x pixel position x
     * @param y pixel position y
     * @return the color
     */

    public int getHotspotColor (int hotspotId, int x, int y) {
        ImageView img = findViewById (hotspotId);
        if (img == null) {
            Log.d ("BodyMapSelection", "Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspot = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspot == null) {
                Log.d ("BodyMapSelection", "Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspot.getPixel(x, y);
            }
        }
    }

    /**
     * This draw the dot at the target position
     */

    public void drawDots() {
        ImageView imageView = findViewById (R.id.bodyMap);
        // Convert drawable to bitmap
        Drawable drawable = getResources().getDrawable(R.drawable.body_map);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        // Create a new image bitmap and attach a brand new canvas to it
        Bitmap newbitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newbitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        //Draw the bitmap into canvas
        canvas.drawBitmap(bitmap,0,0,null);

        // Draw dots
        for (int i=0; i<posX.size(); i++) {
            canvas.drawCircle(posX.get(i) * bitmap.getWidth(), posY.get(i) * bitmap.getHeight(), 15, paint);
        }
        // Draw canvas to imageView

        imageView.setImageDrawable(new BitmapDrawable(getResources(), newbitmap));
    }


    /**
     * load all records from the elastic search
     */
    private void loadAllRecords(){

        RecordController.searchByPatientIds(getIntent().getStringExtra(AppConfig.USERID));
        try{
            records = new RecordController.SearchRecordTask().execute().get();
        }
        catch (Exception e){

            Log.d("Error","Fail to get the records");
            records = new ArrayList<Record>();
        }
    }
}
