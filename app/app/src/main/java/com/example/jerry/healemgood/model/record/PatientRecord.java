package com.example.jerry.healemgood.model.record;

import android.location.Location;

public class PatientRecord extends Record {

    private Location geoLocation;
    private int bodyLocation;

    public PatientRecord(int rId, String title,boolean isPatientRecord){
        super(rId,title,isPatientRecord);
    }

    /*Set the geo-location*/
    public void setGeoLocation(Location geoLocation) {
        this.geoLocation = geoLocation;
    }

    /*Get the geo-location*/
    public Location getGeoLocation() {
        return geoLocation;
    }

    /*Set the body location*/
    public void setBodyLocation(int bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    /*Get the body location*/
    public int getBodyLocation() {
        return bodyLocation;
    }
}
