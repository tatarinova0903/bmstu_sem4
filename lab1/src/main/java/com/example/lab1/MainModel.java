package com.example.lab1;

import java.util.ArrayList;

public class MainModel {
    private SetNumber current_set;
    private ArrayList<Point> set1 = new ArrayList<Point>();
    private ArrayList<Point> set2 = new ArrayList<Point>();
    private Double minSquare = Double.MAX_VALUE;

    public void setCurrent_set(SetNumber current_set) {
        this.current_set = current_set;
    }

    public ArrayList<Point> getSet1() {
        return set1;
    }

    public ArrayList<Point> getSet2() {
        return set2;
    }

    void addToSet(Point point) {
        if (current_set == null) { return; }
        switch (current_set) {
            case FIRST:
                set1.add(point);
                break;
            case SECOND:
                set2.add(point);
                break;
        }
    }

    Circle calculateBtnDidTap() {
        Circle res = circleCenter(new Point(21, 60), new Point(14, 14), new Point(51, 42));
        if (set1.size() > 2 && !set2.isEmpty()) {
            return findCircle();
        }
        return new Circle();
    }

    private Circle findCircle() {
        Circle res = new Circle();
        for (int i = 0 ; i < set1.size() - 2; i++) {
            for (int j = i + 1; j < set1.size() - 1; j++) {
                for (int k = j + 1; k < set1.size(); k++) {
                    Circle circle = circleCenter(set1.get(i), set1.get(j), set1.get(k));
                    if (circle.square() < minSquare && circle.contains(set2, 80)) {
                        minSquare = circle.square();
                        res = circle;
                    }
                }
            }
        }
        return res;
    }

    private Circle circleCenter(Point pointA, Point pointB, Point pointC) {
        Line lineAB = new Line(pointA, pointB);
        Line lineBC = new Line(pointB, pointC);

        Line centerPerpendAB = lineAB.centerPerpendicular(new Segment(pointA, pointB));
        Line centerPerpendBC = lineBC.centerPerpendicular(new Segment(pointB, pointC));

        Point center = new Equation(centerPerpendAB, centerPerpendBC).calculate();
        return new Circle(center, pointA.distance(center));
    }
}
