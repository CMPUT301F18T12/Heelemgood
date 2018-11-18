package com.example.jerry.healemgood.utils;

import java.io.Serializable;

public class BodyLocation implements Serializable {
    private BodyPart part;
    private float x;
    private float y;

    public BodyLocation(BodyPart part, float x, float y) {
        this.part = part;
        this.x = x;
        this.y = y;
    }

    public BodyPart getPart() {
        return part;
    }

    public void setPart(BodyPart part) {
        this.part = part;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
