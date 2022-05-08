package com.example.lab7;

public class Clipper {
    private double xLeft;
    private double xRight;
    private double yUp;
    private double yDown;

    public Clipper() {
        this.xLeft = Double.POSITIVE_INFINITY;
        this.xRight = Double.POSITIVE_INFINITY;
        this.yUp = Double.POSITIVE_INFINITY;
        this.yDown = Double.POSITIVE_INFINITY;
    }

    public Clipper(double xLeft, double xRight, double yUp, double yDown) {
        this.xLeft = xLeft;
        this.xRight = xRight;
        this.yUp = yUp;
        this.yDown = yDown;
    }

    public double getxLeft() {
        return xLeft;
    }

    public void setxLeft(int xLeft) {
        this.xLeft = xLeft;
    }

    public double getxRight() {
        return xRight;
    }

    public void setxRight(int xRight) {
        this.xRight = xRight;
    }

    public double getyUp() {
        return yUp;
    }

    public void setyUp(int yUp) {
        this.yUp = yUp;
    }

    public double getyDown() {
        return yDown;
    }

    public void setyDown(int yDown) {
        this.yDown = yDown;
    }

    public boolean exists() {
        if (xLeft == Double.POSITIVE_INFINITY) {
            return false;
        }
        return true;
    }
}
