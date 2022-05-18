package com.example.lab8;

import java.util.ArrayList;

public class Model {

    private final ArrayList<Point> clipper = new ArrayList<>();
    private int clipperSize = 0;
    private final ArrayList<Segment> figure = new ArrayList<>();
    private final ArrayList<Segment> res = new ArrayList<>();
    private final TranslateCoords translateCoords = new TranslateCoords(0, 0);
    private Action action = null;
    private Segment curSegment = new Segment();
    boolean clipBtnDidTap = false;

    public TranslateCoords getTranslateCoords() {
        return translateCoords;
    }

    public boolean isClipBtnDidTap() {
        return clipBtnDidTap;
    }

    public void setClipBtnDidTap(boolean clipBtnDidTap) {
        this.clipBtnDidTap = clipBtnDidTap;
    }

    public int getClipperSize() {
        clipperSize = clipper.size() - 1;
        return clipperSize;
    }

    public void cancelAll() {
        clipper.clear();
        curSegment = new Segment();
        res.clear();
        figure.clear();
        clipBtnDidTap = false;
    }

    public void cancel() {
        if (action == null) { return; }
        switch (action) {
            case FIGURE -> {
                int lastIndex = figure.size() - 1;
                if (lastIndex < 0) { return; }
                figure.remove(lastIndex);
            }
            case CLIPPER -> {
                int lastIndex = clipper.size() - 1;
                if (lastIndex < 0) { return; }
                clipper.remove(lastIndex);
            }
        }
        res.clear();
    }

    public ArrayList<Point> getClipper() {
        return clipper;
    }

    public ArrayList<Segment> getFigure() {
        return figure;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public ArrayList<Segment> getRes() {
        return res;
    }

    public void addPointToFigure(Point point) {
        if (!curSegment.getStart().exists()) {
            curSegment.setStart(point);
        } else {
            curSegment.setEnd(point);
            figure.add(new Segment(curSegment.getStart(), curSegment.getEnd()));
            curSegment = new Segment();
        }
    }

    public void addPointToClipper(Point point) {
        if (clipper.size() > 1) {
            Point firstPoint = clipper.get(0);
            Point lastPoint = clipper.get(clipper.size() - 1);
            if (firstPoint.isEqual(lastPoint)) {
                return;
            }
        }
        clipper.add(point);
    }

    public void lockClipper() {
        if (clipper.size() < 1) { return; }
        Point firstPoint = clipper.get(0);
        clipper.add(new Point(firstPoint.getX(), firstPoint.getY()));
    }

    public void addSegmentToRes(Segment segment) {
        res.add(segment);
    }
}
