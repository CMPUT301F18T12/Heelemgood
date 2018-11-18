/*
 *  Class Name: DrawDot
 *
 *  Version: Version 1.0
 *
 *  Date: November 16, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
/**
 * Represents a color on the body map
 *
 * @author rwang4
 * @version 1.0
 * @since 1.0
 */
public class DrawDot extends View {
    Paint paint;
    Float x;
    Float y;


    /**
     * Creates an instance of the DrawDot class
     *
     * @param context
     */
    public DrawDot(Context context) {
        super(context);

        // create the Paint and set its color
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    /**
     * Sets the x coordinate where the dot is to be drawn
     *
     * @param x
     */
    public void setx(Float x){
        this.x=x;
    }

    /**
     * Gets the x coordinate of the dot's location
     *
     * @return x
     */
    public double getx(){

        return this.x;
    }

    /**
     * Sets the y coordinate of where the dot is to be drawn
     *
     * @param y
     */
    public void sety(Float y){
        this.y=y;
    }

    /**
     * Gets the y coordinate of the dot's location
     *
     * @return y
     */
    public double gety(){

        return this.y;
    }

    /**
     * Draws the dot at the correct coordinates
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(x, y, 20, paint);
    }

}
