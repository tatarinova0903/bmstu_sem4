package com.example.lab6;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class CustomColor {
    private final ArrayList<Color> colors = new ArrayList<>();

    public CustomColor() {
        colors.add(Color.BLACK);
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.CHOCOLATE);
        colors.add(Color.ORANGE);
        colors.add(Color.GREEN);
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public ArrayList<String> getColorsStr() {
        ArrayList<String> colors = new ArrayList<>();
        this.colors.forEach(color -> {
            if (color == Color.RED) { colors.add("RED"); }
            if (color == Color.BLUE) { colors.add("BLUE"); }
            if (color == Color.YELLOW) { colors.add("YELLOW"); }
            if (color == Color.CHOCOLATE) { colors.add("CHOCOLATE"); }
            if (color == Color.ORANGE) { colors.add("ORANGE"); }
            if (color == Color.GREEN) { colors.add("GREEN"); }
            if (color == Color.BLACK) { colors.add("BLACK"); }
        });
        return colors;
    }
}
