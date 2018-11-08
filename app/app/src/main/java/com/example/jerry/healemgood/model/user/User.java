package com.example.jerry.healemgood.model.user;

import java.util.Date;

/**
 * This is the class for user
 * It is a base class contains common info for both patients and care providers
 * */
public abstract class User {

    private String userId;
    private String password;
    private String fullName;
    private String phoneNum;
    private String email;
    private Date birthday;
    private char gender;

    User(String userId, String password, String fullName, String phoneNum, String email, Date birthday, char gender){

        this.userId = userId;
        this.password = password;
        this.fullName = fullName;
        this.phoneNum = phoneNum;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;

    }
    /* Get the user id*/
    public String getUserId() {
        return userId;
    }

    /* Set the user id*/
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /* Get the password*/
    public String getPassword() {
        return password;
    }

    /* Set the password*/
    public void setPassword(String password) {
        this.password = password;
    }

    /* Get the full name of the user*/
    public String getFullName() {
        return fullName;
    }

    /* Set the full name of the user*/
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /* Get the phone number of the user*/
    public String getPhoneNum() {
        return phoneNum;
    }

    /* Set the phone number of the user*/
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /* Get the email of the user*/
    public String getEmail() {
        return email;
    }

    /* Set the email*/
    public void setEmail(String email) {
        this.email = email;
    }

    /* Get the birthday*/
    public Date getBirthday() {
        return birthday;
    }

    /* Set the birthday*/
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /* Get the gender*/
    public char getGender() {
        return gender;
    }

    /* Set the gender*/
    public void setGender(char gender) {
        this.gender = gender;
    }


}
