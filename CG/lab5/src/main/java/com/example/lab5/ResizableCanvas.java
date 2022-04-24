package com.example.lab5;

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
import java.util.concurrent.atomic.AtomicReference;

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
            Point ideal = translatePointFromReal(new Point(mouseEvent.getX(), mouseEvent.getY()));
            controller.setCurrentMousePosition(ideal.getX(), ideal.getY());
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

//        drawAxes();
        fillBtnDidTap(figureColor, true);

        oldWidth = width;
        oldHeight = height;
    }

    @Override
    public boolean isResizable() { return true; }

    @Override
    public double prefWidth(double height) { return getWidth(); }

    @Override
    public double prefHeight(double width) { return getHeight(); }

    void fillBtnDidTap(Color color, boolean withoutTimeSleep) {
        figureColor = color;
        ArrayList<Point> figure = model.getFigure();
        int pointsCount = figure.size();
        if (pointsCount == 0) { return; }
        if (!figure.get(0).isEqual(figure.get(pointsCount - 1))) {
            figure.add(new Point(figure.get(0).getX(), figure.get(0).getY()));
        }
        drawFigure(color);
        fillFigure(figure, color, withoutTimeSleep);
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

    private void onMouseClicked(MouseEvent event) {
        requestFocus();
        Point point = translatePointFromReal(new Point(event.getX(), event.getY()));
        addPointBtnDidTap((int) point.getX(), (int) point.getY(), figureColor);
    }

    private void drawAxes() {
//        gc.setLineWidth(0.4);
        Point screenCenter = translatePointFromIdeal(new Point(0, 0));
        gc.strokeLine(0, screenCenter.getY(), getWidth(), screenCenter.getY());
        gc.strokeLine(screenCenter.getX(), 0, screenCenter.getX(), getHeight());
        gc.setLineWidth(1.0);
    }

    private void drawFigure(Color figureColor) {
        gc.setFill(figureColor);
        ArrayList<Point> figure = model.getFigure();
        int pointsCount = figure.size();
        if (pointsCount > 0) {
            drawPoint(figure.get(0));
        }
        if (pointsCount == 1) { return; }
        for (int i = 0; i < pointsCount - 1; i++) {
            Point startPoint = figure.get(i);
            Point endPoint = figure.get(i + 1);
            drawPoint(startPoint);
            drawPoint(endPoint);
            drawLine(startPoint, endPoint);
        }
    }

    private void fillFigure(ArrayList<Point> figure, Color color, boolean withoutTimeSleep) {
        int pointsCount = figure.size();
        if (pointsCount < 3) { return; }
        double border = border(figure);
        System.out.println(border);
        if (withoutTimeSleep) {
            for (int i = 0; i < pointsCount - 1; i++) {
                Line line = new Line(figure.get(i), figure.get(i + 1));
                if (!line.isHorizontal()) {
                    fillLeft(line, border, color);
                }
            }
        } else {
            AtomicInteger i = new AtomicInteger();
            Timeline timeleine = new Timeline(new KeyFrame(Duration.millis(2500), (ActionEvent event) -> {
                Line line = new Line(figure.get(i.get()), figure.get(i.get() + 1));
                if (!line.isHorizontal()) {
                    fillLeft(line, border, color);
                }
                i.getAndIncrement();
            }));
            timeleine.setCycleCount(pointsCount - 1);
            timeleine.play();
        }
    }

    private double border(ArrayList<Point> figure) {
        AtomicReference<Double> max = new AtomicReference<>(Double.MIN_VALUE);
        figure.forEach(point -> {
            if (point.getX() > max.get()) {
                max.set(point.getX());
            }
        });
        return max.get();
    }

    private void fillLeft(Line line, double border, Color color) {
        int x1 = (int) line.getStart().getX();
        int x2 = (int) line.getEnd().getX();
        int y1 = (int) line.getStart().getY();
        int y2 = (int) line.getEnd().getY();

        if (y1 > y2)
        {
            int tmp = y2;
            y2 = y1;
            y1 = tmp;
            tmp = x2;
            x2 = x1;
            x1 = tmp;
        }

        double dx = (x2 - x1) / (double)(y2 - y1);
        double xstart = x1;
        for (int y = y1; y < y2; y++)
        {
            for (int x = (int) Math.round(xstart); x <= border; x++)
            {
                drawPixel(x, y, color, border);
            }
            xstart += dx;
        }
    }

    private void drawPoint(Point point) {
        Point realPoint = translatePointFromIdeal(point);
        double x = realPoint.getX();
        double y = realPoint.getY();
        gc.fillOval(x - 1, y - 1, 2, 2);
    }

    private void drawLine(Point startPoint, Point endPoint) {
        Point realStartPoint = translatePointFromIdeal(startPoint);
        Point realEndPoint = translatePointFromIdeal(endPoint);
        gc.strokeLine(realStartPoint.getX(), realStartPoint.getY(), realEndPoint.getX(), realEndPoint.getY());
    }

    private void drawPixel(int x, int y, Color color, double border) {
        Point realPoint = translatePointFromIdeal(new Point(x, y));
        int newX = (int)realPoint.getX();
        int newY = (int)realPoint.getY();
        Color[][] pixels = model.getPixels();
        if (pixels[newX][newY] == color && newX != (int) border) {
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
