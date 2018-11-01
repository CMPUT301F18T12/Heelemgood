package com.example.jerry.healemgood.model.photo;

public class Photo {

    private String path;
    private int width;
    private int height;

    public Photo(String path, int width, int height){

        this.path = path;
        this.width = width;
        this.height = height;
    }

    /* Get the path of the photo*/
    public String getPath(){

        return path;
    }

    /* Set the path of the photo*/
    public void setPath(String path){

        this.path = path;
    }

    /* get the width*/
    public int getWidth(){

        return width;
    }

    /* set the width*/
    public void setWidth(int width){

        this.width = width;
    }

    /* get the height*/
    public int getHeight(){

        return height;
    }

    /* set the height*/
    public void setHeight(int height){

        this.height = height;
    }

}
