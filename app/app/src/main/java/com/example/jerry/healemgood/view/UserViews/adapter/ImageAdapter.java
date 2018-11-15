package com.example.jerry.healemgood.view.UserViews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.jerry.healemgood.R;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Bitmap> photoBitmapCollections = new ArrayList<Bitmap>();

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public void addPhoto(Bitmap imageBitmap){

        photoBitmapCollections.add(imageBitmap);
    }

    public int getCount() {
        return photoBitmapCollections.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
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
