package com.example.jerry.healemgood.controller;

import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ProblemControllerTest {
    /**
     * description : test for constructor
     */
    @Test
    public void searchProblemTest(){
        try {
            ArrayList<Problem> problems = new ProblemController.SearchProblemTask().execute("title").get();
        }catch(Exception e){

        }
    }
    @Test
    public void deleteProblemTest(){
        try {
            new ProblemController.DeleteProblemTask().execute();
        }catch(Exception e){

        }
    }
    @Test
    public void updateProblemTest(){
        try {
            new ProblemController.UpdateProblemTask().execute();
        }catch(Exception e){

        }
    }
}