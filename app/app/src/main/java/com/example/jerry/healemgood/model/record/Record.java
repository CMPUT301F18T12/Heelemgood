package com.example.jerry.healemgood.model.record;

import com.example.jerry.healemgood.model.photo.Photo;

import java.util.ArrayList;
import java.util.Date;

public class Record {

    /* Record ID*/
    private int rId;
    private String title;
    private String description;
    private ArrayList<Photo> photos;
    private Date createdDate;
    /* This is a boolean var to determine whether this record is a patient record or care provider record*/
    private boolean isPatientRecord;

    public Record(int rId,String title, boolean isPatientRecord){
        this.title = title;
        this.rId = rId;
        this.createdDate = new Date();
        this.isPatientRecord = isPatientRecord;
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

    /* Add photos*/
    public void addPhoto(Photo photo){
        photos.add(photo);
    }

    /* Get photo collection*/
    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    /* Get photo by id*/
    public Photo getPhotoById(int id){
        return photos.get(id);
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
}
