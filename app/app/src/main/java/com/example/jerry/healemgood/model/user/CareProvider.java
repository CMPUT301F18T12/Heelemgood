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

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a CareProvider
 *
 * @author xiacijie
 * @version 1.0
 * @see CareProvider
 * @since 1.0
 */

public class CareProvider extends User {

    private ArrayList<String> patientUserIds; // The user id list of all the patients belongs to this care provider

    public CareProvider(String userId, String password, String fullName, String phoneNum, String email, Date birthday, char gender) {
        super(userId, password, fullName, phoneNum, email, birthday, gender);
        this.patientUserIds = new ArrayList<String>();

    }

    /* Get all the patients user id*/
    public ArrayList<String> getPatientsUserIds() {
        return patientUserIds;
    }

    /* Add patient user id*/
    public void addPatientUserId(String patientUserId) {
        patientUserIds.add(patientUserId);
    }

    /* Get one patient user id by index */
    public String getPatientUserIdByIndex(int index) {
        return patientUserIds.get(index);
    }

    public void removePatientUserId(String patientUserId){patientUserIds.remove(patientUserId);}
}
