package com.example.jerry.healemgood.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.model.user.User;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

//TODO: Fix the issue of setClient() being local, assign it a singleton and make it global
public class UserCreationController {
    private static JestDroidClient client=null;

    // Add a User to the database
    public static class addUserTask extends AsyncTask<User,Void,Void> {
        protected Void doInBackground(User... users){
            setClient();
            User user = users[0];
            Index index = new Index.Builder(user).index("Name-Jeff").type("user").build();
            try{
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()){
                    System.out.println("OK, it worked");
                    user.setUserId(result.getId());
                }
            }catch (IOException e){
                System.out.println("lmao, I dun goofed here boyo");
                e.printStackTrace();
            }
            return null;
        }
    }

    // Class used to find the user from the password and username
    public static class searchUserPasswordandUsername extends AsyncTask<String,Void,ArrayList<Record>> {
        protected ArrayList<Record> doInBackground(String... keywords) {
            setClient();
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"multi_match\" :{ \n"+
                    "               \"query\": \""+keywords[0]+"\",\n"+
                    "               \"fields\": [ \"title\",\"description\"]\n"+
                    "    }\n" +
                    "}";
            ArrayList<Record> records = new ArrayList<Record>();
            Search search = new Search.Builder(query).addIndex("Name-Jeff").addType("record").build();
            try{
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    Log.d("Name-Jeff","Record searched");
                    List<Record> resultList = result.getSourceAsObjectList(Record.class);
                    records.addAll(resultList);
                }
            }catch(IOException e){
                Log.d("Joey Error"," IOexception when executing client");
            }
            return records;
        }
    }

    // Set the client, duplicate code
    private static void setClient(){
        if(client==null){
            DroidClientConfig config = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/").build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client=(JestDroidClient)factory.getObject();
        }
    }
}
