package com.example.lab1;

public class Oval {
    private Point center;
    private Double width;
    private Double height;

    public Oval(Point center, Double width, Double height) {
        this.center = center;
        this.width = width;
        this.height = height;
    }

    public Oval() {
        this.center = new Point(0, 0);
        this.width = 0.0;
        this.height = 0.0;
    }

    public Point getCenter() {
        return center;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
