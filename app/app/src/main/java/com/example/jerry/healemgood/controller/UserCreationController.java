package com.example.jerry.healemgood.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

import static com.example.jerry.healemgood.controller.ProblemController.setClient;

//TODO: Fix the issue of setClient() being local, assign it a singleton and make it global
// https://stackoverflow.com/questions/12069669/how-can-you-pass-multiple-primitive-parameters-to-asynctask
public class UserCreationController {

    private static JestDroidClient client = null;

    // Add a User to the database
    public static class addUserTask extends AsyncTask<User, Void, Void>{
        protected Void doInBackground(User... users) {
            setClient();
            User user = users[0];
            Index index = new Index.Builder(user).index("Name-Jeff").type("patient").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    System.out.println("OK, it worked");
                    user.setUserId(result.getId());
                }
            } catch (IOException e) {
                System.out.println("lmao, I dun goofed here boyo");
                e.printStackTrace();
            }
            return null;
        }
    }


    // Add a Patient to the database
    public static class addPatientTask extends AsyncTask<Patient, Void, Void> {
        protected Void doInBackground(Patient... patients) {
            setClient();
            User user = patients[0];
            Index index = new Index.Builder(user).index("Name-Jeff").type("patient").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    System.out.println("OK, it worked");
                    user.setUserId(result.getId());
                }
            } catch (IOException e) {
                System.out.println("lmao, I dun goofed here boyo");
                e.printStackTrace();
            }
            return null;
        }
    }


    // Add a Care provider to the database
    public static class addCareProviderTask extends AsyncTask<CareProvider, Void, Void> {
        protected Void doInBackground(CareProvider... careProviders) {
            setClient();
            User user = careProviders[0];
            Index index = new Index.Builder(user).index("Name-Jeff").type("careprovider").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    System.out.println("OK, it worked");
                    user.setUserId(result.getId());
                }
            } catch (IOException e) {
                System.out.println("lmao, I dun goofed here boyo");
                e.printStackTrace();
            }
            return null;
        }
    }


    // Searches to see if a username already exists
    // Returns the object associated with that username
    public static class searchUsername extends AsyncTask<String, Void, ArrayList<User>> {
        protected ArrayList<User> doInBackground(String... username) {
            setClient();
            String usernameQuery = "{\n" +
                    "    \"query\": {\n" +
                    "        \"multi_match\" :{ \n" +
                    "               \"query\": \"" + username[0] + "\",\n" +
                    "               \"fields\": [ \"userId\"]\n" +
                    "    }\n" +
                    "}";
            Search searchUsername = new Search.Builder(usernameQuery).addIndex("Name-Jeff").addType("user").build();
            ArrayList<User> userArrayList = new ArrayList<>();
            try {
                SearchResult usernameResult = client.execute(searchUsername);
                if (usernameResult.isSucceeded()) {
                    List<User> userList = usernameResult.getSourceAsObjectList(User.class);
                    userArrayList.addAll(userList);
                }
            } catch (IOException e) {
                Log.d("Error", " IOexception when executing client");
            }
            return userArrayList;
        }
    }

    // Search for the username of a care provider
    public static class searchCareProviderUsername extends AsyncTask<String, Void, ArrayList<CareProvider>> {
        protected ArrayList<CareProvider> doInBackground(String... username) {
            setClient();
            String usernameQuery = "{\n" +
                    "    \"query\": {\n" +
                    "        \"multi_match\" :{ \n" +
                    "               \"query\": \"" + username[0] + "\",\n" +
                    "               \"fields\": [ \"userId\"]\n" +
                    "    }\n" +
                    "}";
            Search searchUsername = new Search.Builder(usernameQuery).addIndex("Name-Jeff").addType("careprovider").build();
            ArrayList<CareProvider> userArrayList = new ArrayList<>();
            try {
                SearchResult usernameResult = client.execute(searchUsername);
                if (usernameResult.isSucceeded()) {
                    List<CareProvider> userList = usernameResult.getSourceAsObjectList(CareProvider.class);
                    userArrayList.addAll(userList);
                }
            } catch (IOException e) {
                Log.d("Error", " IOexception when executing client");
            }
            return userArrayList;
        }
    }

    // Class used to find the patient profile from searching for its username
    public static class searchPatientUsername extends AsyncTask<String, Void, ArrayList<Patient>> {
        protected ArrayList<Patient> doInBackground(String... username) {
            setClient();
            String usernameQuery = "{\n" +
                    "    \"query\": {\n" +
                    "        \"multi_match\" :{ \n" +
                    "               \"query\": \"" + username[0] + "\",\n" +
                    "               \"fields\": [ \"userId\"]\n" +
                    "    }\n" +
                    "}";
            Search searchUsername = new Search.Builder(usernameQuery).addIndex("Name-Jeff").addType("patient").build();
            ArrayList<Patient> userArrayList = new ArrayList<>();
            try {
                SearchResult usernameResult = client.execute(searchUsername);
                if (usernameResult.isSucceeded()) {
                    List<Patient> userList = usernameResult.getSourceAsObjectList(Patient.class);
                    userArrayList.addAll(userList);
                }
            } catch (IOException e) {
                Log.d("Error", " IOexception when executing client");
            }
            return userArrayList;
        }
    }

/*    public static class searchCareProviderID extends  AsyncTask<String, Void, ArrayList<CareProvider>>{
        protected ArrayList<User> doInBackground(String... keywords) {
            setClient();
            ArrayList<User> userList = new UserCreationController.searchUsername().doInBackground(keywords[0]);
            ArrayList<User> careProviders = new ArrayList<>();
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getClass() == CareProvider.class) {
                    careProviders.add(userList.get(i));
                }
            }
            return careProviders;
        }
    }*/


    // Class used to find the user from the password and username
    // Assume the positioning is username, then password
    // Returns the user object if username and passwords match
    public static class searchUserPasswordandUsername extends AsyncTask<String, Void, ArrayList<User>> {
        protected ArrayList<User> doInBackground(String... keywords) {
            setClient();
            ArrayList<User> userList = new UserCreationController.searchUsername().doInBackground(keywords[0]);
            String passwordQuery = "{\n" +
                    "    \"query\": {\n" +
                    "        \"multi_match\" :{ \n" +
                    "               \"query\": \"" + keywords[0] + "\",\n" +
                    "               \"fields\": [ \"password\"]\n" +
                    "    }\n" +
                    "}";
            Search searchPassword = new Search.Builder(passwordQuery).addIndex("Name-Jeff").addType("user").build();
            ArrayList<User> userArrayList = new ArrayList<>();
            try {
                SearchResult passwordResult = client.execute(searchPassword);
                if (userList.size() != 0 && passwordResult.isSucceeded()) {
                    if (userList.get(0).getPassword().equals(passwordResult.getSourceAsObject(User.class).getPassword())) {
                        List<User> users = passwordResult.getSourceAsObjectList(User.class);
                        userArrayList.addAll(users);
                    }
                }
            } catch (IOException e) {
                Log.d("Error", " IOexception when executing client");
            }
            return userArrayList;
        }
    }


    // Set the client, duplicate code
    private static void setClient() {
        if (client == null) {
            DroidClientConfig config = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/").build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}