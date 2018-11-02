package com.example.jerry.healemgood.model.user;

import com.example.jerry.healemgood.model.request.Request;
import com.example.jerry.healemgood.model.problem.Problem;

import java.util.ArrayList;
import java.util.Date;

/**
 * It is the class for patients
 * It inherits from the base class User*/
public class Patient extends User {

    private ArrayList<String> problemIds;
    private ArrayList<String> careProviderUserIds; // The userId list of the care providers
    private ArrayList<Request> requests;

    public Patient(String userId, String password, String fullName, String phoneNum, String email, Date birthday, char gender) {
        super(userId, password, fullName, phoneNum, email, birthday, gender);
        this.problemIds = new ArrayList<String>();
        this.careProviderUserIds = new ArrayList<String>();
        this.requests = new ArrayList<Request>();

    }

    /* Get all the problems ids*/
    public ArrayList<String> getProblemIds() {
        return problemIds;
    }

    /*Add new problem id*/
    public void addProblemId(String problemId) {
        problemIds.add(problemId);
    }

    /* Get single problem id by index */
    public String getProblemIdByIndex(int index) {
        return problemIds.get(index);
    }

    /* Remove one problem id*/
    public void deleteProblemId(int index) {
        if (index < problemIds.size()) {
            problemIds.remove(index);
        }
    }

    /* Get all the care providers' user id*/
    public ArrayList<String> getCareProviderUserIds() {
        return careProviderUserIds;
    }

    /* Add one care provider user id*/
    public void addCareProviderUserId(String careProviderUserId) {
        careProviderUserIds.add(careProviderUserId);
    }

    /* Get one care provider by index */
    public String getCareProviderUserIdByIndex(int index) {
        return careProviderUserIds.get(index);
    }

    /*Get all the requests*/
    public ArrayList<Request> getRequests() {
        return requests;
    }

    /*Get one request by index*/
    public Request getRequestByIndex(int index) {
        return requests.get(index);
    }

    /* Add one request*/
    public void addRequest(Request request) {
        requests.add(request);
    }

    /* remove one request by index */
    public void deleteRequest(int index) {
        if (index < requests.size()){
            requests.remove(index);
        }
    }

}

