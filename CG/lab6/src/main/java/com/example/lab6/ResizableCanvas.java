package com.example.lab6;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Stack;

class ResizableCanvas extends Canvas {
    private final GraphicsContext gc = getGraphicsContext2D();
    private final PixelWriter pixelWriter = gc.getPixelWriter();
    private double oldWidth = getWidth();
    private double oldHeight = getHeight();
    private final Model model = new Model();
    private double scale = 1.0;
    private MainController controller;
    private boolean isFilling = false;
    private Point pixel = null;

    public ResizableCanvas(MainController controller) {
        this.controller = controller;
        setOnMouseMoved(mouseEvent -> {
            Point point = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());
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

        if (pixel != null) {
            model.translateFigure();
            pixel.translate(model.getTranslateCoords());
            drawFigure(controller.getBorderColor());
            fillFigure(controller.getBorderColor(), controller.getFigureColor(), controller.isWithoutTimeSleep(), pixel);
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

    void fillBtnDidTap() {
        isFilling = true;
        setCursor(Cursor.HAND);
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
        pixel = null;
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
        model.getTranslateCoords().clear();
    }

    private void onMouseClicked(MouseEvent event) {
        requestFocus();
        Point point = new Point((int) event.getX(), (int) event.getY());
        if (isFilling) {
            fillFigure(controller.getBorderColor(), controller.getFigureColor(), controller.isWithoutTimeSleep(), point);
            isFilling = false;
            pixel = point;
            setCursor(null);
            return;
        }
        addPointBtnDidTap(point.getX(), point.getY(), controller.getBorderColor());
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

    private void fillFigure(Color borderColor, Color figureColor, boolean withoutTimeSleep, Point point) {
//        WritableImage snap = gc.getCanvas().snapshot(null, null);
//        pixelReader = snap.getPixelReader();
        if (withoutTimeSleep) {
            fillWithoutTimeSleep(borderColor, figureColor, point);
        } else {
            fillWithTimeSleep(borderColor, figureColor, point);
        }
    }

    private void fillWithoutTimeSleep(Color borderColor, Color figureColor, Point point) {
        long start = System.currentTimeMillis();

        Stack<Point> stack = new Stack<>();
        stack.push(point);

        Color[][] pixels = model.getPixels();

        while (!stack.isEmpty()) {
            Point dot_z = stack.pop();

            int cur_x = dot_z.getX();
            int cur_y = dot_z.getY();

            Color got_color = pixels[cur_x][cur_y];
            while (!similar(got_color, borderColor)) {
                drawPixel(cur_x, cur_y, figureColor);
                cur_x -= 1;
                got_color = pixels[cur_x][cur_y];
            }
            int x_left = cur_x + 1;
//            picture.put(seed_color, (x_left, cur_y, dot_z[0] + 1, cur_y + 1))

            cur_x = dot_z.getX() + 1;
            got_color = pixels[cur_x][cur_y];
            while (!similar(got_color, borderColor)) {
                drawPixel(cur_x, cur_y, figureColor);
                cur_x += 1;
                got_color = pixels[cur_x][cur_y];
            }
            int x_right = cur_x - 1;
//            picture.put(seed_color, (dot_z[0], cur_y, x_right + 1, cur_y + 1))

            cur_x = x_left;
            cur_y += 1;

            boolean flag = false;
            while (cur_x <= x_right) {
                got_color = pixels[cur_x][cur_y];
                while (!similar(got_color, borderColor) && !similar(got_color,figureColor) && cur_x <= x_right) {
                    flag = true;
                    cur_x += 1;
                    got_color = pixels[cur_x][cur_y];
                }

                if (flag) {
                    if (cur_x == x_right && !similar(got_color, borderColor) && !similar(got_color, figureColor)) {
                        stack.push(new Point(cur_x, cur_y));
                    } else {
                        stack.push(new Point(cur_x - 1, cur_y));
                    }
                    flag = false;
                }

                int x_start = cur_x;
                while ((similar(got_color, borderColor) || similar(got_color, figureColor)) && cur_x < x_right) {
                    cur_x += 1;
                    got_color = pixels[cur_x][cur_y];
                }

                if (cur_x == x_start) {
                    cur_x += 1;
                }
            }

            cur_x = x_left;
            cur_y -= 2;

            flag = false;
            while (cur_x <= x_right) {
                got_color = pixels[cur_x][cur_y];
                while (!similar(got_color, borderColor) && !similar(got_color,figureColor) && cur_x <= x_right) {
                    flag = true;
                    cur_x += 1;
                    got_color = pixels[cur_x][cur_y];
                }

                if (flag) {
                    if (cur_x == x_right && !similar(got_color, borderColor) && !similar(got_color,figureColor)) {
                        stack.push(new Point(cur_x, cur_y));
                    } else{
                        stack.push(new Point(cur_x - 1, cur_y));
                    }
                    flag = false;
                }

                int x_start = cur_x;
                while ((similar(got_color, borderColor) || similar(got_color,figureColor)) && cur_x < x_right) {
                    cur_x += 1;
                    got_color = pixels[cur_x][cur_y];
                }

                if (cur_x == x_start) {
                    cur_x += 1;
                }
            }
        }

        long finish = System.currentTimeMillis();
        controller.setTime(finish - start);
    }

    private void fillWithTimeSleep(Color borderColor, Color figureColor, Point point) {
        Stack<Point> stack = new Stack<>();
        stack.push(point);

        Color[][] pixels = model.getPixels();

        Timeline timeleine = new Timeline(new KeyFrame(Duration.millis(200), (ActionEvent event) -> {
            if (!stack.isEmpty()) {
                Point dot_z = stack.pop();

                int cur_x = dot_z.getX();
                int cur_y = dot_z.getY();

                Color got_color = pixels[cur_x][cur_y];

                while (!similar(got_color, borderColor)) {
                    drawPixel(cur_x, cur_y, figureColor);
                    cur_x -= 1;
                    got_color = pixels[cur_x][cur_y];
                }

                int x_left = cur_x + 1;

                cur_x = dot_z.getX() + 1;
                got_color = pixels[cur_x][cur_y];
                while (!similar(got_color, borderColor)) {
                    drawPixel(cur_x, cur_y, figureColor);
                    cur_x += 1;
                    got_color = pixels[cur_x][cur_y];
                }
                int x_right = cur_x - 1;

//            gc.strokeLine(x_left, cur_y, x_right, cur_y);

                cur_x = x_left;
                cur_y += 1;

                boolean flag = false;
                while (cur_x <= x_right) {
                    got_color = pixels[cur_x][cur_y];
                    while (!similar(got_color, borderColor) && !similar(got_color,figureColor) && cur_x <= x_right) {
                        flag = true;
                        cur_x += 1;
                        got_color = pixels[cur_x][cur_y];
                    }

                    if (flag) {
                        if (cur_x == x_right && !similar(got_color, borderColor) && !similar(got_color, figureColor)) {
                            stack.push(new Point(cur_x, cur_y));
                        } else {
                            stack.push(new Point(cur_x - 1, cur_y));
                        }
                        flag = false;
                    }

                    int x_start = cur_x;
                    while ((similar(got_color, borderColor) || similar(got_color, figureColor)) && cur_x < x_right) {
                        cur_x += 1;
                        got_color = pixels[cur_x][cur_y];
                    }

                    if (cur_x == x_start) {
                        cur_x += 1;
                    }
                }

                cur_x = x_left;
                cur_y -= 2;

                flag = false;
                while (cur_x <= x_right) {
                    got_color = pixels[cur_x][cur_y];
                    while (!similar(got_color, borderColor) && !similar(got_color,figureColor) && cur_x <= x_right) {
                        flag = true;
                        cur_x += 1;
                        got_color = pixels[cur_x][cur_y];
                    }

                    if (flag) {
                        if (cur_x == x_right && !similar(got_color, borderColor) && !similar(got_color,figureColor)) {
                            stack.push(new Point(cur_x, cur_y));
                        } else{
                            stack.push(new Point(cur_x - 1, cur_y));
                        }
                        flag = false;
                    }

                    int x_start = cur_x;
                    while ((similar(got_color, borderColor) || similar(got_color,figureColor)) && cur_x < x_right) {
                        cur_x += 1;
                        got_color = pixels[cur_x][cur_y];
                    }

                    if (cur_x == x_start) {
                        cur_x += 1;
                    }
                }
            }
        }));
        timeleine.setCycleCount(1000);
        timeleine.play();
    }

    private void drawPoint(Point point) {
        double x = point.getX();
        double y = point.getY();
        gc.fillOval(x - 1, y - 1, 2, 2);
    }

    private void drawLine(Point startPoint, Point endPoint) {
        CDA(startPoint, endPoint, controller.getBorderColor());
    }

    private void CDA(Point start, Point end, Color color) {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        double L = Math.max(Math.abs(dx), Math.abs(dy));

        double sx = (end.getX() - start.getX()) / L;
        double sy = (end.getY() - start.getY()) / L;

        double x = start.getX();
        double y = start.getY();
        int i = 1, steps = 1;
        while (i <= L + 1) {
            drawPixel((int) Math.round(x), (int) Math.round(y), color);
            x += sx;
            y += sy;
            i += 1;
        }
    }

    private void drawPixel(int x, int y, Color color) {
//        if (x < 0 || y < 0) { return; }
        Point point = new Point(x, y);
        int newX = point.getX();
        int newY = point.getY();
        Color[][] pixels = model.getPixels();
        pixels[newX][newY] = color;
        pixelWriter.setColor(newX, newY, color);
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

    private boolean similar(Color c1, Color c2){
        double distance = (c1.getRed() - c2.getRed()) * (c1.getRed() - c2.getRed()) +
                (c1.getGreen() - c2.getGreen()) * (c1.getGreen() - c2.getGreen()) +
                (c1.getBlue() - c2.getBlue()) * (c1.getBlue() - c2.getBlue());
        if (distance < 1e-6) {
            return true;
        } else {
            return false;
        }
    }
}

