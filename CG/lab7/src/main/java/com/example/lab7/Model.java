package com.example.lab7;

import java.util.ArrayList;

public class Model {

    private Clipper clipper = new Clipper();
    private final ArrayList<Segment> figure = new ArrayList<>();
    private final ArrayList<Segment> res = new ArrayList<>();
    private final TranslateCoords translateCoords = new TranslateCoords(0, 0);
    private Action action = null;
    private Segment curSegment = new Segment();
    private Point firstPoint = new Point(), secondPoint = new Point();
    private Segment resSegment = new Segment();
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

    public void cancelAll() {
        clipper = new Clipper();
        firstPoint = new Point();
        secondPoint = new Point();
        resSegment = new Segment();
        curSegment = new Segment();
        res.clear();
        figure.clear();
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
                clipper = new Clipper();
            }
        }
    }

    public Clipper getClipper() {
        return clipper;
    }

    public void setClipper(Clipper clipper) {
        this.clipper = clipper;
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

    public void addPointToRes(Point point) {
        if (!resSegment.getStart().exists()) {
            resSegment.setStart(point);
        } else {
            resSegment.setEnd(point);
            res.add(new Segment(resSegment.getStart(), resSegment.getEnd()));
            resSegment = new Segment();
        }
    }

    public void addSegmentToRes(Segment segment) {
        res.add(segment);
    }

    public boolean setClippPoint(Point point) {
        if (!firstPoint.exists()) {
            firstPoint = point;
            return false;
        } else {
            secondPoint = point;
            double xLeft = Math.min(firstPoint.getX(), secondPoint.getX());
            double xRight = Math.max(firstPoint.getX(), secondPoint.getX());
            double yUp = Math.max(firstPoint.getY(), secondPoint.getY());
            double yDown = Math.min(firstPoint.getY(), secondPoint.getY());
            setClipper(new Clipper(xLeft, xRight, yUp, yDown));
            firstPoint = new Point();
            secondPoint = new Point();
            return true;
        }
    }
}
