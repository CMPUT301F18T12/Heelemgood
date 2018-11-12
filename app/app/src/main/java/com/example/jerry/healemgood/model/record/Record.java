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
    // pId is the problem ID this record belongs to
    private String pId;
    //title of the record
    private String title;
    //descriptions in this record
    private String description;
    /*photos attached to the record, store as String Base64*/
    private ArrayList<String> photos;
    /*The date when this record is created*/
    private Date createdDate;
    /* This is a boolean var to determine whether this record is a patient record or care provider record*/
    private boolean isPatientRecord;
    /* geoLocation(optional) of where this problem is created*/
    protected LatLng geoLocation;
    /*The bodyLocaton id*/
    protected int bodyLocation;
    /**
     * A problem have to be created before this record can be created;
     * @param pid    which problem this record is attached to
     * @param title   title of the record
     * @param isPatientRecord   indicate whether or not this is patient record or doctor record
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
     * Add photos to the list, then converting it into base64 format and add it to the photo list
     * See : https://stackoverflow.com/questions/4830711/how-to-convert-a-image-into-base64-string
     * @param imgPath enter the imgPath
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

    /**
     * Get aphoto by Index
     * @param index where this photo positon int the photo list
     * @return Bitmap of the photo
     */
    public Bitmap getPhotoById(int index){
        String imgString = this.photos.get(index);
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

    /**get the record Id
     *
     * @return record Id
     */
    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    /**
     * Set geo-location from place, then store it as LatLng See https://developers.google.com/places/android
     * @param place the place is obtained through place picker
     */
    public void setGeoLocation(Place place) {
        this.geoLocation= place.getLatLng();
    }

    /**
     * @return GeoLocation in LatLng
     */
    public LatLng getGeoLocation() {
        return this.geoLocation;
    }

    /**
     * Get the pid of the correspoded problem
     * @return pid
     */
    public String getpId() {
        return pId;
    }

    /**
     * Set the pid (optional, most likely not needed in this application)
     * @param pId
     */
    public void setpId(String pId) {
        this.pId = pId;
    }
}
