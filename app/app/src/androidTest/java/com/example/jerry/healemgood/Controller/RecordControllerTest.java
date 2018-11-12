package com.example.jerry.healemgood.Controller;

import android.test.ActivityInstrumentationTestCase2;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;
import com.google.gson.Gson;
import com.robotium.solo.Solo;

import java.util.Date;

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

    public void testDeleteRecord(){
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

}
