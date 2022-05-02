package com.example.lab6;

public class TranslateCoords {
    private int x;
    private int y;

    public TranslateCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void incrementX() {
        this.x = 5;
    }

    public void incrementY() {
        this.y = -5;
    }

    public void decrementX() {
        this.x = -5;
    }

    public void decrementY() {
        this.y = 5;
    }

    public void clear() {
        this.x = 0;
        this.y = 0;
    }
}

