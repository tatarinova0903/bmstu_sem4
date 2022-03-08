package com.example.lab3;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class ResizableCanvas extends Canvas {
    private final GraphicsContext gc = getGraphicsContext2D();
    private double oldWidth = getWidth();
    private double oldHeight = getHeight();
    private final Model model = new Model();
    private Color lastBackgroundColor = Color.WHITE;
    private Color backgroundColor = Color.WHITE;

    public ResizableCanvas(MainController controller) {
        setOnMouseMoved(mouseEvent -> {
            Point ideal = translatePointFromReal(new Point(mouseEvent.getX(), mouseEvent.getY()));
            controller.setCurrentMousePosition(ideal.getX(), ideal.getY());
        });
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    private void draw() {
        double width = getWidth();
        double height = getHeight();
        if (width == 0 || height == 0) { return; }

        gc.clearRect(0, 0, width, height);
        gc.setFill(backgroundColor);
        gc.fillRect(0, 0, width, height);

        drawAxes();
        model.getSegments().forEach(segment -> {
            drawSegment(segment);
        });

        oldWidth = width;
        oldHeight = height;
    }

    @Override
    public boolean isResizable() { return true; }

    @Override
    public double prefWidth(double height) { return getWidth(); }

    @Override
    public double prefHeight(double width) { return getHeight(); }

    void drawBtnDidTap(Point start, Point end, AlgoritmType algoritmType, Color segmentColor, Color backgroundColor) {
        Segment segment = new Segment(start, end, segmentColor, algoritmType);
        model.addSegment(segment);
        changeBackgroundColor(backgroundColor);
        draw();
    }

    void cancelBtnDidTap() {
        model.cancel();
        backgroundColor = lastBackgroundColor;
        draw();
    }

    void cancelAllBtnDidTap() {
        changeBackgroundColor(Color.WHITE);
        model.cancelAll();
        draw();
    }

    void scale(boolean isPlus) {
        if (isPlus) { model.incrementCurrScale(); }
        else { model.decrementCurrScale(); }
        draw();
    }

    void goTo(Direction direction) {
        switch (direction) {
            case RIGHT -> {
                model.getTranslateCoords().incrementX();
            }
            case LEFT -> {
                model.getTranslateCoords().decrementX();
            }
            case DOWN -> {
                model.getTranslateCoords().decrementY();
            }
            case UP -> {
                model.getTranslateCoords().incrementY();
            }
        }
        draw();
    }

    Point translatePointFromIdeal(Point point) {
        double newX = (point.getX() + model.getTranslateCoords().getX()) * model.getCurrScale();
        double newY = (point.getY() + model.getTranslateCoords().getY()) * model.getCurrScale();
        newX = newX + getWidth() / 2;
        newY = (newY - getHeight() / 2) * (-1);
        return new Point(newX, newY);
    }

    Point translatePointFromReal(Point point) {
        double newX = point.getX() - getWidth() / 2;
        double newY = point.getY() * (-1) + getHeight() / 2;
        newX = newX / model.getCurrScale() - model.getTranslateCoords().getX();
        newY = newY / model.getCurrScale() - model.getTranslateCoords().getY();
        return new Point(newX, newY);
    }

    private void changeBackgroundColor(Color newColor) {
        lastBackgroundColor = backgroundColor;
        backgroundColor = newColor;
    }

    private void drawAxes() {
        if (backgroundColor == Color.BLACK) { gc.setStroke(Color.WHITE); }
        else { gc.setStroke(Color.BLACK); }
        gc.setLineWidth(0.4);
        Point screenCenter = translatePointFromIdeal(new Point(0, 0));
        gc.strokeLine(0, screenCenter.getY(), getWidth(), screenCenter.getY());
        gc.strokeLine(screenCenter.getX(), 0, screenCenter.getX(), getHeight());
        gc.setLineWidth(1.0);
    }

    private void drawSegment(Segment segment) {
        gc.setStroke(segment.getColor());
        Point startRealPoint = translatePointFromIdeal(segment.getStart());
        Point endRealPoint = translatePointFromIdeal(segment.getEnd());
        gc.strokeLine(startRealPoint.getX(), startRealPoint.getY(),
                endRealPoint.getX(), endRealPoint.getY());
    }
}