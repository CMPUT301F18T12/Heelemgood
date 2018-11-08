package com.example.jerry.healemgood.model.record;

import com.example.jerry.healemgood.model.photo.Photo;

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
    private ArrayList<Photo> photos;
    private Date createdDate;
    /* This is a boolean var to determine whether this record is a patient record or care provider record*/
    private boolean isPatientRecord;

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

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }
}
