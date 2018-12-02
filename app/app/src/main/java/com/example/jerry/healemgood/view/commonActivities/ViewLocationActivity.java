package com.example.jerry.healemgood.view.commonActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jerry.healemgood.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Represents a ViewLocationActivity
 * display all Geolocation info
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class ViewLocationActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    /**
     * Reloads an earlier version of the activity if possible
     * @param savedInstanceState Bundle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setTitle("View Location");
        setContentView(R.layout.activity_patient_view_location);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * draw google map & add marker on google map
     * @param googleMap GoogleMap
     */

    //Include the OnCreate() method here too, as described above.
    @Override
    public void onMapReady(GoogleMap googleMap) {

        // get the geo location from the intent
        double[] geoLocation = getIntent().getDoubleArrayExtra("geoLocation");
        //get the title from the intent
        String title = getIntent().getStringExtra("title");
        LatLng place = new LatLng(geoLocation[1],geoLocation[0]);
        googleMap.addMarker(new MarkerOptions().position(place)
                .title(title));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(place));
    }
}




