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
    /**]
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
     * Delete a record from DB
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
     * Seach for a list of records by the title name, description
     * @params Search query:String
     * @return list of problems that fit the search query: ArrayList<Problem>
     */

    public static class SearchRecordTask extends AsyncTask<String,Void,ArrayList<Record>> {
        protected ArrayList<Record> doInBackground(String... keywords) {
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

    public static void initSearchQuery(){
        String query = "{\n" +
                "    \"query\" : {\n" +
                "    \"bool\" : {\n" +
                "        \"must\" [\n"
                ;
        searchQuery=query;
    }
    public static void searchByKeyword(String keyword){
        if(keyword!="") {
            searchQuery += "   {\"multi_match\" : {\n" +
                    "   \"query\": \""+keyword+"\", \n"+
                    "   \"fields\": [\"title\",\"description\"] \n"+
                    "   }\n"+
                    " }\n";
        }
    }
    public static void searchByBodyLocation(int location){
        if (location>=0){
            searchQuery += "   {\"term\" : {\n" +
                    "   \"bodyLocation\": \""+String.valueOf(location)+"\", \n"+
                    "       }\n"+
                    "   }\n";

        }
    }
    //distance is in km
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

    public static void finalizeSearchQuery(){
        searchQuery += "]\n"+
                "           }\n"+
                "           }\n"+
                "}";

    }



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
