package com.example.jerry.healemgood.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
// Image Slider - Android Studio Tutorial from https://www.youtube.com/watch?v=Q2FPDI99-as by Coding in Flow on Nov 28 2018
public class SlideShowAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Bitmap> photoBitmapCollections = new ArrayList<Bitmap>();

    public SlideShowAdapter(Context mContext) {
        this.mContext = mContext;
    }



    @Override
    public int getCount() {
        return photoBitmapCollections.size();
    }

    public void addPhoto(Bitmap imageBitmap){
        photoBitmapCollections.add(imageBitmap);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(photoBitmapCollections.get(position));
        container.addView(imageView,0);
        return imageView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
