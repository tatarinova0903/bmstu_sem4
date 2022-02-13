package com.example.lab1;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

class ResizableCanvas extends Canvas {
    private GraphicsContext gc = getGraphicsContext2D();
    private double oldWidth = getWidth();
    private double oldHeight = getHeight();
    private MainModel model = new MainModel();

    public ResizableCanvas() {
        model.setCurrent_set(SetNumber.FIRST);
        gc.setFill(Color.WHITE);
        setOnMouseClicked(mouseEvent -> onMouseClicked(mouseEvent));
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    private void draw() {
        double width = getWidth();
        double height = getHeight();
        if (width == 0 || height == 0) { return; }
        double deltaWidth = width / (oldWidth == 0 ? width : oldWidth);
        double deltaHeight = height / (oldHeight == 0 ? height : oldHeight);
        gc.clearRect(0, 0, width, height);

        model.getSet1().forEach(point -> {
            point.setX(point.getX() * deltaWidth);
            point.setY(point.getY() * deltaHeight);
            gc.fillOval(point.getX(), point.getY(), Constants.pointDiameter, Constants.pointDiameter);
        });

        model.getSet2().forEach(point -> {
            point.setX(point.getX() * deltaWidth);
            point.setY(point.getY() * deltaHeight);
            gc.fillOval(point.getX(), point.getY(), Constants.pointDiameter, Constants.pointDiameter);
        });

        oldWidth = width;
        oldHeight = height;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

    void onMouseClicked(MouseEvent event) {
        int diameter = 5;
        Point point = new Point((int)event.getX(), (int)event.getY());
        gc.fillOval(point.getX(), point.getY(), diameter, diameter);
        model.addToSet(point);
    }

    void inputFirstSetBtnDidTap(ActionEvent event) {
        gc.setFill(Color.CORAL);
        model.setCurrent_set(SetNumber.FIRST);
    }

    void inputSecondSetBtnDidTap(ActionEvent event) {
        gc.setFill(Color.OLIVE);
        model.setCurrent_set(SetNumber.SECOND);
    }

    void cancelBtnDidTap() {

    }

    void calculateBtnDidTap() {
        Circle circle = model.calculateBtnDidTap();
        gc.setFill(Color.GREEN);
        double radius = circle.getRadius();
        double diameter = circle.getRadius() * 2;
        gc.fillOval(circle.getCenter().getX() - radius, circle.getCenter().getY() - radius, diameter, diameter);
    }
}

class Constants {
    static int pointDiameter = 5;
}
