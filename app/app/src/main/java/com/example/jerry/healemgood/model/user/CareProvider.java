package com.example.jerry.healemgood.model.user;

import com.example.jerry.healemgood.model.user.User;

import java.util.ArrayList;
import java.util.Date;

public class CareProvider extends User {

    private ArrayList<Patient> patients;

    public CareProvider(String userId, String password, String fullName, String phoneNum, String email, Date birthday, char gender) {
        super(userId, password, fullName, phoneNum, email, birthday, gender);

    }

    /* Get all the patients */
    public ArrayList<Patient> getPatients() {
        return patients;
    }

    /* Add patient*/
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    /* Get one patient by index */
    public Patient getPatientByIndex(int index) {
        return patients.get(index);
    }



}
