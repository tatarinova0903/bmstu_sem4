package com.example.lab4;

import java.util.ArrayList;

public class Model {

    private final ArrayList<Oval> ovals = new ArrayList<>();
    private final ArrayList<Circle> circles = new ArrayList<>();
    private final TranslateCoords translateCoords = new TranslateCoords(0, 0);

    public ArrayList<Oval> getOvals() {
        return ovals;
    }

    public ArrayList<Circle> getCircles() {
        return circles;
    }

    public void addOval(Oval oval) {
        ovals.add(oval);
    }

    public void addCircle(Circle circle) { circles.add(circle); }

    public TranslateCoords getTranslateCoords() {
        return translateCoords;
    }

    public void cancelAll() {
        ovals.clear();
        circles.clear();
    }

    public void cancel() {
        if (ovals.size() != 0) {
            int lastIndex = ovals.size() - 1;
            ovals.remove(lastIndex);
        } else if (circles.size() != 0) {
            int lastIndex = circles.size() - 1;
            circles.remove(lastIndex);
        }
    }
}
