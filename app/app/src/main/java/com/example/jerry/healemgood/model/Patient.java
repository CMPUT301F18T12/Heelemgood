package com.example.jerry.healemgood.model;

import java.util.ArrayList;

public class Patient extends User {
    public ArrayList<Integer> getProblemKeys() {
        return problemKeys;
    }

    public void setProblemKeys(ArrayList<Integer> problemKeys) {
        this.problemKeys = problemKeys;
    }

    public ArrayList<String> getCareProviders() {
        return careProviders;
    }

    public void setCareProviders(ArrayList<String> careProviders) {
        this.careProviders = careProviders;
    }

    public ArrayList<Request> getRequests() {
        return Requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        Requests = requests;
    }

    private ArrayList<Integer> problemKeys;
    private ArrayList<String> careProviders;
    private ArrayList<Request> Requests;

}
