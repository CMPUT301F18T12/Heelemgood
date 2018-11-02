package com.example.jerry.healemgood;

import android.support.test.runner.AndroidJUnit4;

import com.example.jerry.healemgood.model.user.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * User Test
 * 1. userConstructorTest: The class constructors and getters and setters.
 * @author tw
 * @version 1.0.1
 */
@RunWith(AndroidJUnit4.class)
public class UserTest {
    @Test
    public void userConstructorTest() {
        String userId = "jackb0";
        String password = "12345678";
        String fullName = "Black Jack";
        String phoneNum = "7806425671";
        String email = "jack@realmail.com";
        Date birthday = new Date();
        char gender = 'M';

        User user = new User(userId, password, fullName, phoneNum, email, birthday, gender);
        assertEquals(user.getUserId(), userId);
        assertEquals(user.getPassword(), password);
        assertEquals(user.getFullName(), fullName);
        assertEquals(user.getPhoneNum(), phoneNum);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getBirthday(), birthday);
        assertEquals(user.getGender(), gender);

        String userId2 = "jackb1";
        String password2 = "12345677";
        String fullName2 = "White Jack";
        String phoneNum2 = "7806425672";
        String email2 = "jack2@realmail.com";
        Date birthday2 = new Date();
        char gender2 = 'F';

        user.setUserId(userId2);
        user.setPassword(password2);
        user.setFullName(fullName2);
        user.setPhoneNum(phoneNum2);
        user.setEmail(email2);
        user.setBirthday(birthday2);
        user.setGender(gender2);

        assertEquals(user.getUserId(), userId2);
        assertEquals(user.getPassword(), password2);
        assertEquals(user.getFullName(), fullName2);
        assertEquals(user.getPhoneNum(), phoneNum2);
        assertEquals(user.getEmail(), email2);
        assertEquals(user.getBirthday(), birthday2);
        assertEquals(user.getGender(), gender2);
    }
}
