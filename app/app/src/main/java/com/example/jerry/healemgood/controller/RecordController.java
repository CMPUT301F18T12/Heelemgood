package com.example.jerry.healemgood.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;
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
import io.searchbox.core.Update;

public class RecordController {
    private static JestDroidClient client=null;

    /**]
     * This will create a record in the database
     * @params record
     */
    public static class CreateRecordTask extends AsyncTask<Record,Void,Void> {

        protected Void doInBackground(Record... records) {
            setClient();
            Record record = records[0];
            Index index = new Index.Builder(record).index("Name-Jeff").type("record").build();
            try{
                DocumentResult result = client.execute(index);
                if(result.isSucceeded()){
                    record.setrId(result.getId());
                }
                /*Append the recordIds to the problem in DB
                String query = "{\n" +
                        "    \"script\": {\n" +
                        "        \"inline\" :{ \"ctx._source.recordsIDs\" : \""+result.getId()+"\"}\n"+
                        "    }\n" +
                        "}";
                Update update = new Update.Builder(query).index("Name-Jeff").type("problem").id(record.getpId()).build();*/
            }catch(IOException e){
                Log.d("Name-Jeff"," IOexception when executing client");
            }
            return null;
        }
    }

    /**
     * Delete a record from DB
     */
    public static class DeleteRecordTask extends AsyncTask<Record,Void,Void> {

        protected Void doInBackground(Record... records) {
            String rid = records[0].getrId();
            Delete delete = new Delete.Builder(rid).index("Name-Jeff").type("record").build();
            try{
                DocumentResult result = client.execute(delete);
                if(result.isSucceeded()){
                    Log.d("Name-Jeff","Record removed");
                }
            }catch(IOException e){
                Log.d("Joey Error"," IOexception when executing client");
            }
            return null;
        }
    }

    /***
     * Seach for a list of records by the title name, description
     * @params Search query:String
     * @return list of problems that fit the search query: ArrayList<Problem>
     */

    public static class SearchRecordTask extends AsyncTask<String,Void,ArrayList<Record>> {
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

    public static class GetRecordsByProblemIdTask extends AsyncTask<String,Void,ArrayList<Record>> {
        protected ArrayList<Record> doInBackground(String... piDs) {
            setClient();
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"match_phrase\" :{ \"pId\" : \""+piDs[0]+"\"}\n"+
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
