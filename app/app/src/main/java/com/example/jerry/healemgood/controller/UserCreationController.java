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

//TODO: Fix the issue of setClient() being local, assign it a singleton and make it global
// https://stackoverflow.com/questions/12069669/how-can-you-pass-multiple-primitive-parameters-to-asynctask
public class UserCreationController {

    private static JestDroidClient client = null;
    private static String indexName = "cmput301f18t12";

    // Add a User to the database
    // Can either be a patient or a user
    public static class addUserTask extends AsyncTask<User, Void, Void>{
        protected Void doInBackground(User... users) {
            setClient();
            Index index = new Index.Builder(users[0]).index(indexName).type("patient").build();
            try {
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

    // Delete a user from the DB
    public static class deleteUserTask extends AsyncTask<User,Void,Void> {
        protected Void doInBackground(User... users) {
            String userId = users[0].getUserId();
            Delete delete = new Delete.Builder(userId).index(indexName).type("patient").build();
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"constant\" :{ \n"+
                    "           \"term\" :{ \n"+
                    "               \"userId\" :\""+ userId +"\"\n"+
                    "            }\n"+
                    "         }\n"+
                    "    }\n" +
                    "}";
            try{
                DocumentResult result = client.execute(delete);
                if(result.isSucceeded()){
                    Log.d("DeleteUserSuccess","User removed");
                }
            }catch(IOException e){
                Log.d("DeleteUserError"," IOexception when executing client");
            }
            return null;
        }
    }

    // Class used to find the user from the password and username
    // Assume the positioning is username, then password
    // Returns the user object if username and passwords match
/*
    public static class searchUserPasswordandUsername extends AsyncTask<String, Void, ArrayList<User>> {
        protected ArrayList<User> doInBackground(String... keywords) {
            setClient();
            //ArrayList<User> userList = new UserCreationController.searchUsername().doInBackground(keywords[0]);
            String passwordQuery = "{\n" +
                    "    \"query\": {\n" +
                    "        \"multi_match\" :{ \n" +
                    "               \"query\": \"" + keywords[0] + "\",\n" +
                    "               \"fields\": [ \"password\"]\n" +
                    "    }\n" +
                    "}";
            Search searchPassword = new Search.Builder(passwordQuery).addIndex(indexName).addType("user").build();
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
*/


    public static class searchUserTask extends AsyncTask<String, Void, User> {
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

                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse(searchResult.getSourceAsStringList().get(0)).getAsJsonObject();

                    if(jsonObject.getClass().toString().equals("CareProvider")){
                        return searchResult.getSourceAsObjectList(CareProvider.class).get(0);
                    }
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