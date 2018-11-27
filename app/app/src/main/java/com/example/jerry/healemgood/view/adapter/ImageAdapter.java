/*
 *  Class Name: ImageAdapter
 *
 *  Version: Version 1.0
 *
 *  Date: November 15, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * An adapter class that helps with images
 *
 * @author xiacijie
 * @version 1.0
 * @since 1.0
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Bitmap> photoBitmapCollections = new ArrayList<Bitmap>();

    /**
     * Gives context
     *
     * @param c
     */
    public ImageAdapter(Context c) {
        mContext = c;
    }

    /**
     * Adds a photo
     *
     * @param imageBitmap
     */
    public void addPhoto(Bitmap imageBitmap){

        photoBitmapCollections.add(imageBitmap);
    }


    public void removePhotoByIndex(int i){
        photoBitmapCollections.remove(i);
    }

    /**
     * Gets size of image collection
     *
     * @return photoBitmapCollections.size()
     */
    public int getCount() {
        return photoBitmapCollections.size();
    }

    /**
     * Gets and returns an item
     *
     * @param position
     * @return null
     */
    public Object getItem(int position) {
        return null;
    }

    /**
     * Gets and returns an item id
     *
     * @param position
     * @return 0
     */
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Creates a new ImageView for each item referenced by the adapter
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
        } else {
            imageView = (ImageView) convertView;
        }
        try{
            imageView.setImageBitmap(photoBitmapCollections.get(position));
        }
        catch (Exception e){
            imageView.setImageBitmap(null);
        }

        return imageView;
    }


}
