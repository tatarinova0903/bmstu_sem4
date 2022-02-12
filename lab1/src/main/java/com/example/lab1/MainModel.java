package com.example.lab1;

import java.util.ArrayList;

public class MainModel {
    private SetNumber current_set;

    private ArrayList<Point> set1 = new ArrayList<Point>();
    private ArrayList<Point> set2 = new ArrayList<Point>();

    public void setCurrent_set(SetNumber current_set) {
        this.current_set = current_set;
    }

    void addToSet(Point point) {
        if (current_set == null) { return; }
        switch (current_set) {
            case FIRST:
                set1.add(point);
                break;
            case SECOND:
                set2.add(point);
                break;
        }
    }
}
