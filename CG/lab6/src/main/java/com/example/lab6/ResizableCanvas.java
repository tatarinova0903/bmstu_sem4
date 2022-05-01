package com.example.lab6;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

class ResizableCanvas extends Canvas {
    private final int INTENSITY = 255;
    private final GraphicsContext gc = getGraphicsContext2D();
    private final PixelWriter pixelWriter = gc.getPixelWriter();
    private double oldWidth = getWidth();
    private double oldHeight = getHeight();
    private final Model model = new Model();
    private Color figureColor = Color.BLACK;
    private double scale = 1.0;
    private MainController controller;

    public ResizableCanvas(MainController controller) {
        this.controller = controller;
        setOnMouseMoved(mouseEvent -> {
            Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
            controller.setCurrentMousePosition(point.getX(), point.getY());
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
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
        model.clearPixels();

//        fillBtnDidTap(figureColor, true);

        oldWidth = width;
        oldHeight = height;
    }

    @Override
    public boolean isResizable() { return true; }

    @Override
    public double prefWidth(double height) { return getWidth(); }

    @Override
    public double prefHeight(double width) { return getHeight(); }

    void fillBtnDidTap(Color borderColor, Color figureColor, boolean withoutTimeSleep) {
        ArrayList<Figure> figures = model.getFigures();
        figures.forEach(figure -> {
            int pointsCount = figure.getPoints().size();
            if (pointsCount == 0) { return; }
            fillFigure(borderColor, figureColor, withoutTimeSleep);
        });
    }

    void addPointBtnDidTap(int x, int y, Color color) {
        model.addPoint(x, y);
        drawFigure(color);
    }

    void cancelBtnDidTap() {
        model.cancel();
        draw();
    }

    void cancelAllBtnDidTap() {
        model.cancelAll();
        draw();
    }

    void lockFigureBtnDidTap(Color color) {
        model.lockFigure();
        drawFigure(color);
    }

    void scale(boolean isPlus) {
        if (isPlus) { scale += 0.4; }
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

    private void onMouseClicked(MouseEvent event) {
        requestFocus();
        Point point = new Point(event.getX(), event.getY());
        addPointBtnDidTap((int) point.getX(), (int) point.getY(), controller.getBorderColor());
    }

    private void drawFigure(Color figureColor) {
        gc.setFill(figureColor);
        gc.setStroke(figureColor);
        ArrayList<Figure> figures = model.getFigures();
        figures.forEach(figure -> {
            int pointsCount = figure.getPoints().size();
            if (pointsCount > 0) {
                drawPoint(figure.getPoints().get(0));
            }
            if (pointsCount == 1) { return; }
            for (int i = 0; i < pointsCount - 1; i++) {
                Point startPoint = figure.getPoints().get(i);
                Point endPoint = figure.getPoints().get(i + 1);
                drawPoint(startPoint);
                drawPoint(endPoint);
                drawLine(startPoint, endPoint);
            }
        });
    }

    private void fillFigure(Color borderColor, Color figureColor, boolean withoutTimeSleep) {
        if (withoutTimeSleep) {

        } else {
            AtomicInteger i = new AtomicInteger();
            Timeline timeleine = new Timeline(new KeyFrame(Duration.millis(2500), (ActionEvent event) -> {

                i.getAndIncrement();
            }));
            timeleine.setCycleCount(0);
            timeleine.play();
        }
    }

    private void drawPoint(Point point) {
        double x = point.getX();
        double y = point.getY();
        gc.fillOval(x - 1, y - 1, 2, 2);
    }

    private void drawLine(Point startPoint, Point endPoint) {
        gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    private void drawPixel(int x, int y, Color color, double border) {
        Point point = new Point(x, y);
        int newX = (int) point.getX();
        int newY = (int) point.getY();
        Color[][] pixels = model.getPixels();
        if (pixels[newX][newY] == color) {
            pixels[newX][newY] = Color.WHITE;
            pixelWriter.setColor(newX, newY, Color.WHITE);
        } else {
            pixels[newX][newY] = color;
            pixelWriter.setColor(newX, newY, color);
        }
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
}

