/*
 *  Class Name: User
 *
 *  Version: Version 1.0
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.model.user;

import com.example.jerry.healemgood.utils.LengthOutOfBoundException;

import java.util.Date;

/**
 * Represents a user
 *
 * @author xiacijie
 * @version 1.0
 * @see Patient
 * @see CareProvider
 * @since 1.0
 */
public abstract class User {

    private String userId;
    private String password;
    private String fullName;
    private String phoneNum;
    private String email;
    private Date birthday;
    private char gender;

    /**
     * Creates a new user
     *
     * @param userId
     * @param password
     * @param fullName
     * @param phoneNum
     * @param email
     * @param birthday
     * @param gender
     * @throws LengthOutOfBoundException
     */
    User(String userId, String password, String fullName, String phoneNum, String email, Date birthday, char gender) throws LengthOutOfBoundException {
        if (userId.length() < 8) {
            throw new LengthOutOfBoundException();
        }
        this.userId = userId;
        this.password = password;
        this.fullName = fullName;
        this.phoneNum = phoneNum;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;

    }

    /**
     * Returns the id of the user
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the id of the user
     *
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets and returns the value of the password for the user
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets and returns the full name of the user
     *
     * @return fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name of the user
     *
     * @param fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets and returns the phone number of the user
     *
     * @return phoneNum
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * Sets the phone number for the user
     *
     * @param phoneNum
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * Gets and returns the email of the user
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets and returns the birthday of the user
     *
     * @return birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday of the user
     *
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets and returns the gender of the user
     *
     * @return gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * Sets the gender for the user
     *
     * @param gender
     */
    public void setGender(char gender) {
        this.gender = gender;
    }


}
