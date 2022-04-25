package com.example.lab6;

public class Line {
    private Point start;
    private Point end;

    public Line(Point start, Point end) {
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

    boolean isHorizontal() {
        return Math.abs(this.start.getY() - this.end.getY()) < 1e-6;
    }

    double getX(double y) {
        double coefA = end.getY() - start.getY();
        double coefB = start.getX() - end.getX();
        double coefC = start.getY() * end.getX() - start.getX() * end.getY();
        return ((-coefB * y - coefC) / coefA);
    }

    public double getY(double x) {
        double coefA = end.getY() - start.getY();
        double coefB = start.getX() - end.getX();
        double coefC = start.getY() * end.getX() - start.getX() * end.getY();
        return ((-coefA * x - coefC) / coefB);
    }
}

