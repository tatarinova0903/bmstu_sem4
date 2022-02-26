package com.example.lab2;

import java.util.ArrayList;
import java.util.Arrays;

public class Model {

    private final ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Integer> road;

    public Model() {
        points.add(new Point(-160, -30)); // 0
        points.add(new Point(-170, 30)); // 1
        points.add(new Point(-150, 40)); // 2
        points.add(new Point(-155, 50)); // 3
        points.add(new Point(-165, 45)); // 4
//        points.add(new Point(-110, 30)); // 5
//        points.add(new Point(-110, 30)); // 6
//        points.add(new Point(-110, 30)); // 7
//        points.add(new Point(-110, 30)); // 8
//        points.add(new Point(-110, 30)); // 9
//        points.add(new Point(-110, 30)); // 10
//        points.add(new Point(-110, 30)); // 11
//        points.add(new Point(-110, 30)); // 12
//        points.add(new Point(-110, 30)); // 13
//        points.add(new Point(-110, 30)); // 14
//        points.add(new Point(-110, 30)); // 15
//        points.add(new Point(-110, 30)); // 16
//        points.add(new Point(-110, 30)); // 17
//        points.add(new Point(-110, 30)); // 18
//        points.add(new Point(-110, 30)); // 19
//        points.add(new Point(-110, 30)); // 20
//        points.add(new Point(-110, 30)); // 21
//        points.add(new Point(-110, 30)); // 22
//        points.add(new Point(-110, 30)); // 23
//        points.add(new Point(-110, 30)); // 24
//        points.add(new Point(-110, 30)); // 25
//        points.add(new Point(-110, 30)); // 26
//        points.add(new Point(-110, 30)); // 27

        road = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 0, 4, 3));
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Integer> getRoad() {
        return road;
    }

    void move(double dx, double dy) {
        points.forEach(point -> {
            point.move(dx, dy);
        });
    }
}
