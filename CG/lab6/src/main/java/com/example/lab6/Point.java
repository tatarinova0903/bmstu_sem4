package com.example.lab6;

public class Point {
    double EPS = 1e-6;

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    boolean isEqual(Point point) {
        return Math.abs(point.getX() - this.x) < EPS && Math.abs(point.getY() - this.y) < EPS;
    }
}
