/*
 *  Class Name: CareProviderTest
 *
 *  Version: Version 1.0
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood;

import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @author joeyUalberta
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CareProviderTest {
    /**
     * description : test for constructor
     */
    @Test
    public void providerConstructTest(){
        String userid = "userid";
        String password = "password";
        String name = "Joey Wong";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        try {
            CareProvider male_p =new CareProvider(userid,password,name,phoneNum,email,birthday,gender);
            assertEquals(male_p.getUserId(),userid);
            assertEquals(male_p.getPassword(),password);
            assertEquals(male_p.getFullName(),name);
            assertEquals(male_p.getPhoneNum(),phoneNum);
            assertEquals(male_p.getEmail(),email);
            assertEquals(male_p.getBirthday(),birthday);
            assertEquals(male_p.getGender(),gender);
            // test female
            gender='F';
            CareProvider female_p =new CareProvider(userid,password,name,phoneNum,email,birthday,gender);
            assertEquals(female_p.getGender(),'F');
        }catch (Exception e){}
    }

    public void addPatientIdTest(){
        String userid = "userid";
        String password = "password";
        String name = "Joey Wong";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        try{
            CareProvider male_p =new CareProvider(userid,password,name,phoneNum,email,birthday,gender);
            male_p.addPatientUserId("p1");
            assertEquals(male_p.getPatientUserIdByIndex(0),"p1");
            male_p.addPatientUserId("p2");
            assertEquals(male_p.getPatientUserIdByIndex(1),"p2");
        }catch (Exception e){}
    }

    public void deletePatientIdTest(){
        String userid = "userid";
        String password = "password";
        String name = "Joey Wong";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        try{
            CareProvider male_p =new CareProvider(userid,password,name,phoneNum,email,birthday,gender);
            male_p.addPatientUserId("p1");
            male_p.addPatientUserId("p2");
            male_p.removePatientUserId("p1");
            assertEquals(male_p.getPatientsUserIds().size(),1);
            assertEquals(male_p.getPatientUserIdByIndex(0),"p2");
            male_p.removePatientUserId("p2");
            assertEquals(male_p.getPatientsUserIds().size(),0);
        }catch (Exception e){}
    }
}