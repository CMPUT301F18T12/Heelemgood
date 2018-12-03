/*
 *  Class Name: Record
 *
 *  Version: Version 1.0
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.model.record;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;

import com.example.jerry.healemgood.model.photo.Photo;
import com.example.jerry.healemgood.utils.BodyLocation;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * Represents a Record
 *
 * @author xiacijie
 * @version 1.0
 * @see PatientRecord
 * @see CareProviderRecord
 * @since 1.0
 */

public class Record {

    /* Record ID*/
    @JestId
    private String rId;
    // pId is the problem ID this record belongs to
    private String pId;
    /**
     * The id of the patient who this record is for
     */
    private String patientId;
    //title of the record
    private String title;
    //descriptions in this record
    private String description;
    /*photos attached to the record, store as String Base64*/
    private ArrayList<Photo> photos;
    /*The date when this record is created*/
    private Date createdDate;
    /* This is a boolean var to determine whether this record is a patient record or care provider record*/
    private boolean isPatientRecord;
    /* geoLocation(optional) of where this problem is created*/
    private double[] geoLocation;
    /*The bodyLocaton */
    private String bodyLocation;

    private float[] bodyLocationPercent;
    /**
     * A problem have to be created before this record can be created;
     *
     * @param pid    which problem this record is attached to
     * @param title   title of the record
     * @param isPatientRecord   indicate whether or not this is patient record or doctor record
     */
    public Record(String pid,String patientId,String title, boolean isPatientRecord) throws LengthOutOfBoundException {
        this.pId = pid;
        this.patientId = patientId;
        if (title.length() > 30) {throw new LengthOutOfBoundException();}
        this.title = title;
        this.createdDate = new Date();
        this.isPatientRecord = isPatientRecord;
        this.photos = new ArrayList<>();
    }

    /**
     * This sets the title of the record.
     *
     * @param title
     */

    public void setTitle(String title) throws LengthOutOfBoundException {
        if (title.length() > 30) {throw new LengthOutOfBoundException();}
        this.title = title;
    }

    /**
     * This gets and returns the title of the record.
     *
     * @return title
     */

    public String getTitle() {
        return title;
    }

    /**
     * This sets the description of the Record
     *
     * @param description
     */

    public void setDescription(String description) throws LengthOutOfBoundException {
        if (description.length() > 300) {
            throw new LengthOutOfBoundException();
        }
        this.description = description;
    }

    /**
     * This gets and returns the description of the record
     *
     * @return
     */

    public String getDescription(){
        return description;
    }



    /**
     * This gets and returns user id of the record;
     *
     * @return
     */

    public String getUserId(){
        return patientId;
    }

    /**
     * Add photos to the list
     *
     *
     * @param photo enter the Photo
     */

    public void addPhoto(Photo photo) {
        photos.add(photo);

    }

    /**
     * Set a collection of new photos
     *
     * @param photos
     *
     */

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = new ArrayList<Photo>();
        for (Photo p : photos){
            addPhoto(p);
        }
    }

    /**
     *  Get photo collection
     *
     *
     * @return photos
     */

    public ArrayList<Photo> getPhotos() {

        return photos;
    }

    /**
     * Get a photo by Index
     *
     * @param index where this photo positon int the photo list
     * @return Bitmap of the photo
     */

    public Photo getPhotoById(int index){
        return photos.get(index);
    }


    /**
     * Gets and returns the date of the record
     *
     * @return record
     *
     */

    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the date of the record.
     *
     * @param createdDate
     */

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Returns whether the record belongs to a patient or not
     *
     * @return isPatientRecord
     */

    public boolean isPatientRecord() {
        return isPatientRecord;
    }

    /**
     * get the record Id
     *
     * @return record Id
     */
    public String getrId() {
        return rId;
    }

    /**
     * Sets the id of the record
     *
     * @param rId
     */

    public void setrId(String rId) {
        this.rId = rId;
    }

    /**
     * Set geo-location from place, then store it as LatLng See https://developers.google.com/places/android
     * @param lat the latitude
     * @param lon the lontitude
     */
    public void setGeoLocation(double lat,double lon) {
        this.geoLocation= new double[2];
        this.geoLocation[0]= lon;
        this.geoLocation[1]=lat;
    }

    /**
     * Returns the GeoLocation
     *
     * @return GeoLocation in LatLng
     */

    public double[] getGeoLocation() {
        return this.geoLocation;
    }

    /**
     * Get the pid of the correspoded problem
     *
     * @return pid
     */

    public String getpId() {
        return pId;
    }

    /**
     * Set the pid (optional, most likely not needed in this application)
     *
     * @param pId
     */

    public void setpId(String pId) {
        this.pId = pId;
    }

    /**
     * Get the body location
     *
     * @return BodyLocation
     */
    public String getBodyLocation() {
        return bodyLocation;
    }

    /**
     * Set the pid (optional, most likely not needed in this application)
     *
     * @param bodyLocation
     */
    public void setBodyLocation(String bodyLocation) {
        this.bodyLocation = bodyLocation;
    }


    /**
     * Set geo-location from place, then store it as LatLng See https://developers.google.com/places/android
     * @param x
     * @param y
     */
    public void setBodyLocationPercent(float x,float y) {
        this.bodyLocationPercent = new float[2];
        this.bodyLocationPercent[0]= x;
        this.bodyLocationPercent[1]=y;
    }

    /**
     * Returns the GeoLocation
     *
     * @return GeoLocation in LatLng
     */

    public float[] getBodyLocationPercent() {
        return this.bodyLocationPercent;
    }


}
