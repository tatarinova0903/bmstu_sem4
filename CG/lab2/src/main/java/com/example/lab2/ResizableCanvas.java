package com.example.lab2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

class ResizableCanvas extends Canvas {
    private final GraphicsContext gc = getGraphicsContext2D();
    private double oldWidth = getWidth();
    private double oldHeight = getHeight();
    private final Model model = new Model();

    public ResizableCanvas(MainController controller) {
        setOnMouseMoved(mouseEvent -> {
            Point ideal = translatePointFromReal(new Point(mouseEvent.getX(), mouseEvent.getY()));
            controller.setCurrentMousePosition(ideal.getX(), ideal.getY());
        });
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

        ArrayList<Point> points = model.getPoints();
        ArrayList<Integer> road = model.getHorse();
        for (int i = 0; i < road.size() - 1; i++) {
            Line line = new Line(points.get(road.get(i)), points.get(road.get(i + 1)));
            drawLine(line);
        }

        oldWidth = width;
        oldHeight = height;
    }

    @Override
    public boolean isResizable() { return true; }

    @Override
    public double prefWidth(double height) { return getWidth(); }

    @Override
    public double prefHeight(double width) { return getHeight(); }

    void moveBtnDidTap(double dx, double dy) {
        model.move(dx, dy);
        draw();
    }

    void rotateBtnDidTap(double x, double y, double degree) {
        model.rotate(x, y, degree);
        draw();
    }

    void scaleBtnDidTap(double dx, double dy) {
        model.scale(dx, dy);
        draw();
    }

    private void drawLine(Line line) {
        Point start = translatePointFromIdeal(line.getStart());
        Point end = translatePointFromIdeal(line.getEnd());
        gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

    private Point translatePointFromIdeal(Point point) {
        return new Point(point.getX() + getWidth() / 2, (point.getY() - getHeight() / 2) * (-1));
    }

    private Point translatePointFromReal(Point point) {
        return new Point(point.getX() - getWidth() / 2, (point.getY()) * (-1) + getHeight() / 2);
    }
}