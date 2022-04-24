package com.example.lab5;

import java.util.ArrayList;

public class Figure {
    private ArrayList<Point> points = new ArrayList<>();

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }

    public void lock() {
        int size = points.size();
        if (size == 0) { return; }
        if (!points.get(0).isEqual(points.get(size - 1))) {
            points.add(new Point(points.get(0).getX(), points.get(0).getY()));
        }
    }

    public boolean isLocked() {
        int size = points.size();
        if (size < 2) { return false; }
        return points.get(0).isEqual(points.get(size - 1));
    }
}
