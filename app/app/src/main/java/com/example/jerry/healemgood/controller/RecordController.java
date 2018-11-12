package com.example.jerry.healemgood.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;
import com.google.android.gms.location.places.Place;
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
    private static String indexName = "cmput301f18t12";
    private static String searchQuery;
    /**
     * This will create a record in the database
     * @params record
     */
    public static class CreateRecordTask extends AsyncTask<Record,Void,Void> {

        protected Void doInBackground(Record... records) {
            setClient();
            Record record = records[0];
            Index index = new Index.Builder(record).index(indexName).type("record").build();
            try{
                DocumentResult result = client.execute(index);
                if(result.isSucceeded()){
                    record.setrId(result.getId());
                }
            }catch(IOException e){
                Log.d("Name-Jeff"," IOexception when executing client");
            }
            return null;
        }
    }

    /**
     * Delete a record from DB given the the record as input
     */
    public static class DeleteRecordTask extends AsyncTask<Record,Void,Void> {

        protected Void doInBackground(Record... records) {
            String rid = records[0].getrId();
            Delete delete = new Delete.Builder(rid).index(indexName).type("record").build();
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
     * Seach for a list of records by the title name, description. At the end, it will reset the searchQuery
     * @params It will use the searchQuery user created before
     */
    public static class SearchRecordTask extends AsyncTask<Void,Void,ArrayList<Record>> {
        protected ArrayList<Record> doInBackground(Void... empty) {
            setClient();
            ArrayList<Record> records = new ArrayList<Record>();
            Search search = new Search.Builder(searchQuery).addIndex(indexName).addType("record").build();
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
            searchQuery="";
            return records;
        }
    }

    /**
     * Initialize/Create the searchQuery, call finalizeSearchQuery() after adding the necessary input
     */
    public static void initSearchQuery(){
        String query = "{\n" +
                "    \"query\" : {\n" +
                "    \"bool\" : {\n" +
                "        \"must\" [\n"
                ;
        searchQuery=query;
    }

    /**
     * Modify the search query so it will search for records by keyword in descriptions and title
     * @param keyword
     */
    public static void searchByKeyword(String keyword){
        if(keyword!="") {
            searchQuery += "   {\"multi_match\" : {\n" +
                    "   \"query\": \""+keyword+"\", \n"+
                    "   \"fields\": [\"title\",\"description\"] \n"+
                    "   }\n"+
                    " }\n";
        }
    }

    /**
     * Modify the search query so it will search for records by bodyLocation
     * @param location
     */
    public static void searchByBodyLocation(int location){
        if (location>=0){
            searchQuery += "   {\"term\" : {\n" +
                    "   \"bodyLocation\": \""+String.valueOf(location)+"\", \n"+
                    "       }\n"+
                    "   }\n";

        }
    }

    /**
     * Modify the search query so it it will search for records that are inside the distance of place
     * @param place     Enter a location
     * @param distance  Enter the radius from the location
     */
    public static void searchByGeoLocation(Place place,int distance){
        double Lat = place.getLatLng().latitude;
        double Lon = place.getLatLng().longitude;
        searchQuery += "   {\"geo_location\" : {\n" +
                "               \"distance\": \""+String.valueOf(distance)+"km\", \n"+
                "                   \"geoLocation\": { \n"+
                "                   \"latitude\": " +String.valueOf(Lat) +", \n"+
                "                   \"longitude\": " +String.valueOf(Lon) +"\n"+
                "                   }\n"+
                "       }\n"+
                "   }\n";
    }

    /**
     * Call this after all the search parameter is entered
     */
    public static void finalizeSearchQuery(){
        searchQuery += "]\n"+
                "           }\n"+
                "           }\n"+
                "}";

    }

    /**
     * This is most likely for testing/debug purposes, enter a record ID and it will get a record, if the record with such ID exist
     */
    public static class GetRecordsByProblemIdTask extends AsyncTask<String,Void,ArrayList<Record>> {
        protected ArrayList<Record> doInBackground(String... piDs) {
            setClient();
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"term\" :{ \"pId\" : \""+piDs[0]+"\"}\n"+
                    "    }\n" +
                    "}";
            ArrayList<Record> records = new ArrayList<Record>();
            Search search = new Search.Builder(query).addIndex(indexName).addType("record").build();
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
    /**
     * provide client
     */
}
