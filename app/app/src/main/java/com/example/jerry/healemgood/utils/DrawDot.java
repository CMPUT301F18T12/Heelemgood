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


    public DrawDot(Context context) {
        super(context);

        // create the Paint and set its color
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    public void setx(Float x){
        this.x=x;
    }
    public double getx(){

        return this.x;
    }

    public void sety(Float y){
        this.y=y;
    }
    public double gety(){

        return this.y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(x, y, 20, paint);
    }

}
