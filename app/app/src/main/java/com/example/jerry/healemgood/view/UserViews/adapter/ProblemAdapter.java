package com.example.jerry.healemgood.view.UserViews.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.controller.RecordController;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;

import java.util.ArrayList;
import java.util.List;

public class ProblemAdapter extends ArrayAdapter<Problem> {

    private Context mContext;
    private int layout;
    private List<Problem> problems;

    public ProblemAdapter(Context c, int layout, List<Problem> problems){
        super(c,layout,problems);
        this.layout = layout;
        this.mContext = c;
        this.problems = problems;
    }


    public void refreshAdapter(List<Problem> problems){
        this.problems.clear();
        this.problems.addAll(problems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
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
            TextView recordCount = v.findViewById(R.id.descriptionTextView);
            problemName.setText(p.getTitle());
            date.setText(p.getCreatedDate().toString());

            ArrayList<Record> records;

            try{
                RecordController.initSearchQuery();
                RecordController.searchByProblemIds(p.getpId());
                RecordController.finalizeSearchQuery();
                records = new RecordController.SearchRecordTask().execute().get();
            }
            catch (Exception e){
                records = new ArrayList<Record>();

            }
            recordCount.setText(records.size()+"");
        }




        return v;
    }


}
