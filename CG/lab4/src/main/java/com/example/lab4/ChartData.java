package com.example.lab4;

import java.util.ArrayList;

public class ChartData {
    private AlgoritmType algoritmType;
    private ArrayList<Double> axis;
    private ArrayList<Long> time;

    public ChartData(AlgoritmType algoritmType, ArrayList<Double> axis, ArrayList<Long> time) {
        this.algoritmType = algoritmType;
        this.axis = axis;
        this.time = time;
    }

    public AlgoritmType getAlgoritmType() {
        return algoritmType;
    }

    public void setAlgoritmType(AlgoritmType algoritmType) {
        this.algoritmType = algoritmType;
    }

    public ArrayList<Double> getAxis() {
        return axis;
    }

    public void setAxis(ArrayList<Double> axis) {
        this.axis = axis;
    }

    public ArrayList<Long> getTime() {
        return time;
    }

    public void setTime(ArrayList<Long> time) {
        this.time = time;
    }
}
