package com.example.lab3;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.lang.Math;
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

    void drawPuchokBtnDidTap(int length, double radiansStep, AlgoritmType algoritmType, Color segmentColor, Color backgroundColor) {
        double curDegree = 0.0;
        while (curDegree < 2 * Math.PI) {
            Point startPoint = new Point(0.0, 0.0);
            Point endPoint = getPoint(length, curDegree);
            Segment segment = new Segment(startPoint, endPoint, segmentColor, algoritmType);
            model.addSegment(segment);
            changeBackgroundColor(backgroundColor);
            curDegree += radiansStep;
        }
        draw();
    }

    void drawChartBtnDidTap(int length, double angle, AlgoritmType algoritm) {
        ArrayList<Integer> angles = new ArrayList<>();
        ArrayList<Integer> steps = new ArrayList<>();
        int curAngle = 0;
        while (curAngle < 90) {
            Point startPoint = new Point(0.0, 0.0);
            Point endPoint = getPoint(length, curAngle);
            angles.add(curAngle);
            steps.add(stepCount(translatePointFromIdeal(startPoint), translatePointFromIdeal(endPoint), algoritm));
            curAngle += angle;
        }
        showChart(algoritm, angles, steps);
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
        if (isPlus) { scale += 0.1; }
        else if (scale > 1) { scale -= 0.1; }
        this.setScaleX(scale);
        this.setScaleY(scale);
        double newWidth = getWidth() * 3;
        double newX = newXForScale(scale);
        double newHeight = getHeight() * 3;
        double newY = newYForScale(scale);
        Rectangle rect = new Rectangle(newX, newY, newWidth, newHeight);
        this.setClip(rect);
//        if (isPlus) { model.incrementCurrScale(); }
//        else { model.decrementCurrScale(); }
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
        switch (segment.getAlgoritmType()) {
            case STANDARD -> {
                gc.strokeLine(startRealPoint.getX(), startRealPoint.getY(), endRealPoint.getX(), endRealPoint.getY());
            }
            case CDA -> {
                CDA(startRealPoint, endRealPoint, true, segment.getColor());
            }
            case BREZ_INT -> {
                BREZ_INT(startRealPoint, endRealPoint, true, segment.getColor());
            }
            case BREZ_FLOAT -> {
                BREZ_FLOAT(startRealPoint, endRealPoint, true, segment.getColor());
            }
            case BREZ_SMOOTH -> {
                BREZ_SMOOTH(startRealPoint, endRealPoint, true, segment.getColor());
            }
            case VU -> {
                VU(startRealPoint, endRealPoint, true, segment.getColor());
            }
        }
    }

    private int CDA(Point start, Point end, boolean draw, Color color) {
        if (isSegmentDegenerate(start, end, draw)) {
            return -1;
        }
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        double L = Math.max(Math.abs(dx), Math.abs(dy));

        double sx = (end.getX() - start.getX()) / L;
        double sy = (end.getY() - start.getY()) / L;

        double x = start.getX();
        double y = start.getY();
        int i = 1, steps = 1;
        while (i <= L + 1) {
            if (draw) {
                drawPoint(Math.round(x), Math.round(y), color, INTENSITY);
            }
            if (!draw && i <= L) {
                if (!((Math.round(x + sx) == Math.round(x) && Math.round(y + sy) != Math.round(y)) ||
                        (Math.round(x + sx) != Math.round(x) && Math.round(y + sy) == Math.round(y)))) {
                    steps += 1;
                }
            }
            x += sx;
            y += sy;
            i += 1;
        }
        if (!draw) {
            return steps;
        } else {
            return -1;
        }
    }

    private int BREZ_INT(Point start, Point end, boolean draw, Color color) {
        if (isSegmentDegenerate(start, end, draw)) {
            return -1;
        }
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();

        int sx = (int) Math.signum(dx);
        int sy = (int) Math.signum(dy);

        dx = Math.abs(dx);
        dy = Math.abs(dy);

        int change = 0;
        if (dy >= dx) {
            double temp = dx;
            dx = dy;
            dy = temp;
            change = 1;
        }

        double m = 2 * dy;
        double m1 = 2 * dx;
        double e = m - dx;

        int x = (int) Math.round(start.getX());
        int y = (int) Math.round(start.getY());

        int i = 1, steps = 1, xPrev = x, yPrev = y;
        while (i <= dx + 1) {
            if (draw) {
                drawPoint(x, y, color, INTENSITY);
            }
            if (e >= 0) {
                if (change == 1) {
                    x += sx;
                } else {
                    y += sy;
                }
                e -= m1;
            }
            if (e <= 0) {
                if (change == 1) {
                    y += sy;
                } else {
                    x += sx;
                }
                e += m;
            }
            i += 1;
            if (!draw) {
                if (!((xPrev == x && yPrev != y) || (xPrev != x && yPrev == y))) {
                    steps += 1;
                }
                xPrev = x;
                yPrev = y;
            }
        }
        if (!draw) {
            return steps;
        } else {
            return -1;
        }
    }

    private int BREZ_FLOAT(Point p_start, Point p_end, boolean draw, Color color) {
        if (isSegmentDegenerate(p_start, p_end, draw)) {
            return -1;
        }
        double dx = p_end.getX() - p_start.getX();
        double dy = p_end.getY() - p_start.getY();

        int sx = (int) Math.signum(dx);
        int sy = (int) Math.signum(dy);

        dx = Math.abs(dx);
        dy = Math.abs(dy);

        int change = 0;
        if (dy >= dx) {
            double temp = dx;
            dx = dy;
            dy = temp;
            change = 1;
        }

        double m = dy / dx;
        double e = m - 0.5;

        int x = (int) Math.round(p_start.getX());
        int y = (int) Math.round(p_start.getY());

        int i = 1, steps = 1, xPrev = x, yPrev = y;
        while (i <= dx + 10) {
            if (draw) {
                drawPoint(x, y, color, INTENSITY);
            }
            if (e >= 0) {
                if (change == 1) {
                    x += sx;
                }
                else {
                    y += sy;
                }
                e -= 1;
            }
            if (e <= 0) {
                if (change == 1) {
                    y += sy;
                } else {
                    x += sx;
                }
                e += m;
            }
            i += 1;
            if (!draw) {
                if (!((xPrev == x && yPrev != y) || (xPrev != x && yPrev == y))) {
                    steps += 1;
                }
                xPrev = x;
                yPrev = y;
            }
        }
        if (!draw) {
            return steps;
        }
        return -1;
    }

    private int BREZ_SMOOTH(Point start, Point end, boolean draw, Color color) {
        if (isSegmentDegenerate(start, end, draw)) {
            return -1;
        }

        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();

        int sx = (int) Math.signum(dx);
        int sy = (int) Math.signum(dy);

        dx = Math.abs(dx);
        dy = Math.abs(dy);

        int change = 0;
        if (dy >= dx) {
            double temp = dx;
            dx = dy;
            dy = temp;
            change = 1;
        }

        double m = dy / dx;
        double e = INTENSITY / 2.0;

        int x = (int) Math.round(start.getX());
        int y = (int) Math.round(start.getY());

        m *= INTENSITY;
        double W = INTENSITY - m;

        if (draw) {
            drawPoint(x, y, color, Math.round(e));
        }

        int i = 1, steps = 1, xPrev = x, yPrev = y;
        while (i <= dx) {
            if (e < W) {
                if (change == 0) {
                    x += sx;
                } else {
                    y += sy;
                }
                e += m;
            } else {
                x += sx;
                y += sy;
                e -= W;
            }
            if (draw) {
                drawPoint(x, y, color, Math.round(e));
            } else {
                if (!((xPrev == x && yPrev != y) || (xPrev != x && yPrev == y))) {
                    steps += 1;
                }
                xPrev = x;
                yPrev = y;
            }
            i += 1;
        }
        if (!draw) {
            return steps;
        }
        return -1;
    }

    private int VU(Point start, Point end, boolean draw, Color color) {
        if (isSegmentDegenerate(start, end, draw)) {
            return -1;
        }

        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();

        double m = 1;
        int d = 1, steps = 1;
        if (Math.abs(dy) > Math.abs(dx)) {
            if (dy != 0) {
                m = dx / dy;
            }
            double m1 = m;
            if (start.getY() > end.getY()) {
                m1 *= -1;
                d *= -1;
            }
            for (int y = (int) Math.round(start.getY()); d < 0 ? (y > Math.round(end.getY()) + 1) : (y < Math.round(end.getY()) + 1); y += d) {
                double d1 = start.getX() - Math.floor(start.getX());
                double d2 = 1 - d1;
                if (draw) {
                    drawPoint(start.getX(), y, color, Math.round(Math.abs(d2) * INTENSITY));
                    drawPoint(start.getX() + 1, y, color, Math.round(Math.abs(d1) * INTENSITY));
                }
                if (!draw && y < Math.round(end.getY())) {
                    if ((int) start.getX() != (int)(start.getX() + m)) {
                        steps += 1;
                    }
                }
                start.setX(start.getX() + m1);
            }
        }
        else {
            if (dx != 0) {
                m = dy / dx;
            }
            double m1 = m;
            if (start.getX() > end.getX()) {
                m1 *= -1;
                d *= -1;
            }
            for (int x = (int) Math.round(start.getX()); d < 0 ? (x > Math.round(end.getX()) + 1) : (x < Math.round(end.getX()) + 1); x += d) {
                double d1 = start.getY() - Math.floor(start.getY());
                double d2 = 1 - d1;
                if (draw) {
                    drawPoint(x, start.getY(), color, Math.round(Math.abs(d2) * INTENSITY));
                    drawPoint(x, start.getY() + 1, color, Math.round(Math.abs(d1) * INTENSITY));
                }
                if (!draw && x < Math.round(end.getX())) {
                    if ((int) start.getY() != (int) (start.getY() + m)) {
                        steps += 1;
                    }
                }
                start.setY(start.getY() + m1);
            }
        }
        if (!draw) {
            return steps;
        }
        return -1;
    }

    private void drawPoint(double x, double y, Color color, double intensity) {
        Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), intensity / 255);
        pixelWriter.setColor((int)x, (int)y, newColor);
    }

    private boolean isSegmentDegenerate(Point start, Point end, boolean draw) {
        if (start.getX() == end.getX() && start.getY() == end.getY())
        {
            if (draw)
            {
                controller.showInfoAlert("Отрезок вырожден");
            }
            return true;
        }
        return false;
    }

    private Point getPoint(int length, double angle) {
        return new Point(Math.cos(angle) * length, Math.sin(angle) * length);
    }

    private void showChart(AlgoritmType algoritm, ArrayList<Integer> angles, ArrayList<Integer> steps) {
        ChartController chartControllerPane = new ChartController(algoritm, angles, steps);
        Scene scene = new Scene(chartControllerPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(600);
        stage.show();
    }

    private int stepCount(Point start, Point end, AlgoritmType algoritm) {
        switch (algoritm) {
            case CDA -> {
                return CDA(start, end, false, backgroundColor);
            }
            case BREZ_INT -> {
                return BREZ_INT(start, end, false, backgroundColor);
            }
            case BREZ_FLOAT -> {
                return BREZ_FLOAT(start, end, false, backgroundColor);
            }
            case BREZ_SMOOTH -> {
                return BREZ_SMOOTH(start, end, false, backgroundColor);
            }
            case VU -> {
                return VU(start, end, false, backgroundColor);
            }
        }
        return 0;
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
