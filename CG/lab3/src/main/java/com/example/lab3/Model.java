package com.example.lab3;

public class Model {
    private Double currScale = 1.0;
    private TranslateCoords translateCoords = new TranslateCoords(0.0,0.0);

    public Double getCurrScale() {
        return currScale;
    }

    public void incrementCurrScale() {
        if (currScale < 0.2) { currScale += 0.01; }
        else { currScale += 0.1; }
    }

    public void decrementCurrScale() {
        if (currScale > 0.2) { this.currScale -= 0.1; }
        else if (currScale > 0.02) { this.currScale -= 0.01; }
    }

    public TranslateCoords getTranslateCoords() {
        return translateCoords;
    }

    void move() {

    }
}
