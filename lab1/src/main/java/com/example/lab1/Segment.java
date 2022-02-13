package com.example.lab1;

public class Segment {
    private Point point1;
    private Point point2;

    public Segment(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    Point center() {
        return new Point((point1.getX() + point2.getX()) / 2, (point1.getY() + point2.getY()) / 2);
    }
}
