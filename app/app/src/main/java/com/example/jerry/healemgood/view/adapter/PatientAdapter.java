/*
 *  Class Name: ProblemAdapter
 *
 *  Version: Version 1.0
 *
 *  Date: November 17, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.commonActivities.ViewContactActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * An adapter class that helps with records
 *
 * @author xiacijie
 * @version 1.0
 * @since 1.0
 */
public class PatientAdapter extends ArrayAdapter<Patient> {

    private Context mContext;
    private int layout;
    private ArrayList<Patient> patients;

    /**
     * Creates a RecordAdapter
     *
     * @param c
     * @param layout
     * @param patients
     */
    public PatientAdapter(Context c, int layout, ArrayList<Patient> patients){
        super(c,layout,patients);
        this.layout = layout;
        this.mContext = c;
        this.patients = patients;
    }


    /**
     * Refreshes the adapter
     *
     * @param patients
     */
    public void refreshAdapter(List<Patient> patients){
        this.patients.clear();
        this.patients.addAll(patients);
        notifyDataSetChanged();
    }

    /**
     * Gets and returns the view
     *
     * @param position
     * @param convertView
     * @param viewGroup
     * @return v
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(layout, null);
        }

        final Patient p = getItem(position);

        if (p != null){
            // Get the patient name and patient ID
            TextView patientName = v.findViewById(R.id.patientName);
            TextView patientUid = v.findViewById(R.id.patientUid);
            ImageView imageView = v.findViewById(R.id.imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,ViewContactActivity.class);
                    intent.putExtra(AppConfig.USERID,p.getUserId());
                    mContext.startActivity(intent);
                }
            });

            // Set the xml elements
            patientName.setText(p.getFullName());
            patientUid.setText(p.getUserId());
        }
        return v;
    }


}
