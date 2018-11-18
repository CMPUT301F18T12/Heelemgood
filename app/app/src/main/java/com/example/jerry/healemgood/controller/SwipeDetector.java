/*
 * Controller Name: ProblemController
 *
 *  Version: Version 1.0
 *
 *  Date: November 17, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */


package com.example.jerry.healemgood.controller;

import android.view.MotionEvent;
import android.view.View;

/**
 * A controller that handles swiping on the app
 *
 * @author TianqiCS
 * @version 1.0
 * @since 1.0
 */


// https://stackoverflow.com/questions/17520750/list-view-item-swipe-left-and-swipe-right
public class SwipeDetector implements View.OnTouchListener {
    public static enum Action {
        LR, // Left to Right
        RL, // Right to Left
        TB, // Top to bottom
        BT, // Bottom to Top
        None // when no action was detected
    }

    private static final String logTag = "SwipeDetector";
    private static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;
    private Action mSwipeDetected = Action.None;

    public boolean swipeDetected() {
        return mSwipeDetected != Action.None;
    }

    public Action getAction() {
        return mSwipeDetected;
    }

    /**
     * Handles swiping
     *
     * @param v
     * @param event
     * @return
     */
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                mSwipeDetected = Action.None;
                return false; // allow other events like Click to be processed
            }
            case MotionEvent.ACTION_MOVE: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // horizontal swipe detection
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        //Logger.show(Log.INFO,logTag, "Swipe Left to Right");
                        mSwipeDetected = Action.LR;
                        return true;
                    }
                    if (deltaX > 0) {
                        //Logger.show(Log.INFO,logTag, "Swipe Right to Left");
                        mSwipeDetected = Action.RL;
                        return true;
                    }
                } else

                    // vertical swipe detection
                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        // top or down
                        if (deltaY < 0) {
                            //Logger.show(Log.INFO,logTag, "Swipe Top to Bottom");
                            mSwipeDetected = Action.TB;
                            return false;
                        }
                        if (deltaY > 0) {
                            //Logger.show(Log.INFO,logTag, "Swipe Bottom to Top");
                            mSwipeDetected = Action.BT;
                            return false;
                        }
                    }
                return true;
            }
        }
        return false;
    }
}
