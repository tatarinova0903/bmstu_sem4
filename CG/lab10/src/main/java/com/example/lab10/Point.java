package com.example.lab10;

public class Point {
    private double x;
    private double y;

    public Point() {
        this.x = Double.POSITIVE_INFINITY;
        this.y = Double.POSITIVE_INFINITY;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    boolean isEqual(Point point) {
        return Math.abs(point.getX() - this.x) < 1e-6 && Math.abs(point.getY() - this.y) < 1e-6;
    }

    boolean exists() {
        if (this.x == Double.POSITIVE_INFINITY || this.y == Double.POSITIVE_INFINITY) {
            return false;
        }
        return true;
    }
}
