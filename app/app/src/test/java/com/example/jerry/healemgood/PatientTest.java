/*
 *  Class Name: PatientTest
 *
 *  Version: Version 1.0
 *
 *  Date: November 14, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood;


import android.util.Log;

import com.example.jerry.healemgood.model.request.Request;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 *This handles tests relating to patient requests
 *
 * @author joeyUalberta
 */

public class PatientTest {
    @Test
    public void patientConstructTest(){
        String userid = "userid";
        String password = "password";
        String name = "Joey Wong";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        Patient male_p =new Patient(userid,password,name,phoneNum,email,birthday,gender);
        assertEquals(male_p.getUserId(),userid);
        assertEquals(male_p.getPassword(),password);
        assertEquals(male_p.getFullName(),name);
        assertEquals(male_p.getPhoneNum(),phoneNum);
        assertEquals(male_p.getEmail(),email);
        assertEquals(male_p.getBirthday(),birthday);
        assertEquals(male_p.getGender(),gender);
        assertEquals(male_p.getRequests().size(),0);
        gender='F';
        Patient female_p =new Patient(userid,password,name,phoneNum,email,birthday,gender);
        assertEquals(female_p.getGender(),'F');
    }

    /**
     * Tests adding requests for care providers
     */
    @Test
    public void patientAddRequestTest(){
        String userid = "userid";
        String password = "password";
        String name = "Joey Wong";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        Patient male_p =new Patient(userid,password,name,phoneNum,email,birthday,gender);
        assertEquals(male_p.getRequests().size(),0);
        String cp ="2";
        Request r = new Request(cp,":(");
        Request r2 = new Request(cp, ":)");
        male_p.addRequest(r);
        male_p.addRequest(r2);
        assertEquals(male_p.getRequestByIndex(0),r);
        assertEquals(male_p.getRequestByIndex(1),r2);
    }

    /**
     * Tests delete patient request
     */
    @Test
    public void patientDeleteRequestTest(){
        String userid = "userid";
        String password = "password";
        String name = "Joey Wong";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        Patient male_p =new Patient(userid,password,name,phoneNum,email,birthday,gender);
        assertEquals(male_p.getRequests().size(),0);
        String cp ="2";
        Request r = new Request(cp,":(");
        Request r2 = new Request(cp, ":)");
        male_p.addRequest(r);
        male_p.addRequest(r2);
        male_p.deleteRequest(0);
        assertEquals(male_p.getRequests().size(),1);
        male_p.deleteRequest(0);
        assertEquals(male_p.getRequests().size(),0);
    }

    /**
     * Tests getting the list of requests for patients
     */
    @Test
    public  void patientGetRequestTest(){
        String userid = "userid";
        String password = "password";
        String name = "Joey Wong";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        Patient male_p =new Patient(userid,password,name,phoneNum,email,birthday,gender);
        assertEquals(male_p.getRequests().size(),0);
        String cp ="2a";
        Request r = new Request(cp,":(");
        Request r2 = new Request(cp, ":)");
        male_p.addRequest(r);
        male_p.addRequest(r2);
        assertEquals(male_p.getRequestByIndex(0),r);
        assertEquals(male_p.getRequestByIndex(1),r2);
    }
}
