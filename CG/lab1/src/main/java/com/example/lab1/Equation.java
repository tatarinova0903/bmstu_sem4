package com.example.lab1;

public class Equation {
    private Line line1;
    private Line line2;

    public Equation(Line line1, Line line2) {
        this.line1 = line1;
        this.line2 = line2;
    }

    Point calculate() {
        double delta = line1.getCoefA() * line2.getCoefB() - line1.getCoefB() * line2.getCoefA();
        double deltaX = -line1.getCoefC() * line2.getCoefB() - line1.getCoefB() * (-line2.getCoefC());
        double deltaY = line1.getCoefA() * (-line2.getCoefC()) + line1.getCoefC() * line2.getCoefA();
        return new Point(deltaX / delta, deltaY / delta);
    }
}
