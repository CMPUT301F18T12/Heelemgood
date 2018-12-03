package com.example.jerry.healemgood.Intent;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.controller.ProblemController;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;
import com.example.jerry.healemgood.view.careProviderActivities.CareProviderViewProblems;
import com.example.jerry.healemgood.view.commonActivities.AddRecordActivity;
import com.example.jerry.healemgood.view.commonActivities.AllRecordActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientAllProblemActivity;
import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.sleep;

public class CareProviderAddCommentActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;
    public CareProviderAddCommentActivityTest(){
        super("com.example.jerry.healemgood.Intent",
                com.example.jerry.healemgood.MainActivity.class);
    }
    /**
     * Handles errors
     */
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
     * Shuts down if there's an error.
     */
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    /**
     * test Create comment on a patient
     */
    public void testCreateComment(){
        //create the test careprovider and patient first, if this is not the first time the test is run, it will just overwrite the previous data with the same data.
        String c_id="testCasdasdas";
        String c_pass= "tesadfasfjadfas";
        String c_fullName= "asda sdfgh sdsdfsd";
        String c_phone="513456789";
        String c_email="sgsd2342fsd@gmail.com";
        Date c_birth = new Date();
        char c_gender = 'M';
        String p_id="fsdf23423dsfsdf";
        String p_pass= "tesadfasfjadfas";
        String p_fullName= "asda sdfgh sdsdfsd";
        String p_phone="513456789";
        String p_email="sgsd2342fsd@gmail.com";
        Date p_birth = new Date();
        char p_gender = 'M';
        CareProvider c;
        Patient p;
        try {
            c = new CareProvider(c_id, c_pass, c_fullName, c_phone, c_email, c_birth, c_gender);
            p = new Patient(p_id, p_pass, p_fullName, p_phone, p_email, p_birth, p_gender);
            c.addPatientUserId(p.getUserId());  //add patient to care provider
            new UserController.AddUserTask().execute(c).get();
            new UserController.AddUserTask().execute(p).get();
            Problem problem = new Problem("asdasdasdasd",new Date(),p.getUserId(),"Whatever");
            new ProblemController.CreateProblemTask().execute(problem).get();
        }catch(Exception e){
            assertTrue(false);
        }
        solo.sleep(2000); //wait for server to process the data
        EditText loginCredentials = (EditText) solo.getView(R.id.userIdEditText);
        solo.enterText(loginCredentials, c_id);
        solo.clickOnButton("Sign In");
        solo.sleep(2000);
        //click a patinet
        ListView listView = (ListView)solo.getView(R.id.patientListView);
        View child = listView.getChildAt(0);
        solo.clickOnView(child);
        //click a problem
        solo.assertCurrentActivity("Check on Problems", CareProviderViewProblems.class);
        solo.sleep(2000); //wait
        listView= (ListView)solo.getView(R.id.listView);
        child = listView.getChildAt(0);
        solo.clickOnView(child);
        //click on add comment
        solo.assertCurrentActivity("Check on a record", AllRecordActivity.class);
        solo.clickOnButton("Add Comment");
        solo.assertCurrentActivity("Clicked on add comment", AddRecordActivity.class);
        //add details
        EditText title= (EditText) solo.getView(R.id.titleInput);
        EditText description=(EditText) solo.getView(R.id.descriptionInput);
        String titleText="sdsadasdgdfsdvsdf";
        solo.enterText(title,titleText);
        solo.enterText(description,"I am so sick of this assginemtn right now");
        solo.clickOnButton("Save");
        solo.sleep(2000);
        //check if the record is there
        RecordController.searchByKeyword(titleText);
        ArrayList<Record> records=null;
        try {
            records= new RecordController.SearchRecordTask().execute().get();
        }catch (Exception e){
            assertTrue(false);
        }
        assertEquals(records.get(0).getTitle(),titleText);
    }


}
