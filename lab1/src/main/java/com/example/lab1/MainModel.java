package com.example.lab1;

import java.util.ArrayList;

public class MainModel {
    private SetNumber current_set;
    private ArrayList<Point> set1 = new ArrayList<>();
    private ArrayList<Point> set2 = new ArrayList<>();
    private ArrayList<Point> res = new ArrayList<>();
    private Circle circle = new Circle();
    private Oval oval = new Oval();
    public void setCurrent_set(SetNumber current_set) {
        this.current_set = current_set;
    }

    public ArrayList<Point> getSet1() {
        return set1;
    }

    public ArrayList<Point> getSet2() {
        return set2;
    }

    public Oval getOval() {
        return oval;
    }

    public void setOval(Oval oval) {
        this.oval = oval;
    }

    public Circle getCircle() {
        return this.circle;
//        if (res.size() != 3) { return new Circle(); }
//        return circleBy3Points(res.get(0), res.get(1), res.get(2));
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public SetNumber getCurrent_set() {
        return current_set;
    }

    public ArrayList<Point> getRes() {
        return res;
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
//        Circle res = circleBy3Points(new Point(21, 60), new Point(14, 14), new Point(51, 42));
        if (set1.size() > 2 && !set2.isEmpty()) {
            return findCircle();
        }
        return new Circle();
    }

    private Circle findCircle() {
        double minSquare = Double.MAX_VALUE;
        Circle res = new Circle();
        for (int i = 0 ; i < set1.size() - 2; i++) {
            for (int j = i + 1; j < set1.size() - 1; j++) {
                for (int k = j + 1; k < set1.size(); k++) {
                    Circle circle = circleBy3Points(set1.get(i), set1.get(j), set1.get(k));
                    if (circle.square() < minSquare && circle.contains(set2, 80)) {
                        minSquare = circle.square();
                        res = circle;
                        this.res.clear();
                        this.res.add(set1.get(i));
                        this.res.add(set1.get(j));
                        this.res.add(set1.get(k));
                        this.oval = new Oval(circle.getCenter(), circle.getRadius() * 2, circle.getRadius() * 2);
                    }
                }
            }
        }
        this.circle = res;
        return res;
    }

    private Circle circleBy3Points(Point pointA, Point pointB, Point pointC) {
        Line lineAB = new Line(pointA, pointB);
        Line lineBC = new Line(pointB, pointC);

        Line centerPerpendAB = lineAB.centerPerpendicular(new Segment(pointA, pointB));
        Line centerPerpendBC = lineBC.centerPerpendicular(new Segment(pointB, pointC));

        Point center = new Equation(centerPerpendAB, centerPerpendBC).calculate();
        return new Circle(center, pointA.distance(center));
    }
}
