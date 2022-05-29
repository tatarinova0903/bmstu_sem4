package com.example.lab10;

public class Limit {
    private double start;
    private double end;
    private double step;

    public Limit(double start, double end, double step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }
}
