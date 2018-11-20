package com.example.jerry.healemgood.view.UserViews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jerry.healemgood.R;

/**
 * Represents a PatientSearchActivity
 * handles all friends search
 *
 * @author xiacijie
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class PatientSearchActivity extends AppCompatActivity {

    /**
     * Reloads an earlier version of the activity if possible
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_search);
    }
}
