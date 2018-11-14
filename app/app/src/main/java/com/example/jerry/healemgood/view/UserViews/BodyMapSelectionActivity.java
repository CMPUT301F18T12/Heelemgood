package com.example.jerry.healemgood.view.UserViews;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.utils.BodyColor;
import com.example.jerry.healemgood.utils.BodyPart;

public class BodyMapSelectionActivity extends AppCompatActivity{
    private float lastTouchX;  // position x
    private float lastTouchY;  // position y
    private BodyColor bodyColor = new BodyColor();  // color under the hood

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_body_selection);
        ImageView imageView = findViewById (R.id.bodyMap);
        imageView.setOnTouchListener(touchListener);
        imageView.setOnClickListener(clickListener);
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent e) {

            // save the X,Y coordinates
            if (e.getActionMasked() == MotionEvent.ACTION_UP) {
                int color = getHotspotColor(R.id.colorMap,(int) e.getX(),(int) e.getY());

                Log.i("Click", "X = "+ e.getX() + " Y = " + e.getY());
                if (bodyColor.getBodyPart(color) == BodyPart.NULL) {  // the position is invalid
                    return false;
                }

                lastTouchX = (int) e.getX();
                lastTouchY = (int) e.getY();
                Toast toast = Toast.makeText(getApplicationContext(), ""+bodyColor.getBodyPart(color), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return true;
            }

            // let the touch event pass on to whoever needs it
            return false;
        }
    };

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // retrieve the stored coordinates
            // use the coordinates for whatever
        }
    };


    public int getHotspotColor (int hotspotId, int x, int y) {
        ImageView img = findViewById (hotspotId);
        if (img == null) {
            Log.d ("BodyMapSelection", "Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d ("ImageAreasActivity", "Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }
}
