package com.example.jerry.healemgood.model.record;

import android.location.Location;

/**
 * This is the class for patient record
 * It inherits from the base class Record
 * */
public class PatientRecord extends Record {


    public PatientRecord(String pId,String title){
        super(pId,title,true);

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
