package com.example.lab11;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

class ResizableCanvas extends Canvas {
    private final GraphicsContext gc = getGraphicsContext2D();
    private final PixelWriter pixelWriter = gc.getPixelWriter();
    private double oldWidth = getWidth();
    private double oldHeight = getHeight();
    private final Model model = new Model();
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

        drawFigure(model.getClipper(), Color.BLACK);
        drawFigure(model.getFigure(), Color.BLACK);
        if (model.isClipBtnDidTap()) {
            clipBtnDidTap();
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

    void cancelBtnDidTap() {
        model.cancel();
        draw();
    }

    void cancelAllBtnDidTap() {
        model.cancelAll();
        gc.setStroke(Color.BLACK);
        draw();
    }

    public void clipperBtnDidTap() {
        model.setAction(Action.CLIPPER);
    }

    public void figureBtnDidTap() {
        model.setAction(Action.FIGURE);
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

    public void addPointToClipperBtnDidTap(int x, int y) {
        model.setAction(Action.CLIPPER);
        model.addPointToClipper(new Point(x, y));
        drawFigure(model.getClipper(), Color.BLACK);
    }

    public void addPointToFigureBtnDidTap(double x, double y) {
        model.setAction(Action.FIGURE);
        model.addPointToFigure(new Point(x, y));
        Point point = translatePointFromIdeal(new Point(x, y));
        drawPoint((int) point.getX(), (int) point.getY(), Color.BLACK);
        drawFigure(model.getFigure(), Color.BLACK);
    }

    public void clipBtnDidTap() {
        model.setClipBtnDidTap(true);
        clip(model.getFigure(), model.getClipper());
    }

    public void lockClipperBtnDidTap() {
        model.lock(model.getClipper());
        drawFigure(model.getClipper(), Color.BLACK);
    }

    public void lockFigureBtnDidTap() {
        model.lock(model.getFigure());
        drawFigure(model.getFigure(), Color.BLACK);
    }

    private void onMouseClicked(MouseEvent mouseEvent) {
        if (model.getAction() == null) { return; }
        switch (model.getAction()) {
            case FIGURE -> {
                Point ideal = translatePointFromReal(new Point(mouseEvent.getX(), mouseEvent.getY()));
                addPointToFigureBtnDidTap(ideal.getX(), ideal.getY());
            }
            case CLIPPER -> {
                Point ideal = translatePointFromReal(new Point(mouseEvent.getX(), mouseEvent.getY()));
                model.addPointToClipper(ideal);
                drawFigure(model.getClipper(), Color.BLACK);
            }
        }
    }

    private void drawFigure(ArrayList<Point> clipper, Color color) {
        int pointsCount = clipper.size();
        gc.setStroke(color);
        for (int i = 0; i < pointsCount - 1; i++) {
            Point start = translatePointFromIdeal(clipper.get(i));
            Point end = translatePointFromIdeal(clipper.get(i + 1));
            gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
        }
        gc.setStroke(Color.BLACK);
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
        return new Point((int) newX, (int) newY);
    }

    Point translatePointFromReal(Point point) {
        double newX = point.getX() - getWidth() / 2;
        double newY = point.getY() * (-1) + getHeight() / 2;
        newX = newX - model.getTranslateCoords().getX();
        newY = newY - model.getTranslateCoords().getY();
        return new Point((int) newX, (int) newY);
    }

    // сам алгоритм
    private void clip(ArrayList<Point> figure, ArrayList<Point> clipper) {

    }

//    private double scalarMult(Vector a, Vector b) {
//        return a.getA() * b.getA() + a.getB() * b.getB();
//    }

//    private double vectMult(Vector v1, Vector v2) {
//        return v1.vectMult(v2);
//    }
}
