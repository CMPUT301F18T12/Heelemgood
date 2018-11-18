/*
 * Controller Name: ProblemController
 *
 *  Version: Version 1.0
 *
 *  Date: November 15, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */


package com.example.jerry.healemgood.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jerry.healemgood.model.record.Record;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;

import io.searchbox.client.JestResult;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * A controller that handles removing items from files
 *
 * @author joeyUalberta
 * @version 1.0
 * @since 1.0
 */


public class TestingTools {
    private static JestDroidClient client=null;
    private static String indexName = "cmput301f18t12";

    /**
     * Remove all records under the "record" type
     */
    public static class ResetRecordsTask extends AsyncTask<Void,Void,Void> {

        protected Void doInBackground(Void... records) {
            setClient();
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "           \"match_all\" :{ }\n"+
                    "         }\n"+
                    "}";
            Log.d("Name-Jeff",query);
            DeleteByQuery deleteRecord = new DeleteByQuery.Builder(query).addIndex(indexName).addType("record").build();
            try{
                JestResult result = client.execute(deleteRecord);
                if(result.isSucceeded()){
                    Log.d("Name-Jeff","all records are deleted");
                }
            }catch(IOException e){
                Log.d("Name-Jeff"," IOexception when executing client");
            }
            return null;
        }
    }

    /**
     * Remove all records under the type
     */
    public static class ResetTypeTask extends AsyncTask<String,Void,Void> {

        protected Void doInBackground(String... types) {
            setClient();
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "           \"match_all\" :{ }\n"+
                    "         }\n"+
                    "}";
            Log.d("Name-Jeff",query);
            DeleteByQuery deleteRecord = new DeleteByQuery.Builder(query).addIndex(indexName).addType(types[0]).build();
            try{
                JestResult result = client.execute(deleteRecord);
                if(result.isSucceeded()){
                    Log.d("Name-Jeff","the documents are deleted");
                }
            }catch(IOException e){
                Log.d("Name-Jeff"," IOexception when executing client");
            }
            return null;
        }
    }

    /**
     * Create client
     */
    public static void setClient(){
        if(client==null){
            DroidClientConfig config = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/").build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client=(JestDroidClient)factory.getObject();
        }
    }
    /*
    Query for creating geolocation Index
    PUT http://cmput301.softwareprocess.es:8080/cmput301f18t12/record/_mapping
    {
	"record":{
      "properties": {
        "geoLocation": {
          "type": "geo_point"
        }
      }
	}
    }
     */
}
