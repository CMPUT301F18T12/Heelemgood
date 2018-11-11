package com.example.jerry.healemgood;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.model.problem.Problem;
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

    public void testTweet() {
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
    }
    public void testEqual(){
        assertEquals("Not equal",5,5);
    }
    public void testCreateProblem(){
        boolean temp2=true;
        String text = "cant get it working";
        System.out.println(text);
        Problem p = new Problem(text,new Date());
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
        assertTrue(temp2);
    }

}