/*
 * Controller Name: ProblemController
 *
 *  Version: Version 1.0
 *
 *  Date: November 11, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.controller;

import android.os.AsyncTask;

import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;

import java.io.IOException;
import java.util.ArrayList;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Represents a ProblemController
 *
 * @author WeakMill98
 * @version 1.0
 * @since 1.0
 */

public class UserControllerDeprecated {

    /**
     * User log into the application
     *
     * @param userId UserControllerDeprecated userId
     * @param password UserControllerDeprecated password
     */
    public static boolean login(String userId,String password) {
        return true;
    }

    /**
     * Create a patient and send it to the server
     *
     * @param patient UserControllerDeprecated patient
     */

    public static boolean createPatient(Patient patient) {
        return true;
    }

    /**
     * Create a care provider and send it to the server
     *
     * @param careProvider UserControllerDeprecated careProvider
     */
    public static boolean createCareProvider(CareProvider careProvider) {
        return true;
    }

    //TODO
    /**
     * Get all users from the server
     */
    public static ArrayList<User> getAllUser() {
        return new ArrayList<User>();
    }

    //TODO
    /**
     *  Get all care providers from the server
     */
    public static ArrayList<CareProvider> getAllCareProvider() {
        return new ArrayList<CareProvider>();
    }

    //TODO
    /**
     * Get all patients from the server
     */
    public static ArrayList<Patient> getAllPatient() {
        return new ArrayList<Patient>();
    }






}
