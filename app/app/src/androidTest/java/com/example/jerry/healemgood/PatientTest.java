package com.example.jerry.healemgood;

import android.support.test.runner.AndroidJUnit4;

import com.example.jerry.healemgood.model.request.Request;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import javax.annotation.Nullable;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
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
        assertEquals(male_p.getGender(),'F');
    }
    @Test
    public void patientAddRequest(){
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
        male_p.addRequest(r2);
        assertEquals(male_p.getRequestByIndex(0),r);
        assertEquals(male_p.getRequestByIndex(1),r);
    }
    public void patientDeleteRequest(){
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
        male_p.addRequest(r2);
        male_p.deleteRequest(0);
        assertEquals(male_p.getRequests().size(),1);
        male_p.deleteRequest(0);
        assertEquals(male_p.getRequests().size(),0);
    }
    public  void patientGetRequest(){
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
        assertEquals(male_p.getRequestByIndex(0),r);
        assertEquals(male_p.getRequestByIndex(1),r2);
    }
}
