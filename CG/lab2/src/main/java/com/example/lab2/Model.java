package com.example.lab2;

import java.util.ArrayList;
import java.util.Arrays;

public class Model {

    private final ArrayList<Point> points = new ArrayList<>();
    private final ArrayList<Integer> horse;
    private MoveAction moveAction;
    private ScaleAction scaleAction;
    private RotateAction rotateAction;

    public Model() {
        points.add(new Point(-78, -30)); // 0
        points.add(new Point(-88, 30)); // 1
        points.add(new Point(-71, 37)); // 2
        points.add(new Point(-74, 44)); // 3
        points.add(new Point(-79, 42)); // 4
        points.add(new Point(-55, 0)); // 5
        points.add(new Point(-60, -25)); // 6
        points.add(new Point(-50, -60)); // 7
        points.add(new Point(-42, -60)); // 8
        points.add(new Point(-52, -30)); // 9
        points.add(new Point(-30, 10)); // 10
        points.add(new Point(25, 0)); // 11
        points.add(new Point(35, -25)); // 12
        points.add(new Point(37, -60)); // 13
        points.add(new Point(45, -60)); // 14
        points.add(new Point(48, -5)); // 15
        points.add(new Point(65, 20)); // 16
        points.add(new Point(65, 33)); // 17
        points.add(new Point(78, 52)); // 18
        points.add(new Point(105, 40)); // 19
        points.add(new Point(110, 45)); // 20
        points.add(new Point(95, 75)); // 21
        points.add(new Point(93, 82)); // 22
        points.add(new Point(83, 75)); // 23
        points.add(new Point(63, 74)); // 24
        points.add(new Point(15, 55)); // 25
        points.add(new Point(-15, 55)); // 26
        points.add(new Point(-35, 60)); // 27

        horse = new ArrayList<>(Arrays.asList(
                0, 1, 2, 3, 4, 0, 4, 3, 2, 5, 6, 7, 8, 9, 6, 9, 10, 5, 10, 11, 12,
                13, 14, 15, 12, 15, 16, 25, 16, 17, 18, 19, 20, 21, 22, 23, 21, 23,
                18, 23, 24, 25, 16, 25, 11, 25, 26, 10, 26, 27, 3
        ));
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Integer> getHorse() {
        return horse;
    }

    void move(double dx, double dy) {
        moveAction = new MoveAction(dx, dy);
        points.forEach(point -> {
            point.move(dx, dy);
        });
    }

    void scale(double dx, double dy) {
        scaleAction = new ScaleAction(dx, dy);
        points.forEach(point -> {
            point.scale(dx, dy);
        });
    }

    void rotate(double x, double y, double degree) {
        rotateAction = new RotateAction(x, y, degree);
        points.forEach(point -> {
            point.rotate(x, y, degree);
        });
    }

    void cancel() {
        if (moveAction != null) {
            points.forEach(point -> {
                point.move(-moveAction.getDx(), -moveAction.getDy());
            });
        } else if (scaleAction != null) {
            points.forEach(point -> {
                point.scale(1 / scaleAction.getDx(), 1 / scaleAction.getDy());
            });
        } else if (rotateAction != null) {

        }
    }

    private void clearAction() {
        moveAction = null;
        scaleAction = null;
        rotateAction = null;
    }
}
