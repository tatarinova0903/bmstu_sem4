package com.example.lab7;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

class ResizableCanvas extends Canvas {
    private final int INTENSITY = 255;
    private final GraphicsContext gc = getGraphicsContext2D();
    private final PixelWriter pixelWriter = gc.getPixelWriter();
    private double oldWidth = getWidth();
    private double oldHeight = getHeight();
    private final Model model = new Model();
    private Color lastBackgroundColor = Color.WHITE;
    private Color backgroundColor = Color.WHITE;
    private double scale = 1.0;
    private MainController controller;

    public ResizableCanvas(MainController controller) {
        this.controller = controller;
        setOnMouseMoved(mouseEvent -> {
//            Point ideal = translatePointFromReal(new Point(mouseEvent.getX(), mouseEvent.getY()));
            controller.setCurrentMousePosition(mouseEvent.getX(), mouseEvent.getY());
        });
        setOnMouseClicked(this::onMouseClicked);
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


        oldWidth = width;
        oldHeight = height;
    }

    @Override
    public boolean isResizable() { return true; }

    @Override
    public double prefWidth(double height) { return getWidth(); }

    @Override
    public double prefHeight(double width) { return getHeight(); }

    void cancelBtnDidTap() {
        model.cancel();
        backgroundColor = lastBackgroundColor;
        draw();
    }

    void cancelAllBtnDidTap() {
        model.cancelAll();
        draw();
    }

    public void lockBtnDidTap() {
        model.lockFigure();
        drawFigure();
    }

    void scale(boolean isPlus) {
        if (isPlus) { scale += 0.2; }
        else if (scale > 1) { scale -= 0.1; }
        this.setScaleX(scale);
        this.setScaleY(scale);
        double newWidth = getWidth() * 3;
        double newX = newXForScale(scale);
        double newHeight = getHeight() * 3;
        double newY = newYForScale(scale);
        Rectangle rect = new Rectangle(newX, newY, newWidth, newHeight);
        this.setClip(rect);
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

    public void drawClipperBtnDidTap(int xLeft, int xRight, int yUp, int yDown, Color resColor) {
        model.setClipper(new Clipper(xLeft, xRight, yUp, yDown));
        drawClipper(model.getClipper());
    }

    public void addPointBtnDidTap(int x, int y) {
        model.addPoint(new Point(x, y));
        drawPoint(x, y, Color.BLACK);
        drawFigure();
    }

    private void onMouseClicked(MouseEvent mouseEvent) {
        addPointBtnDidTap((int) mouseEvent.getX(), (int) mouseEvent.getY());
    }

    private void drawClipper(Clipper clipper) {
        gc.strokeLine(clipper.getxLeft(), clipper.getyDown(), clipper.getxLeft(), clipper.getyUp());
        gc.strokeLine(clipper.getxLeft(), clipper.getyUp(), clipper.getxRight(), clipper.getyUp());
        gc.strokeLine(clipper.getxRight(), clipper.getyUp(), clipper.getxRight(), clipper.getyDown());
        gc.strokeLine(clipper.getxRight(), clipper.getyDown(), clipper.getxLeft(), clipper.getyDown());
    }

    private void drawFigure() {
        int pointsCount = model.getFigure().size();
        if (pointsCount < 2) { return; }
        ArrayList<Point> figure = model.getFigure();
        for (int i = 0; i < pointsCount - 1; i++) {
            gc.strokeLine(figure.get(i).getX(), figure.get(i).getY(), figure.get(i + 1).getX(), figure.get(i + 1).getY());
        }
    }

    private void drawPoint(int x, int y, Color color) {
        pixelWriter.setColor(x, y, color);
    }

    private double newXForScale(Double scale) {
        double oldWidth = getWidth();
        double newWidth = oldWidth * scale;
        double newX = (newWidth - oldWidth) / 2;
        return newX * oldWidth / newWidth;
    }

    private double newYForScale(Double scale) {
        double oldHeight = getHeight();
        double newHeight = oldHeight * scale;
        double newY = (newHeight - oldHeight) / 2;
        return newY * oldHeight / newHeight;
    }

    Point translatePointFromIdeal(Point point) {
        double newX = (point.getX() + model.getTranslateCoords().getX());
        double newY = (point.getY() + model.getTranslateCoords().getY());
        newX = newX + getWidth() / 2;
        newY = (newY - getHeight() / 2) * (-1);
        return new Point(newX, newY);
    }

    Point translatePointFromReal(Point point) {
        double newX = point.getX() - getWidth() / 2;
        double newY = point.getY() * (-1) + getHeight() / 2;
        newX = newX - model.getTranslateCoords().getX();
        newY = newY - model.getTranslateCoords().getY();
        return new Point(newX, newY);
    }
}
