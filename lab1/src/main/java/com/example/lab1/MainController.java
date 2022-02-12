package com.example.lab1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MainController {
    @FXML
    private Button calculateButton;

    @FXML
    private Button enterSetButton1;

    @FXML
    private Button enterSetButton2;

    @FXML
    private Button revertButton;

    @FXML
    private Canvas canvas;

    private GraphicsContext gc;
    private MainModel model;

    @FXML
    void initialize() {
        model = new MainModel();
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        int diameter = 5;
        Point point = new Point((int)event.getX(), (int)event.getY());
        gc.fillOval(point.getX(), point.getY(), diameter, diameter);
        model.addToSet(point);
    }

    @FXML
    void enterSetButton1Tap(ActionEvent event) {
        gc.setFill(Color.CORAL);
        model.setCurrent_set(SetNumber.FIRST);
    }

    @FXML
    void enterSetButton2Tap(ActionEvent event) {
        gc.setFill(Color.OLIVE);
        model.setCurrent_set(SetNumber.SECOND);
    }
}