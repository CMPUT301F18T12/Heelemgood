package com.example.jerry.healemgood.Controller;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.google.gson.Gson;
import com.robotium.solo.Solo;

import java.util.Date;

public class ProblemControllerTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public ProblemControllerTest() {
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

    public void testCreateProblem(){
        boolean temp2=true;
        String text = "cant get it working";
        System.out.println(text);
        Problem p = new Problem(text,new Date(),"asdasdasdasdasd");
        try {
            new ProblemController.CreateProblemTask().execute(p).get();
        }catch(Exception e){

        }
        Problem p2=null;
        try {
            p2 = new ProblemController.GetProblemByIdTask().execute(p.getpId()).get();
        }catch(Exception e){
            temp2=false;
        }
        assertNotNull(p2);
        String objectString1 = new Gson().toJson(p);
        String objectString2 = new Gson().toJson(p2);
        assertEquals(objectString1,objectString2);
    }
    public void testUpdateProblem(){
        String text = "Just another test";
        System.out.println(text);
        Problem p = new Problem(text,new Date(),"dgdg_sdcv@sds");
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
    }

    public void testDeleteProblem(){
        String text = "Just another test";
        System.out.println(text);
        Problem p = new Problem(text,new Date(),"sdsadsfdfdsf");
        try {
            new ProblemController.CreateProblemTask().execute(p).get();
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
    }


}