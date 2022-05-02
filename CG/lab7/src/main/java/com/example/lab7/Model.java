package com.example.lab7;

import java.util.ArrayList;

public class Model {

    private Clipper clipper = new Clipper();
    private final ArrayList<Point> figure = new ArrayList<>();
    private final TranslateCoords translateCoords = new TranslateCoords(0, 0);
    private Action action = null;
    private Point firstPoint = new Point(0, 0), secondPoint = new Point(0, 0);

    public TranslateCoords getTranslateCoords() {
        return translateCoords;
    }

    public void cancelAll() {
        clipper = new Clipper();
        figure.clear();
    }

    public void cancel() {

    }

    public Clipper getClipper() {
        return clipper;
    }

    public void setClipper(Clipper clipper) {
        this.clipper = clipper;
    }

    public ArrayList<Point> getFigure() {
        return figure;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void addPoint(Point point) {
        figure.add(point);
    }

    void lockFigure() {
        if (figure.size() == 0) { return; }
        int size = figure.size();
        if (size == 0) { return; }
        if (!figure.get(0).isEqual(figure.get(size - 1))) {
            figure.add(new Point(figure.get(0).getX(), figure.get(0).getY()));
        }
    }

    public boolean setClippPoint(Point point) {
        if (firstPoint.getX() == 0) {
            firstPoint = point;
            return false;
        } else {
            secondPoint = point;
            int xLeft = Math.min(firstPoint.getX(), secondPoint.getX());
            int xRight = Math.max(firstPoint.getX(), secondPoint.getX());
            int yUp = Math.max(firstPoint.getY(), secondPoint.getY());
            int yDown = Math.min(firstPoint.getY(), secondPoint.getY());
            setClipper(new Clipper(xLeft, xRight, yUp, yDown));
            return true;
        }
    }
}
