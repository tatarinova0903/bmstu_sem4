package com.example.lab6;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Model {
    private final TranslateCoords translateCoords = new TranslateCoords(0, 0);
    private ArrayList<Figure> figures = new ArrayList<>();
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
        Figure lastFigure = getLastFigure();
        int lastIndex = lastFigure.getPoints().size() - 1;
        if (lastIndex > 0) {
            lastFigure.getPoints().remove(lastIndex);
        }
    }

    public void cancelAll() {
        figures.clear();
    }

    public void addPoint(int x, int y) {
        Point point = new Point(x, y);
        if (figures.size() == 0 || getLastFigure().isLocked()) {
            figures.add(new Figure());
        }
        getLastFigure().addPoint(point);
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public void clearPixels() {
        for (int i = 0; i < 1500; i++) {
            for (int j = 0; j < 1000; j++) {
                pixels[i][j] = Color.WHITE;
            }
        }
    }

    void lockFigure() {
        if (figures.size() == 0) { return; }
        getLastFigure().lock();
    }

    private Figure getLastFigure() {
        int lastIndex = figures.size() - 1;
        return figures.get(lastIndex);
    }
}

