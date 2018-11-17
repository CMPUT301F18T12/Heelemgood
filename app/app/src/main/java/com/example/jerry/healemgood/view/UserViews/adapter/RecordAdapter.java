package com.example.jerry.healemgood.view.UserViews.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.record.Record;

import java.util.ArrayList;
import java.util.List;

public class RecordAdapter extends ArrayAdapter<Record> {

    private Context mContext;
    private int layout;

    public RecordAdapter(Context c, int layout, List<Record> records){
        super(c,layout,records);
        this.layout = layout;
        this.mContext = c;
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

        Record p = getItem(position);

        if (p != null){

            TextView problemName = v.findViewById(R.id.problemNameTextView);
            TextView date = v.findViewById(R.id.dateTextView);
            TextView records = v.findViewById(R.id.recordsTextView);
            problemName.setText(p.getTitle());
            date.setText(p.getCreatedDate().toString());
            records.setText(p.getTitle());
        }




        return v;
    }


}
