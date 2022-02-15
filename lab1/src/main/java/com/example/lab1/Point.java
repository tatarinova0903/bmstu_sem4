package com.example.lab1;

import static java.lang.Math.sqrt;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public double getX() { return x; }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    double distance(Point point) {
        return sqrt((this.x - point.x) * (this.x - point.x) + (this.y - point.y) * (this.y - point.y));
    }

    boolean isInfinity() {
        return x == Double.NEGATIVE_INFINITY || x == Double.POSITIVE_INFINITY ||
                y == Double.NEGATIVE_INFINITY || y == Double.POSITIVE_INFINITY;
    }
}
