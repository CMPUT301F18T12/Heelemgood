package com.example.jerry.healemgood.controller;

import android.os.AsyncTask;

import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;

import java.io.IOException;
import java.util.ArrayList;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

public class UserController {

    //TODO
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

    //TODO
    /*Get all users from the server*/
    public static ArrayList<User> getAllUser() {
        return new ArrayList<User>();
    }

    //TODO
    /* Get all care providers from the server */
    public static ArrayList<CareProvider> getAllCareProvider() {
        return new ArrayList<CareProvider>();
    }

    //TODO
    /* Get all patients from the server*/
    public static ArrayList<Patient> getAllPatient() {
        return new ArrayList<Patient>();
    }






}
