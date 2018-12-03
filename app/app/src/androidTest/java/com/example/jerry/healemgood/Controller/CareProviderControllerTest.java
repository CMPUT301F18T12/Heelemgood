package com.example.jerry.healemgood.Controller;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.User;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;
import com.robotium.solo.Solo;

import java.util.Date;

import static org.junit.Assert.assertNotEquals;

/**
 * Patient Test
 * 1. testCreateUser: Tests the add user functionality of the patient controller
 * 2. testSearchUser: Tests the search user functionality of the patient controller
 * 3. testCreateCareProvider: Tests the creation of a care provider
 * @author Weakmill98
 * @version 1.0
 */
public class CareProviderControllerTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public CareProviderControllerTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    /**
     * Tests storing users and searching for users
     *
     */
    public void testCreateUser() {
        try {
            CareProvider careProvider = careProviderConstructor();
            User usera = new UserController.SearchCareProviderTask().execute("heb12345678").get();
            if (!usera.getUserId().equals("heb12345678")){
                new UserController.AddUserTask().execute(careProvider);
                // Add a breakpoint on the next line to view the object before it is deleted
                User user2 = new UserController.SearchCareProviderTask().execute("update12345678").get();
                assertNull(user2);
            }
            User user = new UserController.SearchCareProviderTask().execute("heb12345678").get();
            new UserController.DeleteUserTask().execute(user);
        } catch (Exception e) {
            Log.d("Test", "Testing Error Add");
        }
    }

    /**
     * Tests searching for the user
     *
     */
    public void testSearchUser() {
        User user;
        CareProvider careProvider = careProviderConstructor();
        try {
            // Search for a user that exists
            new UserController.AddUserTask().execute(careProvider);
            user = new UserController.SearchCareProviderTask().execute("heb12345678").get();
            assertEquals("heb12345678", user.getUserId());

            // Search for a user that does not exist
            // The user object returned should not have a name
            user = new UserController.SearchCareProviderTask().execute("jeb12345678").get();
            assertNotEquals("jeb12345678", user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates a test patient
     *
     * @return care provider object
     */
    private CareProvider careProviderConstructor() {
        String userId = "heb12345678";
        String password = "password";
        String name = "Joey";
        String phoneNum = "123456789";
        String email = "abc@ualberta.ca";
        Date birthday = new Date();
        char gender = 'M';
        try {
            return new CareProvider(userId, password, name, phoneNum, email, birthday, gender);
        } catch (LengthOutOfBoundException e) {
            return null;
        }
    }
}
