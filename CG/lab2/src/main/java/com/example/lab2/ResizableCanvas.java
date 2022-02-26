package com.example.lab2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

class ResizableCanvas extends Canvas {
    private final GraphicsContext gc = getGraphicsContext2D();
    private double oldWidth = getWidth();
    private double oldHeight = getHeight();
    private final MainController controller;
    private final Model model = new Model();

    public ResizableCanvas(MainController controller) {
        this.controller = controller;
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    public Model getModel() {
        return model;
    }

    private void draw() {
        double width = getWidth();
        double height = getHeight();
        if (width == 0 || height == 0) { return; }
        double deltaWidth = width / (oldWidth == 0 ? width : oldWidth);
        double deltaHeight = height / (oldHeight == 0 ? height : oldHeight);
        gc.clearRect(0, 0, width * 3, height * 3);



        oldWidth = width;
        oldHeight = height;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) { return getWidth(); }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}