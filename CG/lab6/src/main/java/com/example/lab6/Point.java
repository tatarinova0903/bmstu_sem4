package com.example.lab6;

public class Point {
    double EPS = 1e-6;

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

    public void translate(TranslateCoords coords) {
        this.x += coords.getX();
        this.y += coords.getY();
    }

    boolean isEqual(Point point) {
        return Math.abs(point.getX() - this.x) < EPS && Math.abs(point.getY() - this.y) < EPS;
    }
}
