/*
 *  Class Name: Photo
 *
 *  Version: Version 1.0
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.model.photo;

/**
 * Represents a photo
 *
 * @author xiacijie
 * @version 1.0
 * @since 1.0
 */

public class Photo {

    private String path;
    private int width;
    private int height;

    /**
     * This creates an instance of a Photo
     *
     * @param path
     * @param width
     * @param height
     */

    public Photo(String path, int width, int height){

        this.path = path;
        this.width = width;
        this.height = height;
    }

    /**
     * This gets the path of the photo (the photo's location)
     *
     * @return path
     */
    public String getPath(){

        return path;
    }

    /**
     * This sets the path of the photo
     *
     * @param path
     */
    public void setPath(String path){

        this.path = path;
    }

    /**
     * This finds the width of the photo
     *
     * @return width
     */
    public int getWidth(){

        return width;
    }

    /**
     * This sets the width of the photo
     *
     * @param width
     */
    public void setWidth(int width){

        this.width = width;
    }

    /**
     * This returns the height of the photo
     *
     * @return height
     */
    public int getHeight(){

        return height;
    }

    /**
     * This sets the height of the photo
     *
     * @param height
     */
    public void setHeight(int height){

        this.height = height;
    }

}
