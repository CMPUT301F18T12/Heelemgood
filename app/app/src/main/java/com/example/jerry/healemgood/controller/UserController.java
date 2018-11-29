/*
 * Controller Name: UserController
 *
 *  Version: Version 1.0
 *
 *  Date: November 11, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */


package com.example.jerry.healemgood.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

import static com.example.jerry.healemgood.controller.ProblemController.setClient;

/**
 * Represents a UserController
 *
 * @author WeakMill98
 * @version 1.0
 * @since 1.0
 */

//TODO: Fix the issue of setClient() being local, assign it a singleton and make it global
// https://stackoverflow.com/questions/12069669/how-can-you-pass-multiple-primitive-parameters-to-asynctask
public class UserController {

    private static JestDroidClient client = null;
    private static String indexName = "cmput301f18t12";

    /**
     * Add a User to the database
     * Can either be a patient or a user
     */
    public static class AddUserTask extends AsyncTask<User, Void, Void>{
        protected Void doInBackground(User... users) {
            setClient();
            String type = users[0].getClass().getSimpleName();
            /*Index index = new Index.Builder(users[0])
                    .index(indexName)
                    .type(type.toLowerCase())
                    .build();*/
            Index index = new Index.Builder(users[0]).index(indexName).type(type.toLowerCase()).id(users[0].getUserId()).build();
            try {
                //wait until connection is avaliable
                OfflineTools.waitForConnection();
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    users[0].setUserId(result.getId());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Delete a user from the DB
     */
    public static class DeleteUserTask extends AsyncTask<User, Void, Void>
    {
        @Override
        protected Void doInBackground(User... users) {
            setClient();

            // Get the username of the user
            // Also get the type of the user
            String userId = users[0].getUserId();
            String className = users[0].getClass().getSimpleName();
            String type = className.toLowerCase();

            // Search query includes the id as well as the type
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"query_string\" : {\n" +
                    "            \"query\" : \"(userId:" + userId + " AND _type:" + type + ")\" \n" +
                    "        }\n" +
                    "    }\n" +
                    "}";

            DeleteByQuery deleteByQuery = new DeleteByQuery.Builder(query)
                    .addIndex(indexName)
                    .addType(type)
                    .build();

            // If searched, then return object, otherwise return null
            try {
                //wait until connection is avaliable
                OfflineTools.waitForConnection();
                client.execute(deleteByQuery);
            } catch (IOException e) {
                Log.d("Succeed", "Failed!");
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Class used to find the user from the password and username
     * Assume the positioning is username
     */
    public static class SearchPatientTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... userNames) {
            setClient();
            String userName = userNames[0];

            // Build the search query
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"query_string\" : {\n" +
                    "            \"query\" : \"(userId:" + userName + ")\" \n" +
                    "        }\n" +
                    "    }\n" +
                    "}";

            Search search = new Search.Builder(query).addIndex(indexName).addType("patient").build();

            // If searched, then return object, otherwise return null
            try {
                SearchResult searchResult = client.execute(search);
                if(searchResult.isSucceeded() && searchResult.getSourceAsStringList().size()>0){
                    return searchResult.getSourceAsObjectList(Patient.class).get(0);
                }
                Log.d("Succeed", "Empty");
            } catch (IOException e) {
                Log.d("Succeed", "Failed!");
                e.printStackTrace();
            }
            return null;
        }
    }
    public static class GetPatientsByIdsTask extends AsyncTask<String, Void, ArrayList<Patient>> {
        @Override
        protected ArrayList<Patient> doInBackground(String... userNames) {
            setClient();
            String userName = userNames[0];
            String query ="{ \n"+
                    "   \"query\": { \n"+
                    "     \"ids\" : { \n"+
                    "        \"values\" : [";
            for (int i =0;i<userNames.length;i++){
                query += "\""+userNames[i]+"\"";
                if(i!=userNames.length-1){
                    query+=",";
                }
            }
            query+="]}}}";
            Search search = new Search.Builder(query).addIndex(indexName).addType("patient").build();

            // If searched, then return object, otherwise return null
            try {
                SearchResult searchResult = client.execute(search);
                ArrayList<Patient> returns = new ArrayList<Patient>();
                if(searchResult.isSucceeded()){
                    Log.d("Succeed", "Not Empty ?"+!returns.isEmpty());
                    returns.addAll(searchResult.getSourceAsObjectList(Patient.class));
                    return returns;
                }
                Log.d("Succeed", "Empty");
            } catch (IOException e) {
                Log.d("Succeed", "Failed!");
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Class used to find the user from the password and username
     * Assume the positioning is username
     */
    public static class SearchCareProviderTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... userNames) {
            setClient();
            String userName = userNames[0];

            // Build the search query
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"query_string\" : {\n" +
                    "            \"query\" : \"(userId:" + userName + ")\" \n" +
                    "        }\n" +
                    "    }\n" +
                    "}";

            Search search = new Search.Builder(query).addIndex(indexName).addType("careprovider").build();
            // If searched, then return object, otherwise return null
            try {
                SearchResult searchResult = client.execute(search);
                if(searchResult.isSucceeded() && searchResult.getSourceAsStringList().size()>0){
                    return searchResult.getSourceAsObjectList(CareProvider.class).get(0);
                }
                Log.d("Succeed", "Empty");
            } catch (IOException e) {
                Log.d("Succeed", "Failed!");
                e.printStackTrace();
            }
            return null;
        }
    }


    /**
     * Update the user in the DB, assuming the user exists
     * @params the modified user
     * (Note: Do not modify the User Id, otherwise it will create a new User)
     */
    public static class UpdateUserTask extends AsyncTask<User,Void,Void>{

        @Override
        protected Void doInBackground(User... users) {
            setClient();
            User user = users[0];
            String className = users[0].getClass().getSimpleName();
            Index index = new Index.Builder(user).index(indexName).type(className.toLowerCase()).id(user.getUserId()).build();
            try{
                //wait until connection is avaliable
                OfflineTools.waitForConnection();
                DocumentResult result = client.execute(index);
            }catch(IOException e){
                Log.d("Name-Jeff"," IOexception when updating the problem");
            }
            return null;
        }
    }

    /**
     * Set the client, duplicate code
     */
     private static void setClient() {
        if (client == null) {
            DroidClientConfig config = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/").build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}