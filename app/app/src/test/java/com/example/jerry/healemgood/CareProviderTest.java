package com.example.jerry.healemgood;

import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
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
        CareProvider male_p =new CareProvider(userid,password,name,phoneNum,email,birthday,gender);
        assertEquals(male_p.getUserId(),userid);
        assertEquals(male_p.getPassword(),password);
        assertEquals(male_p.getFullName(),name);
        assertEquals(male_p.getPhoneNum(),phoneNum);
        assertEquals(male_p.getEmail(),email);
        assertEquals(male_p.getBirthday(),birthday);
        assertEquals(male_p.getGender(),gender);
        gender='F';
        // test female
        CareProvider female_p =new CareProvider(userid,password,name,phoneNum,email,birthday,gender);
        assertEquals(female_p.getGender(),'F');
    }

    public void addPatientIdTest(){
        String userid = "userid";
        String password = "password";
        String name = "Joey Wong";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        CareProvider male_p =new CareProvider(userid,password,name,phoneNum,email,birthday,gender);
        male_p.addPatientUserId("p1");
        assertEquals(male_p.getPatientUserIdByIndex(0),"p1");
        male_p.addPatientUserId("p2");
        assertEquals(male_p.getPatientUserIdByIndex(1),"p2");
    }

    public void deletePatientIdTest(){
        String userid = "userid";
        String password = "password";
        String name = "Joey Wong";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        CareProvider male_p =new CareProvider(userid,password,name,phoneNum,email,birthday,gender);
        male_p.addPatientUserId("p1");
        male_p.addPatientUserId("p2");
        male_p.removePatientUserId("p1");
        assertEquals(male_p.getPatientsUserIds().size(),1);
        assertEquals(male_p.getPatientUserIdByIndex(0),"p2");
        male_p.removePatientUserId("p2");
        assertEquals(male_p.getPatientsUserIds().size(),0);
    }
}