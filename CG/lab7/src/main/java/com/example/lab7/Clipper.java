package com.example.lab7;

public class Clipper {
    private int xLeft;
    private int xRight;
    private int yUp;
    private int yDown;

    public Clipper() {
        this.xLeft = 0;
        this.xRight = 0;
        this.yUp = 0;
        this.yDown = 0;
    }

    public Clipper(int xLeft, int xRight, int yUp, int yDown) {
        this.xLeft = xLeft;
        this.xRight = xRight;
        this.yUp = yUp;
        this.yDown = yDown;
    }

    public int getxLeft() {
        return xLeft;
    }

    public void setxLeft(int xLeft) {
        this.xLeft = xLeft;
    }

    public int getxRight() {
        return xRight;
    }

    public void setxRight(int xRight) {
        this.xRight = xRight;
    }

    public int getyUp() {
        return yUp;
    }

    public void setyUp(int yUp) {
        this.yUp = yUp;
    }

    public int getyDown() {
        return yDown;
    }

    public void setyDown(int yDown) {
        this.yDown = yDown;
    }
}
