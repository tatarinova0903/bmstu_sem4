package com.example.lab4;

import javafx.scene.paint.Color;

public class Circle {
    private Point center;
    private double rad;
    private Color color;
    private AlgoritmType algoritmType;

    public Circle(Point center, double rad, Color color, AlgoritmType algoritmType) {
        this.center = center;
        this.rad = rad;
        this.color = color;
        this.algoritmType = algoritmType;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getRad() {
        return rad;
    }

    public void setRad(double rad) {
        this.rad = rad;
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
