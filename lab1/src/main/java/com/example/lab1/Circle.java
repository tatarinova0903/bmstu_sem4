package com.example.lab1;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Circle {
    Point center;
    Double radius;

    public Circle(Point center, Double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Circle() { }

    public Point getCenter() {
        return center;
    }

    public Double getRadius() {
        return radius;
    }

    double square() {
        return Math.PI * this.radius * this.radius;
    }

    Boolean contains(ArrayList<Point> points, Integer percent) {
        AtomicInteger count = new AtomicInteger();
        points.forEach ( point -> {
            if (this.center.distance(point) < this.radius) {
                count.getAndIncrement();
            }
        });
        return count.get() >= points.size() * percent / 100;
    }
}
