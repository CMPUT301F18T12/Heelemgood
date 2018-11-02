package com.example.jerry.healemgood;

import android.support.test.runner.AndroidJUnit4;

import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * CareProvider Test
 * 1. careProviderConstructorTest: The class constructors and getters and setters.
 * 2. patientsTest: Tests related to the patients contained by a care provider
 * @author tw
 * @version 1.0.0
 */
@RunWith(AndroidJUnit4.class)
public class CareProviderTest {
    @Test
    public void userConstructorTest() {
        String userId = "jackb0";
        String password = "12345678";
        String fullName = "Black Jack";
        String phoneNum = "7806425671";
        String email = "jack@realmail.com";
        Date birthday = new Date();
        char gender = 'M';

        CareProvider user = new CareProvider(userId, password, fullName, phoneNum, email, birthday, gender);
        assertEquals(user.getUserId(), userId);
        assertEquals(user.getPassword(), password);
        assertEquals(user.getFullName(), fullName);
        assertEquals(user.getPhoneNum(), phoneNum);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getBirthday(), birthday);
        assertEquals(user.getGender(), gender);

        userId = "jackb1";
        password = "12345677";
        fullName = "White Jack";
        phoneNum = "7806425672";
        email = "jack2@realmail.com";
        birthday = new Date();
        gender = 'F';

        user.setUserId(userId);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setPhoneNum(phoneNum);
        user.setEmail(email);
        user.setBirthday(birthday);
        user.setGender(gender);

        assertEquals(user.getUserId(), userId);
        assertEquals(user.getPassword(), password);
        assertEquals(user.getFullName(), fullName);
        assertEquals(user.getPhoneNum(), phoneNum);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getBirthday(), birthday);
        assertEquals(user.getGender(), gender);
    }

    public void patientsTest() {
        String userId = "jackb0";
        String password = "12345678";
        String fullName = "Black Jack";
        String phoneNum = "7806425671";
        String email = "jack@realmail.com";
        Date birthday = new Date();
        char gender = 'M';

        CareProvider user = new CareProvider(userId, password, fullName, phoneNum, email, birthday, gender);
        String pid1 = "Green Jack";
        String pid2 = "Brown Jack";
        user.addPatientUserId(pid1);
        user.addPatientUserId(pid2);
        ArrayList<String> pids = user.getPatientsUserIds();
        assertTrue(pids.contains(pid1));
        assertTrue(pids.contains(pid2));

        assertEquals(user.getPatientUserIdByIndex(0), pid1);
        assertEquals(user.getPatientUserIdByIndex(1), pid2);

        // Deletion ?
    }
}
