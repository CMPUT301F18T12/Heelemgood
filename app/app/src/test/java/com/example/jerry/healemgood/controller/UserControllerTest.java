package com.example.jerry.healemgood.controller;

import android.util.Log;

import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({User.class , Log.class})
public class UserControllerTest {

    private Patient patientConstructor(){
        String userid = "userid";
        String password = "password";
        String name = "Joey Wong";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        return new Patient(userid,password,name,phoneNum,email,birthday,gender);
    }

    @Before
    public void setup() {
        PowerMockito.mockStatic(Log.class);
    }

    // Create a new patient, push it onto elastic search.
    // Test using postman or an http request to see if the patient is successfully created and stored
    @Test
    public void createRecordTest(){
        Patient patient = patientConstructor();
        new UserCreationController.addPatientTask().doInBackground(patient);
    }
}
