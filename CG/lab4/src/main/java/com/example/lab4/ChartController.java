package com.example.lab4;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class ChartController extends AnchorPane {
    public ChartController(AlgoritmType algoritm, ArrayList<Integer> angles, ArrayList<Integer> steps) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
        bc.setTitle(Algoritm.getName(algoritm));
        xAxis.setLabel("Угол наклона");
        yAxis.setLabel("Количество ступенек");
        XYChart.Series data = new XYChart.Series();
        if (angles.size() != steps.size()) { return; }
        for (int i = 0; i < steps.size(); i++) {
            System.out.println(steps.get(i));
            data.getData().add(new XYChart.Data(Integer.toString(angles.get(i)), steps.get(i)));
        }
        bc.getData().add(data);
        this.getChildren().add(bc);
    }
}
