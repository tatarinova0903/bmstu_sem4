package com.example.lab1;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Circle {
    private final double EPS = 1e-7;

    private Point center;
    private Double radius;

    public Circle(Point center, Double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Circle() {
        this.center = new Point(0,0);
        this.radius = 0.0;
    }

    public Point getCenter() {
        return center;
    }

    public Double getRadius() {
        return radius;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    double square() {
        return Math.PI * this.radius * this.radius;
    }

    boolean contains(ArrayList<Point> points, Integer percent) {
        AtomicInteger count = new AtomicInteger();
        points.forEach ( point -> {
            if (this.center.distance(point) < this.radius) {
                count.getAndIncrement();
            }
        });
        return count.get() > points.size() * percent / 100 || Math.abs(count.get() - points.size() * percent / 100) < EPS;
    }

    boolean isZero() {
        return Math.abs(center.getX()) < EPS && Math.abs(center.getY()) < EPS && Math.abs(radius) < EPS;
    }
}
