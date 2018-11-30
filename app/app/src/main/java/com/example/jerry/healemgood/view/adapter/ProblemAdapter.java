/*
 *  Class Name: ProblemAdapter
 *
 *  Version: Version 1.0
 *
 *  Date: November 16, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;
import com.example.jerry.healemgood.view.commonActivities.ViewContactActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientAllProblemActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * An adapter class that helps with problems
 *
 * @author xiacijie
 * @version 1.0
 * @since 1.0
 */
public class ProblemAdapter extends ArrayAdapter<Problem> {

    private Context mContext;
    private int layout;
    private List<Problem> problems;

    /**
     * Creates the ProblemAdapter
     *
     * @param c
     * @param layout
     * @param problems
     */
    public ProblemAdapter(Context c, int layout, List<Problem> problems){
        super(c,layout,problems);
        this.layout = layout;
        this.mContext = c;
        this.problems = problems;
    }

    /**
     * Refreshes the adapter
     *
     * @param problems
     */
    public void refreshAdapter(List<Problem> problems){
        this.problems.clear();
        this.problems.addAll(problems);
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(layout, null);
        }

        Problem p = getItem(position);

        if (p != null){

            TextView problemName = v.findViewById(R.id.problemNameTextView);
            TextView date = v.findViewById(R.id.dateTextView);
            TextView recordCount = v.findViewById(R.id.countTextView);
            problemName.setText(p.getTitle());

            SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
            date.setText(sm.format(p.getCreatedDate()));

            String pId = p.getpId();
            ArrayList<Record> records;
            RecordController.searchByProblemIds(pId);
            try{
                records = new RecordController.SearchRecordTask().execute().get();
            }
            catch (Exception e){
                Log.d("Error","Fail to load records");
                records = new ArrayList<Record>();
            }

            ImageView deleteImageView = v.findViewById(R.id.deleteImageView);

            if (mContext instanceof PatientAllProblemActivity){
                deleteImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((PatientAllProblemActivity)mContext).deleteProblem(position);
                    }
                });
            }
            else{
                deleteImageView.setVisibility(View.GONE);
            }




            recordCount.setText(records.size()+"");
        }




        return v;
    }


}
