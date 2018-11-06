package com.example.jerry.healemgood.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jerry.healemgood.model.problem.Problem;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class ProblemController {
    private static JestDroidClient client=null;
    public static void createProblem(Problem p) {

    }
    public static class CreateProblemTask extends AsyncTask<Problem,Void,Void> {

        protected Void doInBackground(Problem... problems) {
            setClient();
            Problem problem = problems[0];
            Index index = new Index.Builder(problem).index("Name-Jeff").type("problem").build();
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
    public static class DeleteProblemTask extends AsyncTask<String,Void,Void> {

        protected Void doInBackground(String... pids) {
            String pid = pids[0];
            Delete delete = new Delete.Builder(pid).index("Name-Jeff").type("problem").build();
            try{
                DocumentResult result = client.execute(delete);
                if(result.isSucceeded()){
                    Log.d("Name-Jeff","Problem removed");
                }
            }catch(IOException e){
                Log.d("Joey Error"," IOexception when executing client");
            }
            return null;
        }

    }

    /***
     * @params Search query:String
     * @return list of problems that fit the search query: ArrayList<Problem>
     */

    public static class SearchProblemTask extends AsyncTask<String,Void,ArrayList<Problem>> {
        protected ArrayList<Problem> doInBackground(String... querys) {
            setClient();
            String query = querys[0];
            ArrayList<Problem> problems = new ArrayList<Problem>();
            Search search = new Search.Builder(query).addIndex("Name-Jeff").addType("problem").build();
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

    public static void setClient(){
        if(client==null){
            DroidClientConfig config = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/").build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client=(JestDroidClient)factory.getObject();
        }
    }
}
