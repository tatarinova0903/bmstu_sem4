package com.example.lab2;

public class RotateAction {
    private double dx;
    private double dy;
    private double degree;

    public RotateAction(double dx, double dy, double degree) {
        this.dx = dx;
        this.dy = dy;
        this.degree = degree;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }
}
