package com.example.lab9;

public class Vector {
    private double a;
    private double b;

    public Vector() {
        this.a = Double.POSITIVE_INFINITY;
        this.b = Double.POSITIVE_INFINITY;
    }

    public Vector(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public void negA() {
        this.a *= (-1);
    }

    public void negB() {
        this.b *= (-1);
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }
}

