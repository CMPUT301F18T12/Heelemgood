package com.example.jerry.healemgood.model.problem;

import com.example.jerry.healemgood.model.record.Record;

import java.util.ArrayList;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * This is the class for problem
 * Problem contains records
 * */
public class Problem {
    @JestId
    private String pId;
    private String title;
    private ArrayList<String> recordsIDs;
    private Date createdDate;

    public Problem(String title, Date date) {
        this.title = title;
        this.createdDate = date; // The createdDate of a problem should be the date of the first record
        this.recordsIDs = new ArrayList<String>();
    }

    /*Get the problem id*/
    public String getpId() {
        return pId;
    }

    /*Set the problem id*/
    public void setpId(String pId) {
        this.pId = pId;
    }

    /*Set the title*/
    public void setTitle(String title) {
        this.title = title;
    }

    /*Get the title*/
    public String getTitle() {
        return title;
    }

    /*Get the date*/
    public Date getCreatedDate() {
        return createdDate;
    }

    /*Set the date*/
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /*Add record into records*/
    public void addRecordIDs(String record) {
        recordsIDs.add(record);
    }

    /* delete a record*/
    public void deleteRecord(int index) {
        if (index < recordsIDs.size()){
            recordsIDs.remove(index);
        }
    }

    /*Get all the records*/
    public ArrayList<String> getRecords() {
        return recordsIDs;
    }

    /*Get a single record*/
    public String getRecordByIndex(int index) {
        return recordsIDs.get(index);
    }
}
