package com.example.jerry.healemgood.view.UserViews.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.model.problem.Problem;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.view.UserActivities.AccountCreationActivity;
import com.example.jerry.healemgood.view.UserViews.BodyMapSelectionActivity;

import java.util.ArrayList;
import java.util.Date;

public class ProblemAdapter extends AppCompatActivity {
    ListView mListView;
    Button createRecordButton;

    // TODO: replce this list with a controller that gets all the doctor's images
    ArrayList<Problem> problems = problemsListConstructor();

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.patient_home);

        mListView = findViewById(R.id.patientProblemListView);
        createRecordButton = findViewById(R.id.createRecordButton);

        CustomProblemAdapter customProblemAdapter = new CustomProblemAdapter();
        mListView.setAdapter(customProblemAdapter);

        createRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BodyMapSelectionActivity.class);
                startActivity(intent);
            }
        });
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