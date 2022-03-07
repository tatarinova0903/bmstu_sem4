package com.example.lab3;

public class CanvasPoint {
    private Point ideal;
    private Point notIdeal;

    public CanvasPoint() {
        this.ideal = new Point();
        this.notIdeal = new Point();
    }

    public CanvasPoint(Point ideal, Point notIdeal) {
        this.ideal = ideal;
        this.notIdeal = notIdeal;
    }

    public Point getIdeal() {
        return ideal;
    }

    public void setIdeal(Point ideal) {
        this.ideal = ideal;
    }

    public Point getNotIdeal() {
        return notIdeal;
    }

    public void setNotIdeal(Point notIdeal) {
        this.notIdeal = notIdeal;
    }
}
