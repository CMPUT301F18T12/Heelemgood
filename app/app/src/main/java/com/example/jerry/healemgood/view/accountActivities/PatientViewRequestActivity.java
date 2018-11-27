/*
 *  Class Name: PatientViewRequestActivity
 *
 *  Version: Version 1.0
 *
 *  Date: November 16, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.view.accountActivities;

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
import com.example.jerry.healemgood.utils.LengthOutOfBoundException;

import java.util.ArrayList;
import java.util.Date;

/**
 * A controller that handles viewing the care provider requests
 * Likely deprecated
 *
 * @author WeakMill98
 * @version 1.0
 * @since 1.0
 */


public class PatientViewRequestActivity extends AppCompatActivity {

    /**
     * Loads a previous version of the activity if possible
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_view_request);
    }

    /**
     * Creates an instance of the CareProviderAdapter
     */
    public static class CareProviderAdapter extends AppCompatActivity {
        ListView mListView;

        // TODO: replce this list with a controller that gets all the doctor's images
        // This drawable image is only a placeholder
        int[] images = {R.drawable.beter};
        ArrayList<CareProvider> careProviders = careProviderListConstructor();

        /**
         * Loads a previous version of the adapter if possible
         *
         * @param saveInstanceState
         */
        @Override
        protected void onCreate(Bundle saveInstanceState){
            super.onCreate(saveInstanceState);
            setContentView(R.layout.patient_careprovider_requests);

            mListView = findViewById(R.id.listView);

            CustomCareProviderAdaptor customCareProviderAdaptor = new CustomCareProviderAdaptor();
            mListView.setAdapter(customCareProviderAdaptor);
        }

        /**
         * Tests creating the adapter
         *
         * @return
         */
        private ArrayList<CareProvider> careProviderListConstructor(){
            ArrayList<CareProvider> providers = new ArrayList<>();
            CareProvider male_1 = null;
            try {
                male_1 = new CareProvider(
                        "MisterGuy", "password","MisterGuy",
                        "phoneNum","email",new Date(),'M');
            } catch (LengthOutOfBoundException e) {
                e.printStackTrace();
            }

            CareProvider female_2 = null;
            try {
                female_2 = new CareProvider(
                        "MissusLady1","password","MissusLady",
                        "phoneNum","email",new Date(),'F');
            } catch (LengthOutOfBoundException e) {
                e.printStackTrace();
            }

            CareProvider female_3 = null;
            try {
                female_3 = new CareProvider(
                        "MissusLady2","password","MissusLady",
                        "phoneNum","email",new Date(),'F');
            } catch (LengthOutOfBoundException e) {
                e.printStackTrace();
            }

            CareProvider female_4 = null;
            try {
                female_4 = new CareProvider(
                        "MissusLady3","password","MissusLady",
                        "phoneNum","email",new Date(),'F');
            } catch (LengthOutOfBoundException e) {
                e.printStackTrace();
            }


            providers.add(female_2);
            providers.add(female_3);
            providers.add(female_4);
            providers.add(male_1);
            return providers;
        }

        /**
         * Creates a more specific version of the adapter
         *
         */
        class CustomCareProviderAdaptor extends BaseAdapter {

            /**
             * Returns count of patient requests
             *
             * @return
             */
            @Override
            public int getCount() {
                return careProviders.size();
            }

            /**
             * Gets a specific element of the request list
             *
             * @param i
             * @return
             */
            @Override
            public Object getItem(int i) {
                return null;
            }

            /**
             * Gets the id of the selected request
             *
             * @param i
             * @return
             */
            @Override
            public long getItemId(int i) {
                return 0;
            }

            /**
             * Loads the list of patient requests
             *
             * @param i
             * @param convertView
             * @param viewGroup
             * @return
             */
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
