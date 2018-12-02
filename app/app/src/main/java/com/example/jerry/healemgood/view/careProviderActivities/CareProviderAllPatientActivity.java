package com.example.jerry.healemgood.view.careProviderActivities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.UserViews.UserGenerateQRCode;
import com.example.jerry.healemgood.view.adapter.PatientAdapter;
import com.example.jerry.healemgood.view.commonActivities.SearchActivity;
import com.example.jerry.healemgood.view.commonActivities.UserActivity;

import java.util.ArrayList;

/**
 * Represents a CareProviderAllPatientActivity
 * handles all patient activities in care provider account
 *
 * @author WeakMill98
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class CareProviderAllPatientActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Patient> patients = new ArrayList<Patient>();
    CareProvider careProvider = null;
    private PatientAdapter patientAdapter =  null;

    /**
     * Reloads an earlier version of the activity if possible
     * @param savedInstanceState Bundle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_all_patients);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Patient");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.drawerUserId);
        TextView nav_email = (TextView)hView.findViewById(R.id.drawerEmail);
        nav_user.setText(SharedPreferenceUtil.get(this,AppConfig.USERID));
        nav_email.setText(SharedPreferenceUtil.get(this,AppConfig.EMAIL));

        loadCareProvider();
        loadPatients();

        ListView listView = findViewById(R.id.patientListView);
        patientAdapter = new PatientAdapter(getApplicationContext(),R.layout.patients_list_view_custom_layout,patients);
        listView.setAdapter(patientAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showProblems(position);
            }
        });

        Button addPatientButton = findViewById(R.id.addPatientButton);
        addPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CareProviderAddPatientActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * display problem list
     * @param  position int
     */

    private void showProblems(int position){
        String userId = patients.get(position).getUserId();
        Intent intent = new Intent(getApplicationContext(),CareProviderViewProblems.class);
        intent.putExtra(AppConfig.USERID,userId);
        startActivity(intent);
    }

    /**
     * load a care provider
     */

    private void loadCareProvider(){

        String userId = SharedPreferenceUtil.get(getApplicationContext(),AppConfig.USERID);
        try{
            careProvider = (CareProvider)new UserController.SearchCareProviderTask().execute(userId).get();
        }
        catch (Exception e){
            Log.d("Error","Fail to get the care provider");
        }
    }

    /**
     * load patient list of the care provider
     */

    private void loadPatients(){
        ArrayList<String> patientIdList = careProvider.getPatientsUserIds();
        try{
            patients = new UserController.GetPatientsByIdsTask().execute(patientIdList.toArray(new String[patientIdList.size()])).get();

        }catch (Exception e){
            Log.d("Error","Fail to load patient list");
        }
    }

    /**
     * refresh
     */

    @Override
    protected void onResume(){

        super.onResume();
        loadCareProvider();
        loadPatients();
        patientAdapter.refreshAdapter(patients);

    }




    /**
     *  close drawer
     */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * adds items to the action bar
     * @param menu Menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_all_problem, menu);
        return true;
    }
    /**
     * Handle action bar item clicks.
     * @param item MenuItem
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handle navigation view item clicks.
     * @param item MenuItem
     */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (id == R.id.navigation_patients) {
            startActivity(new Intent(getApplicationContext(),CareProviderAddPatientActivity.class));

        } else if (id == R.id.navigation_user) {
            startActivity(new Intent(getApplicationContext(),UserActivity.class));
        }
        else if (id == R.id.navigation_search){
            startActivity(new Intent(getApplicationContext(),SearchActivity.class));
        }
        else if (id == R.id.navigation_qrcode){
            startActivity(new Intent(getApplicationContext(),UserGenerateQRCode.class));
        }


        return true;
    }
}
