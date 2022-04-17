package com.example.lab5;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
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
        for (int i = 0; i < pointsCount - 1; i++) {
            Line line = new Line(figure.get(i), figure.get(i + 1));
            if (line.isHorizontal()) { continue; }
            if ((line.getStart().getX() < border || Math.abs(line.getStart().getX() - border) < 1e-6)  &&
                    (line.getEnd().getX() <= border || Math.abs(line.getEnd().getX() - border) < 1e-6)) {
                fillLeft(line, border, color, withoutTimeSleep);
            } else if ((line.getStart().getX() > border || Math.abs(line.getStart().getX() - border) < 1e-6) &&
                    (line.getEnd().getX() > border || Math.abs(line.getEnd().getX() - border) < 1e-6)) {
                fillRight(line, border, color, withoutTimeSleep);
            } else {
                fillMiddle(line, border, color, withoutTimeSleep);
            }
        }
    }

    private double border(ArrayList<Point> figure) {
        AtomicReference<Double> min = new AtomicReference<>(Double.MAX_VALUE);
        AtomicReference<Double> max = new AtomicReference<>(Double.MIN_VALUE);
        figure.forEach(point -> {
            if (point.getX() > max.get()) {
                max.set(point.getX());
            }
            if (point.getX() < min.get()) {
                min.set(point.getX());
            }
        });
        return ((min.get() + max.get()) / 2);
    }

    private void fillLeft(Line line, double border, Color color, boolean withoutTimeSleep) {
        double startY, endY;
        if (line.getStart().getY() < line.getEnd().getY()) {
            startY = line.getStart().getY();
            endY = line.getEnd().getY();
        } else {
            endY = line.getStart().getY();
            startY = line.getEnd().getY();
        }
        for (double y = startY; y <= endY; y++) {
            double startX = line.getX(y);
            for (double x = startX; x <= border; x++) {
                drawPixel(x, y, color, border);
            }
            if (!withoutTimeSleep) {

            }
        }
    }

    private void fillRight(Line line, double border, Color color, boolean withoutTimeSleep) {
        double startY, endY;
        if (line.getStart().getY() < line.getEnd().getY()) {
            startY = line.getStart().getY();
            endY = line.getEnd().getY();
        } else {
            endY = line.getStart().getY();
            startY = line.getEnd().getY();
        }
        for (double y = startY; y <= endY; y++) {
            double startX = line.getX(y);
            for (double x = startX; x > border; x--) {
                drawPixel(x, y, color, border);
            }
            if (!withoutTimeSleep) {

            }
        }
    }

    private void fillMiddle(Line line, double border, Color color, boolean withoutTimeSleep) {
        double yIntersection = line.getY(border);
        Point leftPoint = line.getStart().getX() < border ? line.getStart() : line.getEnd();
        Point rightPoint = line.getEnd().getX() > border ? line.getEnd() : line.getStart();
        Line left = new Line(new Point(border, yIntersection), leftPoint);
        Line right = new Line(new Point(border, yIntersection), rightPoint);
        fillLeft(left, border, color, withoutTimeSleep);
        fillRight(right, border, color, withoutTimeSleep);
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

    private void drawPixel(double x, double y, Color color, double border) {
        Point realPoint = translatePointFromIdeal(new Point(x, y));
        int newX = (int)realPoint.getX();
        int newY = (int)realPoint.getY();
        Color[][] pixels = model.getPixels();
        if (pixels[newX][newY] == color && newX != (int) border) {
//            gc.setStroke(Color.WHITE);
//            gc.strokeLine(newX, newY, newX, newY);
            pixelWriter.setColor(newX, newY, Color.WHITE);
        } else {
            pixels[newX][newY] = color;
//            gc.setStroke(color);
//            gc.strokeLine(newX, newY, newX, newY);
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
