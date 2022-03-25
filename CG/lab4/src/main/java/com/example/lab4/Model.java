package com.example.lab4;

import java.util.ArrayList;

public class Model {

    private final ArrayList<Oval> circles = new ArrayList<>();
    private final TranslateCoords translateCoords = new TranslateCoords(0, 0);

    public ArrayList<Oval> getCircles() {
        return circles;
    }

    public void addCircle(Oval circle) {
        circles.add(circle);
    }

    public TranslateCoords getTranslateCoords() {
        return translateCoords;
    }

    public void cancelAll() {
        circles.clear();
    }

    public void cancel() {
        int lastIndex = circles.size() - 1;
        circles.remove(lastIndex);
    }
}
