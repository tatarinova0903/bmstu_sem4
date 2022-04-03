package com.example.lab4;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ChartController extends AnchorPane {
    public ChartController(ArrayList<ChartData> chartData) {
        ArrayList<BarChart<String,Number>> barCharts = new ArrayList<>();
        chartData.forEach(el -> {
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            final BarChart<String,Number> bc = new BarChart<>(xAxis, yAxis);
            bc.setTitle(Algoritm.getName(el.getAlgoritmType()));
            xAxis.setLabel("Cумма полуосей");
            yAxis.setLabel("Время");
            XYChart.Series data = new XYChart.Series();
            if (el.getAxis().size() != el.getTime().size()) { return; }
            for (int i = 0; i < el.getAxis().size(); i++) {
                data.getData().add(new XYChart.Data(Double.toString(el.getAxis().get(i)), el.getTime().get(i)));
            }
            bc.getData().add(data);
            barCharts.add(bc);
        });
        HBox box1 = new HBox(barCharts.get(0), barCharts.get(1));
        HBox box2 = new HBox(barCharts.get(2), barCharts.get(3));
        VBox box = new VBox(box1, box2);
        this.getChildren().add(box);
    }
}
