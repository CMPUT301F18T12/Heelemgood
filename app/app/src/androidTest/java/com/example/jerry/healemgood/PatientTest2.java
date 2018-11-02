package com.example.jerry.healemgood;

import android.support.test.runner.AndroidJUnit4;

import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.request.Request;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Patient Test
 * 1. patientConstructorTest: The class constructors and getters and setters.
 * 2. requestsTest: Tests related to the requests contained in a patient
 * 3. problemsTest: Tests related to the problems contained in a patient
 * 4. careProvidersTest: Tests related to the careProviders contained in a patient
 * @author tw
 * @version 1.0.0
 */
@RunWith(AndroidJUnit4.class)
public class PatientTest2 {
    @Test
    public void userConstructorTest() {
        String userId = "jackb0";
        String password = "12345678";
        String fullName = "Black Jack";
        String phoneNum = "7806425671";
        String email = "jack@realmail.com";
        Date birthday = new Date();
        char gender = 'M';

        Patient user = new Patient(userId, password, fullName, phoneNum, email, birthday, gender);
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

    public void requestsTest() {
        String userId = "jackb0";
        String password = "12345678";
        String fullName = "Black Jack";
        String phoneNum = "7806425671";
        String email = "jack@realmail.com";
        Date birthday = new Date();
        char gender = 'M';

        Patient user = new Patient(userId, password, fullName, phoneNum, email, birthday, gender);

        String id = "Blue Jack";
        String msg = "abc";
        Request request = new Request(id, msg);

        id = "White Jack";
        msg = "edg";
        Request request2 = new Request(id, msg);

        user.addRequest(request);
        user.addRequest(request2);

        ArrayList<Request> requests = user.getRequests();
        assertTrue(requests.contains(request));
        assertTrue(requests.contains(request2));
        assertEquals(request2, user.getRequestByIndex(1));
        assertEquals(request, user.getRequestByIndex(0));

        user.deleteRequest(0);
        assertEquals(request2,user.getRequestByIndex(0));
    }

    public void problemTest() {
        String userId = "jackb0";
        String password = "12345678";
        String fullName = "Black Jack";
        String phoneNum = "7806425671";
        String email = "jack@realmail.com";
        Date birthday = new Date();
        char gender = 'M';

        Patient user = new Patient(userId, password, fullName, phoneNum, email, birthday, gender);

        int pid = 1;
        String title = "Test";
        Date date = new Date();
        Problem problem = new Problem(pid, title, date);

        pid = 2;
        title = "Test2";
        date = new Date();
        Problem problem2 = new Problem(pid, title, date);

        user.addProblem(problem);
        user.addProblem(problem2);

        ArrayList<Problem> problems = user.getProblems();
        assertTrue(problems.contains(problem));
        assertTrue(problems.contains(problem2));
        assertEquals(problem2, user.getProblemByIndex(1));
        assertEquals(problem, user.getProblemByIndex(0));

        user.deleteProblem(0);
        assertEquals(problem2,user.getProblemByIndex(0));
    }

    public void careProvidersTest() {
        String userId = "jackb0";
        String password = "12345678";
        String fullName = "Black Jack";
        String phoneNum = "7806425671";
        String email = "jack@realmail.com";
        Date birthday = new Date();
        char gender = 'M';

        Patient user = new Patient(userId, password, fullName, phoneNum, email, birthday, gender);

        String userId2 = "jackb2";

        user.addCareProviderUserId(userId);
        user.addCareProviderUserId(userId2);

        ArrayList<String> cids = user.getCareProviderUserIds();
        assertTrue(cids.contains(userId));
        assertTrue(cids.contains(userId2));
        assertEquals(userId2, user.getCareProviderUserIdByIndex(1));
        assertEquals(userId, user.getCareProviderUserIdByIndex(0));

        // Deletion ?
    }
}
