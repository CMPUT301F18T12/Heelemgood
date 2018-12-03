/*
 *  Class Name: Patient
 *
 *  Version: Version 1.0
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.model.user;

import com.example.jerry.healemgood.model.request.Request;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a patient
 *
 * @author xiacijie
 * @version 1.0
 * @see User
 * @since 1.0
 */
public class Patient extends User {

    private ArrayList<String> problemIds;
    private ArrayList<String> careProviderUserIds; // The userId list of the care providers
    private ArrayList<Request> requests;

    /**
     * Sets up a patient user
     *
     * @param userId user id
     * @param password password
     * @param fullName full name
     * @param phoneNum phone number
     * @param email email
     * @param birthday birthday
     * @param gender gender
     * @throws LengthOutOfBoundException Length of string not in bound
     */
    public Patient(String userId, String password, String fullName, String phoneNum, String email, Date birthday, char gender) throws LengthOutOfBoundException {
        super(userId, password, fullName, phoneNum, email, birthday, gender);
        this.problemIds = new ArrayList<>();
        this.careProviderUserIds = new ArrayList<>();
        this.requests = new ArrayList<>();

    }

    /**
     * Gets arn returns a list of all the problem ids for the patient
     *
     * @return problemIds
     */
    public ArrayList<String> getProblemIds() {
        return problemIds;
    }

    /**
     * Adds the id of a problem to the list of the patient's problems
     *
     * @param problemId problem pid
     */
    public void addProblemId(String problemId) {
        problemIds.add(problemId);
    }

    /**
     * Gets and returns the index of a problem id in the list of problems
     *
     * @param index index
     * @return problemIds.get(index)
     */
    public String getProblemIdByIndex(int index) {
        return problemIds.get(index);
    }

    /**
     * Removes the problem id of a wanted problem (deletes a problem from the list)
     *
     * @param index index
     */
    public void deleteProblemId(int index) {
        if (index < problemIds.size()) {
            problemIds.remove(index);
        }
    }

    /**
     * Gets and returns a list of the ids of all care providers that the patient has
     *
     * @return careProviderUserIds
     */
    public ArrayList<String> getCareProviderUserIds() {
        return careProviderUserIds;
    }

    /**
     * Adds a care provider's id to the list of care providers the patient is seeing
     *
     * @param careProviderUserId care provider uid
     */
    public void addCareProviderUserId(String careProviderUserId) {
        careProviderUserIds.add(careProviderUserId);
    }

    /**
     * Gets and returns the location of a care provider id in the list of care providers for the patient
     *
     * @param index index
     * @return careProviderUserIds.get(index)
     */
    public String getCareProviderUserIdByIndex(int index) {
        return careProviderUserIds.get(index);
    }

    /**
     * Gets and returns a list of all care provider requests
     *
     * @return requests
     */
    public ArrayList<Request> getRequests() {
        return requests;
    }

    /**
     * Gets and returns the location of a specific request from the list of care provider requests
     *
     * @param index index
     * @return requests.get(index)
     */
    public Request getRequestByIndex(int index) {
        return requests.get(index);
    }

    /**
     * Adds a request to the list of care provider requests
     *
     * @param request request
     */
    public void addRequest(Request request) {
        requests.add(request);
    }

    /**
     * Removes a specific request from the list of available requests
     *
     * @param index index
     */
    public void deleteRequest(int index) {
        if (index < requests.size()){
            requests.remove(index);
        }
    }

}

