package com.example.jerry.healemgood.controller;

import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;

import java.util.ArrayList;

public class UserController {

    /*User log into the application*/
    public static boolean login(String userId,String password) {
        return true;
    }

    /*Create a patient and send it to the server*/
    public static boolean createPatient(Patient patient) {
        return true;
    }

    /*Create a care provider and send it to the server*/
    public static boolean createCareProvider(CareProvider careProvider) {
        return true;
    }

    /*Get all users from the server*/
    public static ArrayList<User> getAllUser() {
        return new ArrayList<User>();
    }

    /* Get all care providers from the server */
    public static ArrayList<CareProvider> getAllCareProvider() {
        return new ArrayList<CareProvider>();
    }

    /* Get all patients from the server*/
    public static ArrayList<Patient> getAllPatient() {
        return new ArrayList<Patient>();
    }






}
