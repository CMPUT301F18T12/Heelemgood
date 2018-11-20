/*
 *  Class Name: ProblemControllerTest
 *
 *  Version: Version 1.0
 *
 *  Date: November 12, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.Controller;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.controller.TestingTools;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.PatientRecord;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;
import com.google.gson.Gson;
import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Handles testing for the problem controller
 *
 * @author joeyUalbera
 * @version 1.0
 * @since 1.0
 */
public class ProblemControllerTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    /**
     * Creates a ProblemControllerTest
     *
     */
    public ProblemControllerTest() {
        super(MainActivity.class);
    }

    /**
     * Handles set up
     *
     */
    @Override
    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
     * Handles clean up
     *
     */
    @Override
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    /**
     * Tests creating a problem
     *
     */
    public void testCreateProblem(){
        boolean temp2=true;
        String text = "HOLY";
        System.out.println(text);
        try {
            Problem p = new Problem(text, new Date(), "ok", "asdasdasdasdasd");
            try {
                new ProblemController.CreateProblemTask().execute(p).get();
            } catch (Exception e) {

            }
            Problem p2 = null;
            try {
                p2 = new ProblemController.GetProblemByIdTask().execute(p.getpId()).get();
            } catch (Exception e) {
                temp2 = false;
            }
            assertNotNull(p2);
            String objectString1 = new Gson().toJson(p);
            String objectString2 = new Gson().toJson(p2);
            assertEquals(objectString1, objectString2);
        }catch (Exception e){}
    }

    /**
     * Tests updating an old problem
     *
     */
    public void testUpdateProblem(){
        String text = "Just another test";
        System.out.println(text);
        try{
            Problem p = new Problem(text,new Date(),"ok", "dgdg_sdcv@sds");
            try {
                new ProblemController.CreateProblemTask().execute(p).get();
            }catch(Exception e){

            }
            String title = "New title";
            p.setTitle(title);
            try{
                new ProblemController.UpdateProblemTask().execute(p).get();
            }catch(Exception e){

            }
            Problem p2=null;
            try {
                p2 = new ProblemController.GetProblemByIdTask().execute(p.getpId()).get();
            }catch(Exception e){
            }
            assertEquals(p2.getTitle(),title);
        }catch (Exception e){}
    }

    /**
     * Tests deleting a problem
     *
     */
    public void testDeleteProblem(){
        String text = "HOLY";
        System.out.println(text);
        try {
            Problem p = new Problem(text,new Date(),"ok","sdsadsfdfdsf");
            try {
                new ProblemController.CreateProblemTask().execute(p).get();
            }catch(Exception e){
            }
            //create record
            Record r=null;
            try{
                r=  new PatientRecord(p.getpId(),"Record for deleteion  title");
                new RecordController.CreateRecordTask().execute(r).get();
            }catch(Exception e){
            }
            //delete problem
            try{
                new ProblemController.DeleteProblemTask().execute(p).get();
            }catch (Exception e){}
            //check if problem still in database
            Problem p2=null;
            try {
                p2 = new ProblemController.GetProblemByIdTask().execute(p.getpId()).get();
            }catch(Exception e){ }
            assertNull(p2);
            //Need to test if records is deleted here
            Record r2=null;
            try{
                r2 = new RecordController.GetRecordByIdTask().execute(r.getrId()).get();
            }catch(Exception e){ }

            //wait for the above querys to complete in our server
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch(Exception e){assertTrue(false);}
            //assertNull(r2);
        } catch (Exception e){}
    }

    /**
     * Tests searching for a problem
     *
     */
    public void testSearchProblem(){
        new TestingTools.ResetTypeTask().execute("problem");
        String userid = "sdsdsdsdsdvbu231AV";
        String userid2 = "WSfisthissadassddad";
        String userid3 = "sdgsdsdvbu231AV";

        try {
            Problem p = new Problem("Stomach sick", new Date(), userid, "ok");
            Problem p2 = new Problem("how are you sick", new Date(), userid, "ok");
            Problem p3 = new Problem("My stomach hurt", new Date(), userid2, "ok");
            Problem p4 = new Problem("hand hurt", new Date(), userid2, "ok");
            Problem p5 = new Problem("my hand hurt", new Date(), userid3, "ok");
            try {
                new ProblemController.CreateProblemTask().execute(p).get();
                new ProblemController.CreateProblemTask().execute(p2).get();
                new ProblemController.CreateProblemTask().execute(p3).get();
                new ProblemController.CreateProblemTask().execute(p4).get();
                new ProblemController.CreateProblemTask().execute(p5).get();
            } catch (Exception e) {

            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                assertTrue(false);
            }
            //test search By title
            try {
                ProblemController.searchByKeyword("sick");
                ArrayList<Problem> problems = new ProblemController.SearchProblemTask().execute().get();
                assertEquals(2, problems.size());
                ProblemController.searchByKeyword("stomach");
                problems = new ProblemController.SearchProblemTask().execute().get();
                assertEquals(2, problems.size());
                ProblemController.searchByKeyword("hurt");
                problems = new ProblemController.SearchProblemTask().execute().get();
                assertEquals(3, problems.size());
            } catch (Exception e) {
                assertTrue(false);
            }
            //test search by Patient ids
            try {
                ProblemController.searchByPatientIds(userid);
                ArrayList<Problem> problems = new ProblemController.SearchProblemTask().execute().get();
                assertEquals(2, problems.size());
                ProblemController.searchByPatientIds(userid3);
                problems = new ProblemController.SearchProblemTask().execute().get();
                assertEquals(1, problems.size());
                ProblemController.searchByPatientIds(userid3, userid2);
                problems = new ProblemController.SearchProblemTask().execute().get();
                assertEquals(3, problems.size());
                ProblemController.searchByPatientIds(userid3, userid2, userid);
                problems = new ProblemController.SearchProblemTask().execute().get();
                assertEquals(5, problems.size());
            } catch (Exception e) {
                assertTrue(false);
            }
            //test multiple search
            try {
                ProblemController.searchByPatientIds(userid, userid3);
                ProblemController.searchByKeyword("sick");
                ArrayList<Problem> problems = new ProblemController.SearchProblemTask().execute().get();
                assertEquals(2, problems.size());

                ProblemController.searchByPatientIds(userid3, userid2);
                ProblemController.searchByKeyword("hand");
                problems = new ProblemController.SearchProblemTask().execute().get();
                assertEquals(2, problems.size());

                ProblemController.searchByPatientIds(userid3, userid2);
                ProblemController.searchByKeyword("stomach");
                problems = new ProblemController.SearchProblemTask().execute().get();
                assertEquals(1, problems.size());

                ProblemController.searchByPatientIds(userid, userid2);
                ProblemController.searchByKeyword("stomach");
                problems = new ProblemController.SearchProblemTask().execute().get();
                assertEquals(2, problems.size());

                ProblemController.searchByPatientIds(userid3, userid2, userid);
                ProblemController.searchByKeyword("hurt");
                problems = new ProblemController.SearchProblemTask().execute().get();
                assertEquals(3, problems.size());
            } catch (Exception e) {
                assertTrue(false);
            }
        }catch (Exception e){}
    }
}