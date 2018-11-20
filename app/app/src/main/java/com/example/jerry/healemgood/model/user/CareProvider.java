/*
 *  Class Name: CareProvider
 *
 *  Version: Version 1.0
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.model.user;

import com.example.jerry.healemgood.model.user.User;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a CareProvider
 *
 * @author xiacijie
 * @version 1.0
 * @see User
 * @since 1.0
 */
public class CareProvider extends User {

    private ArrayList<String> patientUserIds; // The user id list of all the patients belongs to this care provider

    public CareProvider(String userId, String password, String fullName, String phoneNum, String email, Date birthday, char gender) throws LengthOutOfBoundException {
        super(userId, password, fullName, phoneNum, email, birthday, gender);
        this.patientUserIds = new ArrayList<String>();

    }

    /**
     * Gets and returns a list of all patient ids
     *
     * @return patientUserIds
     */
    public ArrayList<String> getPatientsUserIds() {
        return patientUserIds;
    }

    /**
     * Add a patient id to the list of patients
     *
     * @param patientUserId
     */
    public void addPatientUserId(String patientUserId) {
        patientUserIds.add(patientUserId);
    }

    /**
     * Searches and returns the location in the list of patients of a required patient id
     *
     * @param index
     * @return patientUserIds.get(index)
     */
    public String getPatientUserIdByIndex(int index) {
        return patientUserIds.get(index);
    }

    /**
     * Removes a patient from the list of patients
     *
     * @param patientUserId
     */
    public void removePatientUserId(String patientUserId){patientUserIds.remove(patientUserId);}
}
