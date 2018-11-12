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
    //pid is auto generated when problem is added to DB
    @JestId
    private String pId;
    //Title of the problem
    private String title;
    private Date createdDate;

    //Id of the patient who created this problem
    private String userId;

    /**
     * @param title     Title of the problem
     * @param date      The date when this problem is created
     * @param userId    The patient who created this problem
     */
    public Problem(String title, Date date,String userId) {
        this.title = title;
        this.createdDate = date; // The createdDate of a problem should be the date of the first record
        this.userId =userId;
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
    
    public String getUserId() {
        return userId;
    }
}
