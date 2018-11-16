package com.example.jerry.healemgood.view.UserViews.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.user.CareProvider;

import java.util.ArrayList;
import java.util.Date;

public class ProblemAdapter extends AppCompatActivity {
    ListView mListView;

    // TODO: replce this list with a controller that gets all the doctor's images
    ArrayList<Problem> problems = problemsListConstructor();

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.patient_home);

        mListView = findViewById(R.id.patientProblemListView);

        CustomProblemAdapter customProblemAdapter = new CustomProblemAdapter();
        mListView.setAdapter(customProblemAdapter);
    }

    class CustomProblemAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return problems.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @NonNull
        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view = getLayoutInflater().inflate(R.layout.problems_list_view_custom_layout, null);
            TextView problemName = view.findViewById(R.id.problemNameTextView);
            TextView date = view.findViewById(R.id.dateTextView);
            TextView records = view.findViewById(R.id.recordsTextView);

            problemName.setText(problems.get(i).getTitle());
            date.setText(problems.get(i).getCreatedDate().toString());
            records.setText(problems.get(i).getTitle());

            return view;
        }
    }

    // Temporary test constructor
    private ArrayList<Problem> problemsListConstructor(){
        ArrayList<Problem> problems = new ArrayList<>();
        Problem problem = new Problem("Knee Itchy", new Date(), "Hello");
        problems.add(problem);
        return problems;
    }

}