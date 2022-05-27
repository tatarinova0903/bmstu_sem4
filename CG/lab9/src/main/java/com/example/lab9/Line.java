package com.example.lab9;

public class Line {
    private double coefA;
    private double coefB;
    private double coefC;

    public Line(double coefA, double coefB, double coefC) {
        this.coefA = coefA;
        this.coefB = coefB;
        this.coefC = coefC;
    }

    public Line(Point point1, Point point2) {
        this.coefA = point2.getY() - point1.getY();
        this.coefB = point1.getX() - point2.getX();
        this.coefC = point1.getY() * point2.getX() - point1.getX() * point2.getY();
    }

    public double getCoefA() {
        return coefA;
    }

    public double getCoefB() {
        return coefB;
    }

    public double getCoefC() {
        return coefC;
    }
}

