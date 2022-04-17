package com.example.lab5;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Model {
    private final TranslateCoords translateCoords = new TranslateCoords(0, 0);
    private ArrayList<Point> figure = new ArrayList<>();
    private Color[][] pixels = new Color[1500][1000];

    public Model() {
        for (int i = 0; i < 1500; i++) {
            for (int j = 0; j < 1000; j++) {
                pixels[i][j] = Color.WHITE;
            }
        }
    }

    public Color[][] getPixels() {
        return pixels;
    }

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

    public void clearPixels() {
        for (int i = 0; i < 1500; i++) {
            for (int j = 0; j < 1000; j++) {
                pixels[i][j] = Color.WHITE;
            }
        }
    }
}
