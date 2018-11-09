package com.example.jerry.healemgood.model.record;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;

import com.example.jerry.healemgood.model.photo.Photo;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * This is the base class for record
 * contains basic information that a record should have
 * */
public class Record {

    /* Record ID*/
    @JestId
    private String rId;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    private String pId;
    private String title;
    private String description;
    private ArrayList<String> photos;
    private Date createdDate;
    /* This is a boolean var to determine whether this record is a patient record or care provider record*/
    private boolean isPatientRecord;
    // Added here for simplicity, since elasticsearch might require all documents to have the same field
    protected LatLng geoLocation;
    protected int bodyLocation;
    /**
     * A problem have to be created before this record can be created;
     * @param pid
     * @param title
     * @param isPatientRecord
     */
    public Record(String pid,String title, boolean isPatientRecord){
        this.pId = pid;
        this.title = title;
        this.createdDate = new Date();
        this.isPatientRecord = isPatientRecord;
        this.photos = new ArrayList<>();
    }

    /* Set the title*/
    public void setTitle(String title) {
        this.title = title;
    }

    /* Get the title*/
    public String getTitle() {
        return title;
    }

    /* Set the description */
    public void setDescription(String description) {
        this.description = description;
    }

    /* Get the description*/
    public String getDescription(){
        return description;
    }

    /**
     * Add photos to the list, converting it into base64 format
     * See : https://stackoverflow.com/questions/4830711/how-to-convert-a-image-into-base64-string
     *
     */
    public void addPhoto(String imgPath){
        Bitmap src=BitmapFactory.decodeFile(imgPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        photos.add(encodedImage);
    }

    /**
     *  Get photo collection
     *  See https://stackoverflow.com/questions/4837110/how-to-convert-a-base64-string-into-a-bitmap-image-to-show-it-in-a-imageview
     */
    public ArrayList<Bitmap> getPhotos() {
        ArrayList<Bitmap> photos = new ArrayList<Bitmap>();
        for (String imgString: this.photos){
            byte[] decodedString = Base64.decode(imgString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            photos.add(decodedByte);
        }
        return photos;
    }

    /* Get photo by id*/
    public Bitmap getPhotoById(int id){
        String imgString = this.photos.get(id);
        byte[] decodedString = Base64.decode(imgString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }


    /* Get the date*/
    public Date getCreatedDate() {
        return createdDate;
    }

    /* Set the date*/
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /* a method to know whether it is a patient record or doctor record*/
    public boolean isPatientRecord() {
        return isPatientRecord;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    /*Set the geo-location
    * * See https://developers.google.com/places/android-sdk/placepicker
    * */
    public void setGeoLocation(Place place) {
        this.geoLocation= place.getLatLng();
    }

    /*Get the geo-location
     * */
    public LatLng getGeoLocation() {
        return this.geoLocation;
    }
}
