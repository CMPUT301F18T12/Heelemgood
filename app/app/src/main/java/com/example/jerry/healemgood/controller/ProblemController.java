/*
 * Controller Name: ProblemController
 *
 *  Version: Version 1.0
 *
 *  Date: November 7, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.controller;


import android.app.Activity;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.jerry.healemgood.model.problem.Problem;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Represents a ProblemController
 *
 * @author joeyUalberta
 * @version 1.0
 * @since 1.0
 */

public class ProblemController {
    private static JestDroidClient client=null;
    private static String indexName = "cmput301f18t12";
    private static final String introQuery= "{\n" +
            "    \"query\" : {\n" +
            "    \"bool\" : {\n" +
            "        \"must\": [\n"
            ;
    private static String searchQuery = introQuery;
    private static boolean building=false;
    /**
     * This function is for debug/testing purposes, return a problem given a problem id
     *
     */

    public static class GetProblemByIdsTask extends AsyncTask<String,Void,ArrayList<Problem>>{
        protected ArrayList<Problem> doInBackground(String... ids) {
            setClient();

            String query ="{ \n"+
            "   \"query\": { \n"+
            "     \"ids\" : { \n"+
            "        \"values\" : [";
            for (int i =0;i<ids.length;i++){
                query += "\""+ids[i]+"\"";
                if(i!=ids.length-1){
                    query+=",";
                }
            }
            query+="]}}}";
            Search search = new Search.Builder(query).addIndex(indexName).addType("problem").build();
            try{
                SearchResult result = client.execute(search);
                if(result.isSucceeded()) {
                    ArrayList<Problem> p = new ArrayList<>();
                    List<Problem> resultList = result.getSourceAsObjectList(Problem.class);
                    p.addAll(resultList);
                    return p;
                }
            }catch(IOException e){
                Log.d("Joey Error"," IOexception when executing client");
            }
            return null;
        }
    }
    /**
     * Create a problem in the database and assigned a JestID/pId to it
     */
    public static class CreateProblemTask extends AsyncTask<Problem,Void,Void> {
        private  AppCompatActivity context=null;

        /**
         * This constructor will take in a context. Note: this is needed if you want to have things
         * @param c Application contest (not base context)
        */
        public CreateProblemTask setContext(AppCompatActivity c){
            this.context =c;
            return this;
        }

        protected Void doInBackground(Problem... problems) {
            setClient();
            Problem problem = problems[0];
            Index index = new Index.Builder(problem).index(indexName).type("problem").build();
            try{
                //wait until connection is avaliable
                if(context!=null) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetworkInfo = connectivityManager
                            .getActiveNetworkInfo();
                    while (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                        Thread.sleep(2000);
                    }
                }
                DocumentResult result = client.execute(index);
                if(result.isSucceeded()){
                    problem.setpId(result.getId());
                }
            }catch(IOException e){
                Log.d("Name-Jeff"," IOexception when executing client");
            }catch(Exception e){
                Log.d("Name-Jeff","Not waiting for internet connection"+e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            super.onPostExecute(v);
            if (context != null){
                try{
                    Thread.sleep(1000);
                }
                catch (Exception e){

                }

                context.finish();
            }

        }
    }

    /**
     * Delete a problem, and removed corresponding records from DB2
     */
    public static class DeleteProblemTask extends AsyncTask<Problem,Void,Void> {

        protected Void doInBackground(Problem... problems) {
            setClient();
            String pid = problems[0].getpId();
            Delete delete = new Delete.Builder(pid).index(indexName).type("problem").build();
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "           \"match\" :{ \n"+
                    "               \"pId\" :\""+pid+"\"\n"+
                    "            }\n"+
                    "         }\n"+
                    "}";
            Log.d("Name-Jeff",query);
            DeleteByQuery deleteRecord = new DeleteByQuery.Builder(query).addIndex(indexName).addType("record").build();
            try{
                DocumentResult result = client.execute(delete);
                JestResult result2 = client.execute(deleteRecord);
                if(result.isSucceeded()){
                    Log.d("Name-Jeff","Problem removed");
                }
                if(result2.isSucceeded()){
                    Log.d("Name-Jeff","Records removed");
                }
            }catch(IOException e){
                Log.d("Joey Error"," IOexception when executing client");
            }
            return null;
        }
    }

    /***
     * Seach for a list of problems by the title name
     *
     * @params Search query:String
     * @return list of problems that fit the search query: ArrayList<Problem>
     */
    public static class SearchProblemTask extends AsyncTask<Void,Void,ArrayList<Problem>> {
        protected ArrayList<Problem> doInBackground(Void... voids) {
            searchQuery += "]\n"+
                    "           }\n"+
                    "           }\n"+
                    "}";
            setClient();
            Log.d("Name-Jeff",searchQuery);
            ArrayList<Problem> problems = new ArrayList<Problem>();
            Search search = new Search.Builder(searchQuery).addIndex(indexName).addType("problem").build();
            //Reset the search Query
            searchQuery = introQuery;
            building = false;
            try{
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    Log.d("Name-Jeff","Problem searched");
                    List<Problem> resultList = result.getSourceAsObjectList(Problem.class);
                    problems.addAll(resultList);
                }
            }catch(IOException e){
                Log.d("Joey Error"," IOexception when executing client");
            }
            return problems;
        }
    }

    /**
     *
     * Modify the search query so it will search for problems by keyword in title and description
     * @param keyword
     */
    public static void searchByKeyword(String keyword){
        if(keyword!="") {
            if(building==true){
                searchQuery+=",";
            }
            searchQuery += "   {\"multi_match\" : {\n" +
                    "   \"query\": \""+keyword+"\", \n"+
                    "   \"fields\": [\"title\",\"description\"] \n"+
                    "   }\n"+
                    " }\n";
        }
        building=true;
    }

    /**
     *
     * Modify the search query so it will search for problems by patient ids
     * @param pIds
     */
    public static void searchByPatientIds(String... pIds){
        if(building==true){
            searchQuery+=",";
        }
        searchQuery +="        {\"terms\" :{ \"userId\" : [";
        for (int i =0;i<pIds.length;i++){
            searchQuery += "\""+pIds[i].toLowerCase()+"\"";
            if(i!=pIds.length-1){
                searchQuery+=",";
            }
        }
        searchQuery+="]}\n"+
                "    }\n";
        building=true;
    }

    /**
     * Update the problem in the DB, assuming the problem passed exist in the current DB
     * @params the modified problem
     * (Note: Do not modify the Problem Id, otherwise it will create a new problem instead)
     */
    public static class UpdateProblemTask extends AsyncTask<Problem,Void,Void>{
        @Override
        protected Void doInBackground(Problem... problems) {
            setClient();
            Problem problem = problems[0];
            Index index = new Index.Builder(problem).index(indexName).type("problem").id(problem.getpId()).build();
            try{
                DocumentResult result = client.execute(index);
            }catch(IOException e){
                Log.d("Name-Jeff"," IOexception when updating the problem");
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
}
