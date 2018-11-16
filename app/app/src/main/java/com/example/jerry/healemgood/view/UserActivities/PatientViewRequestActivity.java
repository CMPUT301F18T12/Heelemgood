package com.example.jerry.healemgood.view.UserActivities;

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
import com.example.jerry.healemgood.model.user.CareProvider;

import java.util.ArrayList;
import java.util.Date;

public class PatientViewRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_view_request);
    }

    public static class CareProviderAdapter extends AppCompatActivity {
        ListView mListView;

        // TODO: replce this list with a controller that gets all the doctor's images
        // This drawable image is only a placeholder
        int[] images = {R.drawable.beter};
        ArrayList<CareProvider> careProviders = careProviderListConstructor();

        @Override
        protected void onCreate(Bundle saveInstanceState){
            super.onCreate(saveInstanceState);
            setContentView(R.layout.patient_careprovider_requests);

            mListView = findViewById(R.id.listView);

            CustomCareProviderAdaptor customCareProviderAdaptor = new CustomCareProviderAdaptor();
            mListView.setAdapter(customCareProviderAdaptor);
        }

        // Temporary test constructor
        private ArrayList<CareProvider> careProviderListConstructor(){
            ArrayList<CareProvider> providers = new ArrayList<>();
            CareProvider male_1 =new CareProvider(
                    "MisterGuy", "password","MisterGuy",
                    "phoneNum","email",new Date(),'M');

            CareProvider female_2 =new CareProvider(
                    "MissusLady1","password","MissusLady",
                    "phoneNum","email",new Date(),'F');

            CareProvider female_3 =new CareProvider(
                    "MissusLady2","password","MissusLady",
                    "phoneNum","email",new Date(),'F');

            CareProvider female_4 =new CareProvider(
                    "MissusLady3","password","MissusLady",
                    "phoneNum","email",new Date(),'F');


            providers.add(female_2);
            providers.add(female_3);
            providers.add(female_4);
            providers.add(male_1);
            return providers;
        }

        class CustomCareProviderAdaptor extends BaseAdapter {

            @Override
            public int getCount() {
                return careProviders.size();
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
                View view = getLayoutInflater().inflate(R.layout.requests_list_view_custom_layout, null);
                ImageView mImageView = view.findViewById(R.id.imageView);
                TextView mTextView = view.findViewById(R.id.textView);

                mImageView.setImageResource(images[0]);
                mTextView.setText(careProviders.get(i).getFullName());

                return view;
            }
        }
    }
}