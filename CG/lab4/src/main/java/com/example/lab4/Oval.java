package com.example.lab4;

import javafx.scene.paint.Color;

public class Oval {
    private Point center;
    private double xAxis;
    private double yAxis;
    private Color color;
    private AlgoritmType algoritmType;

    public Oval(Point center, double xAxis, double yAxis, Color color, AlgoritmType algoritmType) {
        this.center = center;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.color = color;
        this.algoritmType = algoritmType;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getxAxis() {
        return xAxis;
    }

    public void setxAxis(double xAxis) {
        this.xAxis = xAxis;
    }

    public double getyAxis() {
        return yAxis;
    }

    public void setyAxis(double yAxis) {
        this.yAxis = yAxis;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public AlgoritmType getAlgoritmType() {
        return algoritmType;
    }

    public void setAlgoritmType(AlgoritmType algoritmType) {
        this.algoritmType = algoritmType;
    }
}
