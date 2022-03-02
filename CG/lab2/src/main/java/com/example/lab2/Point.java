package com.example.lab2;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    void scale(double dx, double dy) {
        x *= dx;
        y *= dy;
    }

    void rotate(double cx, double cy, double degree) {
        double angle = Math.toRadians(degree);
        double newX = (x - cx) * Math.cos(angle) - (y - cy) * Math.sin(angle);
        double newY = (x - cx) * Math.sin(angle) + (y - cy) * Math.cos(angle);
        x = newX + cx;
        y = newY + cy;
    }
}
