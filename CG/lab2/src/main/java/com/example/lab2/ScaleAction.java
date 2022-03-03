package com.example.lab2;

public class ScaleAction {
    private double dx;
    private double dy;
    private double xc;
    private double yc;

    public ScaleAction(double dx, double dy, double xc, double yc) {
        this.dx = dx;
        this.dy = dy;
        this.xc = xc;
        this.yc = yc;
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

    public double getXc() {
        return xc;
    }

    public void setXc(double xc) {
        this.xc = xc;
    }

    public double getYc() {
        return yc;
    }

    public void setYc(double yc) {
        this.yc = yc;
    }
}
