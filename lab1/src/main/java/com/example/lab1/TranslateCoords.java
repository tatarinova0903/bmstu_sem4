package com.example.lab1;

public class TranslateCoords {
    private double x;
    private double y;

    public TranslateCoords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void incrementX() {
        this.x += 5;
    }

    public void incrementY() {
        this.y += 5;
    }

    public void decrementX() {
        this.x -= 5;
    }

    public void decrementY() {
        this.y -= 5;
    }
}
