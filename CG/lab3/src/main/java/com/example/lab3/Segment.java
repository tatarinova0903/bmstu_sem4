package com.example.lab3;

import javafx.scene.paint.Color;

public class Segment {
    Point start;
    Point end;
    Color color;
    AlgoritmType algoritmType;

    public Segment(Point start, Point end, Color color, AlgoritmType algoritmType) {
        this.start = start;
        this.end = end;
        this.color = color;
        this.algoritmType = algoritmType;
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
