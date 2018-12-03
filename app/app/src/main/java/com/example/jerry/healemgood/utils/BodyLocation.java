/*
 *  Class Name: BodyLocation
 *
 *  Version: Version 1.0
 *
 *  Date: November 18, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */
package com.example.jerry.healemgood.utils;

import java.io.Serializable;

/**
 * Represents a location on the body map
 *
 * @author TianqiCS
 * @version 1.0
 * @since 1.0
 */
public class BodyLocation implements Serializable {
    private BodyPart part;
    private float x;
    private float y;

    /**
     * Sets up a BodyLocation
     *
     * @param part body part
     * @param x x in percentage
     * @param y y in percentage
     */
    public BodyLocation(BodyPart part, float x, float y) {
        this.part = part;
        this.x = x;
        this.y = y;
    }
    public BodyLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets and returns the body part
     *
     * @return part
     */
    public BodyPart getPart() {
        return part;
    }

    /**
     * Sets the body part
     *
     * @param part part
     */
    public void setPart(BodyPart part) {
        this.part = part;
    }

    /**
     * Gets and returns the x coordinate of the body part
     *
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the x coordinate of the body part
     *
     * @param x x in percentage
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets and returns the y coordinate of the body part
     *
     * @return y
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the y coordinate of the body part
     *
     * @param y y in percentage
     */
    public void setY(float y) {
        this.y = y;
    }
}
