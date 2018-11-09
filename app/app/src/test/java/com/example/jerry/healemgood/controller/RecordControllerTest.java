package com.example.jerry.healemgood.controller;

import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class RecordControllerTest {
    @Test
    public void createRecordTest(){
        Record r = new Record("123423e@23","Arm is tingling",true);
        new RecordController.CreateRecordTask().execute(r);
    }
    @Test
    public void deleteRecordTest(){
        Record r = new Record("123423e@23","Arm is tingling",true);
        new RecordController.DeleteRecordTask().execute(r);
    }
    @Test
    public void searchRecordsByPidTest(){
        try {
            ArrayList<Record> records = new RecordController.GetRecordsByProblemIdTask().execute("problemID").get();
        }
        catch(Exception e){

        }
    }
}
