package com.example.lab7;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    boolean isEqual(Point point) {
        return Math.abs(point.getX() - this.x) < 1e-6 && Math.abs(point.getY() - this.y) < 1e-6;
    }
}
