package com.example.jerry.healemgood.model.user;

import com.example.jerry.healemgood.model.user.User;

import java.util.ArrayList;
import java.util.Date;

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
