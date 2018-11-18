/*
 *  Class Name: PatientRecord
 *
 *  Version: Version 1.0
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.model.record;

import android.location.Location;

import com.example.jerry.healemgood.utils.BodyLocation;

/**
 * Represents a PatientRecord
 *
 * @author xiacijie
 * @version 1.0
 * @see Record
 * @since 1.0
 */

public class PatientRecord extends Record {

    /**
     * This creates a PatientRecord
     *
     * @param pId
     * @param title
     */

    public PatientRecord(String pId,String title){
        super(pId,title,true);

    }

    /**
     * This sets the bodyLocation of a problem
     *
     * @param bodyLocation
     */
    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    /**
     * This finds and returns the location on the body that the problem occurred.
     *
     * @return bodyLocation
     */
    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }
}
