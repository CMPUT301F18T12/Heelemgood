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
            assertTrue(user.getUserId().equals("heb12345678"));

            // Search for a user that does not exist
            // The user object returned should not have a name
            user = new UserController.SearchCareProviderTask().execute("jeb12345678").get();
            assertFalse(user.getUserId().equals("jeb12345678"));
        } catch (Exception e) {
        }
    }


    /**
     * Creates a test patient
     *
     * @return
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
