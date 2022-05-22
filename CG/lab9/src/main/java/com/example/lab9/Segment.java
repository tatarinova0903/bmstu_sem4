package com.example.lab9;

public class Segment {
    private Point start;
    private Point end;

    public Segment() {
        this.start = new Point();
        this.end = new Point();
    }

    public Segment(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public double getDX() {
        return this.end.getX() - this.start.getX();
    }

    public double getDY() {
        return this.end.getY() - this.start.getY();
    }
}

