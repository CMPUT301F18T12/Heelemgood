package com.example.jerry.healemgood.model.user;

import com.example.jerry.healemgood.model.request.Request;
import com.example.jerry.healemgood.model.problem.Problem;

import java.util.ArrayList;
import java.util.Date;

public class Patient extends User {

    private ArrayList<Problem> problems;
    private ArrayList<String> careProviderUserIds; // The userId list of the care providers
    private ArrayList<Request> requests;

    public Patient(String userId, String password, String fullName, String phoneNum, String email, Date birthday, char gender) {
        super(userId, password, fullName, phoneNum, email, birthday, gender);
    }

    /* Get all the problems*/
    public ArrayList<Problem> getProblems() {
        return problems;
    }

    /*Add new problem*/
    public void addProblem(Problem problem) {
        problems.add(problem);
    }

    /* Get single problem by index */
    public Problem getProblemByIndex(int index) {
        return problems.get(index);
    }

    /* Remove one problem*/
    public void deleteProblem(int index) {
        if (index < problems.size()) {
            problems.remove(index);
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

