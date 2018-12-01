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
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.model.record.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * An adapter class that helps with records
 *
 * @author xiacijie
 * @version 1.0
 * @since 1.0
 */
public class RecordAdapter extends ArrayAdapter<Record> {

    private Context mContext;
    private int layout;
    private ArrayList<Record> records;

    /**
     * Creates a RecordAdapter
     *
     * @param c
     * @param layout
     * @param records
     */
    public RecordAdapter(Context c, int layout, ArrayList<Record> records){
        super(c,layout,records);
        this.layout = layout;
        this.mContext = c;
        this.records = records;
    }


    /**
     * Refreshes the adapter
     *
     * @param records
     */
    public void refreshAdapter(List<Record> records){
        this.records.clear();
        this.records.addAll(records);
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

        Record r = getItem(position);

        if (r != null){

            TextView recordName = v.findViewById(R.id.recordNameTextView);
            TextView date = v.findViewById(R.id.dateTextView);
            TextView description = v.findViewById(R.id.countTextView);
            ImageView imageView = v.findViewById(R.id.imageView);

            if (r.isPatientRecord()) {
                imageView.setImageResource(R.drawable.record_icon);
                v.setBackgroundResource(R.drawable.rounded_corner);
            }
            else{
                imageView.setImageResource(R.drawable.doctor_record_icon);

                v.setBackgroundResource(R.drawable.orange_small_corner);

            }

            recordName.setText(r.getTitle());

            String pattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String dateString = simpleDateFormat.format(r.getCreatedDate());
            date.setText(dateString);
            description.setText(r.getDescription());
        }




        return v;
    }


}
