package com.example.jerry.healemgood.Controller;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.controller.TestingTools;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.PatientRecord;
import com.example.jerry.healemgood.model.record.Record;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.robotium.solo.Solo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RecordControllerTest  extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public RecordControllerTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    public void testCreateRecord(){
        //reset the database
        new TestingTools.ResetRecordsTask().execute();
        //record create a problem first
        String text = "cant get it working";
        System.out.println(text);
        Problem p = new Problem(text,new Date(),"tyhiswoneridsddf_sd@");
        try {
            new ProblemController.CreateProblemTask().execute(p).get();
        }catch(Exception e){        }
        //Create Patient Record, you can also declare it as PatientRecord
        Record r = new Record(p.getpId(),p.getTitle(),true);
        try{
            new RecordController.CreateRecordTask().execute(r).get();
        }catch (Exception e){}
        //check if record is created already
        Record r2=null;
        try{
            r2 = new RecordController.GetRecordByIdTask().execute(r.getrId()).get();
        }catch(Exception e){        }
        assertNotNull(r2);
        String objectString1 = new Gson().toJson(r);
        String objectString2 = new Gson().toJson(r2);
        assertEquals(objectString1,objectString2);
    }

    public void testUpdateRecord(){
        //reset the database
        new TestingTools.ResetRecordsTask().execute();
        //record create a problem first
        String text = "cant get it working";
        System.out.println(text);
        Problem p = new Problem(text,new Date(),"tyhiswoneridsddf_sd@");
        try {
            new ProblemController.CreateProblemTask().execute(p).get();
        }catch(Exception e){        }
        //Create Patient Record, you can also declare it as PatientRecord
        Record r = new Record(p.getpId(),p.getTitle(),true);
        try{
            new RecordController.CreateRecordTask().execute(r).get();
        }catch (Exception e){}
        //update the record
        r.setDescription("hand is all red");
        Record r2=null;
        new RecordController.UpdateRecordTask().execute(r);
        try{
            r2=new RecordController.GetRecordByIdTask().execute(r.getrId()).get();
        }catch(Exception e){
            assertTrue( false);
        }
        //compare the string
        assertNotNull(r2);
        String objectString1 = new Gson().toJson(r);
        String objectString2 = new Gson().toJson(r2);
        assertEquals(objectString1,objectString2);

    }
    public void testDeleteRecord(){
        //reset the database
        new TestingTools.ResetRecordsTask().execute();
        String text = "cant get it working";
        System.out.println(text);
        Problem p = new Problem(text,new Date(),"tyhiswoneridsddf_sd@");
        try {
            new ProblemController.CreateProblemTask().execute(p).get();
        }catch(Exception e){        }
        //Create Patient Record, you can also declare it as PatientRecord
        Record r = new Record(p.getpId(),p.getTitle(),true);
        try{
            new RecordController.CreateRecordTask().execute(r).get();
        }catch (Exception e){}
        //Delete record
        try{
            new RecordController.DeleteRecordTask().execute(r).get();
        }catch(Exception e){}
        //check if record still exist
        Record r2=null;
        try{
            r2=new RecordController.GetRecordByIdTask().execute(r.getrId()).get();
        }catch(Exception e){        }
        assertNull(r2);
    }

    public void testSearchRecord(){
        Log.d("Name-Jeff","start testSearchRecord");
        //reset the database
        try {
            new TestingTools.ResetRecordsTask().execute().get();
        }catch(Exception e){assertTrue(false);}
        String pid = Long.toHexString(Double.doubleToLongBits(Math.random()));
        String pid2= Long.toHexString(Double.doubleToLongBits(Math.random()));
        String pid3= Long.toHexString(Double.doubleToLongBits(Math.random()));;
        Record r = new PatientRecord(pid,"nervous and sweaty hand");
        ((PatientRecord) r).setBodyLocation(3);
        r.setGeoLocation(53.518047, -113.512086);
        Record r2= new PatientRecord(pid,"I am nervous");
        ((PatientRecord) r2).setBodyLocation(3);
        r2.setGeoLocation(53.514267, -113.511946);
        Record r3= new PatientRecord(pid2,"hand is sweaty");
        ((PatientRecord) r3).setBodyLocation(2);
        r3.setGeoLocation(53.510777, -113.511984);
        Record r4= new PatientRecord(pid2,"back pain");
        r4.setDescription("a little bit nervous as well");
        ((PatientRecord) r4).setBodyLocation(1);
        r4.setGeoLocation(53.507529, -113.512040);
        Record r5= new Record(pid3,"Sofme title",false);
        r5.setGeoLocation(53.504739, -113.512045);
        try{
            new RecordController.CreateRecordTask().execute(r).get();
            new RecordController.CreateRecordTask().execute(r2).get();
            new RecordController.CreateRecordTask().execute(r3).get();
            new RecordController.CreateRecordTask().execute(r4).get();
            new RecordController.CreateRecordTask().execute(r5).get();
        }catch (Exception e){}
        //search By first,2nd pid
        ArrayList<Record> records=new ArrayList<Record>();
        //wait for the creation to complete in our server
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch(Exception e){assertTrue(false);}
        try{
            RecordController.initSearchQuery();
            RecordController.searchByProblemIds(pid,pid2);
            RecordController.finalizeSearchQuery();
            records= new RecordController.SearchRecordTask().execute().get();
        }catch (Exception e){ }
        assertEquals(records.size(),4);
        //search by bodylocation
        try{
            RecordController.initSearchQuery();
            RecordController.searchByBodyLocation(3);
            RecordController.finalizeSearchQuery();
            records=new RecordController.SearchRecordTask().execute().get();
        }catch(Exception e){}
        assertEquals(records.size(),2);
        //search by keyword
        try{
            RecordController.initSearchQuery();
            RecordController.searchByKeyword("nervous");
            RecordController.finalizeSearchQuery();
            records=new RecordController.SearchRecordTask().execute().get();
        }catch(Exception e){}
        assertEquals(records.size(),3);
        try{
            RecordController.initSearchQuery();
            RecordController.searchByKeyword("sweaty");
            RecordController.finalizeSearchQuery();
            records=new RecordController.SearchRecordTask().execute().get();
        }catch(Exception e){}
        assertEquals(records.size(),2);
        //test by geolocation
        try{
            RecordController.initSearchQuery();
            RecordController.searchByGeoLocation(new LatLng(53.518141, -113.512112),1);
            RecordController.finalizeSearchQuery();
            records=new RecordController.SearchRecordTask().execute().get();
        }catch(Exception e){        }

        assertEquals(records.size(),3);
        try{
            RecordController.initSearchQuery();
            RecordController.searchByGeoLocation(new LatLng(53.518141, -113.512112),2);
            RecordController.finalizeSearchQuery();
            records=new RecordController.SearchRecordTask().execute().get();
        }catch(Exception e){        }
        assertEquals(records.size(),5);
    }
}
