package com.example.jerry.healemgood.controller;

import android.location.Location;
import android.os.AsyncTask;
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

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class ProblemController {
    private static JestDroidClient client=null;
    private static String indexName = "cmput301f18t12";

    /**
     * This function is for debug/testing purposes, return a problem given a problem id
     */
    public static class GetProblemByIdTask extends AsyncTask<String,Void,Problem>{
        protected Problem doInBackground(String... ids) {
            setClient();
            String id = ids[0];
            Get get = new Get.Builder(indexName, id).type("problem").build();
            try{
                DocumentResult result = client.execute(get);
                Problem p  = result.getSourceAsObject(Problem.class);
                return p;
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
        protected Void doInBackground(Problem... problems) {
            setClient();
            Problem problem = problems[0];
            Index index = new Index.Builder(problem).index(indexName).type("problem").build();
            try{
                DocumentResult result = client.execute(index);
                if(result.isSucceeded()){
                    problem.setpId(result.getId());
                }
            }catch(IOException e){
                Log.d("Name-Jeff"," IOexception when executing client");
            }
            return null;
        }
    }

    /**
     * Delete a problem base on the id(JestID), and removed corresponding records from DB2
     */
    public static class DeleteProblemTask extends AsyncTask<Problem,Void,Void> {

        protected Void doInBackground(Problem... problems) {
            String pid = problems[0].getpId();
            Delete delete = new Delete.Builder(pid).index(indexName).type("problem").build();
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"constant\" :{ \n"+
                    "           \"term\" :{ \n"+
                    "               \"pId\" :\""+pid+"\"\n"+
                    "            }\n"+
                    "         }\n"+
                    "    }\n" +
                    "}";
            DeleteByQuery deleteRecord = new DeleteByQuery.Builder(query).addIndex(indexName).addType("problem").build();
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
     * @params Search query:String
     * @return list of problems that fit the search query: ArrayList<Problem>
     */
    public static class SearchProblemTask extends AsyncTask<String,Void,ArrayList<Problem>> {
        protected ArrayList<Problem> doInBackground(String... titles) {
            setClient();
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"match\" :{ \"title\" : \""+titles[0]+"\"}\n"+
                    "    }\n" +
                    "}";
            ArrayList<Problem> problems = new ArrayList<Problem>();
            Search search = new Search.Builder(query).addIndex(indexName).addType("problem").build();
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
