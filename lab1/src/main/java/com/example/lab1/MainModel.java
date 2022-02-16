package com.example.lab1;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class MainModel {
    private SetNumber current_set;
    private final ArrayList<Point> set1 = new ArrayList<>();
    private final ArrayList<Point> set2 = new ArrayList<>();
    private final ArrayList<Point> res = new ArrayList<>();
    private Circle circle = new Circle();
    private Oval oval = new Oval();
    private EditingMode isEditing = EditingMode.NONE;
    private SetNumber setToEdit = SetNumber.NONE;
    private Double currScale = 1.0;
    private LastAction lastAction = LastAction.NONE;
    private Point editedPoint = new Point();

    public Point getEditedPoint() {
        return editedPoint;
    }

    public void setEditedPoint(Point editedPoint) {
        this.editedPoint = editedPoint;
    }

    public LastAction getLastAction() {return lastAction; }

    public void setLastAction(LastAction lastAction) {
        this.lastAction = lastAction;
    }

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

    public Double getCurrScale() {
        return currScale;
    }

    public EditingMode getIsEditing() {
        return isEditing;
    }

    public void setIsEditing(EditingMode isEditing) {
        this.isEditing = isEditing;
    }

    public SetNumber getSetToEdit() {
        return setToEdit;
    }

    public void setSetToEdit(SetNumber setToEdit) {
        this.setToEdit = setToEdit;
    }

    public Circle getCircle() {
//        return this.circle;
        if (res.size() != 3) { return new Circle(); }
        return circleBy3Points(res.get(0), res.get(1), res.get(2));
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
        this.oval = new Oval();
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

    public void incrementCurrScale() {
        this.currScale += 0.1;
    }

    public void decrementCurrScale() {
        if (currScale > 0.3) { this.currScale -= 0.1; }
    }

    void calculateBtnDidTap() {
        if (set1.size() < 3) {
            showErrorAlert("В первом множестве не хватает точек");
            return;
        }
        if (set2.isEmpty()) {
            showErrorAlert("Во втором множестве не хватает точек");
            return;
        }
        Circle circle = findCircle();
        if (circle.isZero()) {
            showErrorAlert("Точки лежат на одной прямой");
        }
    }

    Point findClosestAndRemove(Point point) {
        AtomicReference<Double> minDistance = new AtomicReference<>(Double.MAX_VALUE);
        AtomicReference<Point> res = new AtomicReference<>(new Point());
        switch (current_set) {
            case NONE:
                break;
            case FIRST:
                set1.forEach(curPoint -> {
                    if (point.distance(curPoint) < minDistance.get()) {
                        res.set(curPoint);
                        minDistance.set(point.distance(curPoint));
                    }
                });
                break;
            case SECOND:
                set2.forEach(curPoint -> {
                    if (point.distance(curPoint) < minDistance.get()) {
                        res.set(curPoint);
                        minDistance.set(point.distance(curPoint));
                    }
                });
                break;
        }
        if (set1.remove(res.get())) { setToEdit = SetNumber.FIRST; }
        else if (set2.remove(res.get())) { setToEdit = SetNumber.SECOND; }
        return res.get();
    }

    void removePoint() {
        switch (current_set) {
            case NONE:
                break;
            case FIRST:
                int lastIndex = set1.size() - 1;
                if (lastIndex < 0) { break; }
                set1.remove(lastIndex);
                break;
            case SECOND:
                lastIndex = set2.size() - 1;
                if (lastIndex < 0) { break; }
                set2.remove(lastIndex);
                break;
        }
    }

    private Circle findCircle() {
        double minSquare = Double.MAX_VALUE;
        Circle res = new Circle();
        for (int i = 0 ; i < set1.size() - 2; i++) {
            for (int j = i + 1; j < set1.size() - 1; j++) {
                for (int k = j + 1; k < set1.size(); k++) {
                    Circle circle = circleBy3Points(set1.get(i), set1.get(j), set1.get(k));
                    if (!circle.getCenter().isInfinity() && circle.square() < minSquare && circle.contains(set2, 80)) {
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

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }
}
