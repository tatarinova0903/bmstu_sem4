package com.example.lab5;

import java.util.ArrayList;

public class Model {
    private final TranslateCoords translateCoords = new TranslateCoords(0, 0);
    private ArrayList<Point> figure = new ArrayList<>();

    public TranslateCoords getTranslateCoords() {
        return translateCoords;
    }

    public void cancel() {
        int lastIndex = figure.size() - 1;
        if (lastIndex > 0) {
            figure.remove(lastIndex);
        }
    }

    public void cancelAll() {
        figure.clear();
    }

    public void addPoint(int x, int y) {
        Point point = new Point(x, y);
        figure.add(point);
    }

    public ArrayList<Point> getFigure() {
        return figure;
    }
}
