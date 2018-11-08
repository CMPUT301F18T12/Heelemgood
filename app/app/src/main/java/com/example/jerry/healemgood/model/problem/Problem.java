package com.example.jerry.healemgood.model.problem;

import com.example.jerry.healemgood.model.record.Record;

import java.util.ArrayList;
import java.util.Date;

/**
 * This is the class for problem
 * Problem contains records
 * */
public class Problem {

    private int pId;
    private String title;
    private ArrayList<Record> records;
    private Date createdDate;

    public Problem(int pId, String title, Date date) {
        this.pId = pId;
        this.title = title;
        this.createdDate = date; // The createdDate of a problem should be the date of the first record
        this.records = new ArrayList<Record>();
    }

    /*Get the problem id*/
    public int getpId() {
        return pId;
    }

    /*Set the problem id*/
    public void setpId(int pId) {
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
    public void addRecord(Record record) {
        records.add(record);
    }

    /* delete a record*/
    public void deleteRecord(int index) {
        if (index < records.size()){
            records.remove(index);
        }

    }

    /*Get all the records*/
    public ArrayList<Record> getRecords() {
        return records;
    }

    /*Get a single record*/
    public Record getRecordByIndex(int index) {
        return records.get(index);
    }
}
