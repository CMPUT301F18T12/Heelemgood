package com.example.jerry.healemgood.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * This is the adapter for displaying slide show
 * Image Slider - Android Studio Tutorial referred from https://www.youtube.com/watch?v=Q2FPDI99-as by Coding in Flow on Nov 28 2018
 * @version 1.0
 * @since 1.0
 * @author Tianqi
 * @see PagerAdapter
 */
public class SlideShowAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Bitmap> photoBitmapCollections = new ArrayList<Bitmap>();  // the data set we are dealing with

    /**
     * setup the context
     * @param mContext context
     */
    public SlideShowAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return photoBitmapCollections.size();
    }

    /**
     * To tell if the view is belong to this adapter
     * @param view view
     * @param object object
     * @return count
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * Used to generate the image view
     * @param container container
     * @param position position
     * @return the view
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(photoBitmapCollections.get(position));
        container.addView(imageView,0);
        return imageView;

    }

    /**
     * Used to remove the item in the view
     * @param container container of view
     * @param position postion
     * @param object image view object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    /**
     * Add a bitmap photo to the data set
     * @param imageBitmap bitmap of image
     */
    public void addPhoto(Bitmap imageBitmap){
        photoBitmapCollections.add(imageBitmap);
    }
}
