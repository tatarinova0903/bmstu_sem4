package com.example.lab4;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
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
        model.getCircles().forEach(circle -> {
            drawOval(circle);
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

    void drawBtnDidTap(Point center, double xAxis, double yAxis, AlgoritmType algoritmType, Color circleColor, Color backgroundColor) {
        Oval oval = new Oval(center, xAxis, yAxis, circleColor, algoritmType);
        model.addCircle(oval);
        changeBackgroundColor(backgroundColor);
        draw();
    }

    void drawSpectrBtnDidTap(int xAxisLen, int yAxisLen, double step, int count, AlgoritmType algoritmType, Color circleColor, Color backgroundColor) {
        for (int i = 0; i < count; i++) {
            Oval oval = new Oval(new Point(0.0, 0.0),
                    xAxisLen + i * step, yAxisLen + i * step,
                    circleColor, algoritmType
                    );
            model.addCircle(oval);
        }
        changeBackgroundColor(backgroundColor);
        draw();
    }

    void drawChartBtnDidTap(int xAxisLen, int yAxisLen, double step, int count) {
        Algoritm algoritms = new Algoritm();
        ArrayList<ChartData> data = new ArrayList<>();
        int checkAmount = 200;
        for (int j = 0; j < checkAmount; j++) {
            int finalJ = j;
            algoritms.getAlgoritms().forEach(algoritmSTR -> {
                if (algoritms.getAlgoritm(algoritmSTR).equals(AlgoritmType.STANDARD)) {
                    return;
                }
                ArrayList<Double> axis = new ArrayList<>();
                ArrayList<Long> time = new ArrayList<>();
                for (int k = 0; k < count; k++) {
                    time.add(0L);
                }
                for (int i = 0; i < count; i++) {
                    double x = xAxisLen + i * step;
                    double y = yAxisLen + i * step;
                    long start = System.nanoTime();
                    switch (algoritms.getAlgoritm(algoritmSTR)) {
                        case PARAMETER -> PARAMETER(0.0, 0.0, x, y, Color.WHITE, false);
                        case CANONICAL -> CANONICAL(0.0, 0.0, x, y, Color.WHITE, false);
                        case BREZ -> BREZ(0.0, 0.0, x, y, Color.WHITE, false);
                        case MIDDLE_POINT -> MIDDLE_POINT(0.0, 0.0, x, y, Color.WHITE, false);
                    }
                    long finish = System.nanoTime();
                    axis.add(x + y);
                    time.set(i, time.get(i) + finish - start);
                }
                for (int k = 0; k < count; k++) {
                    time.set(k, time.get(k) / checkAmount);
                }
                data.add(new ChartData(algoritms.getAlgoritm(algoritmSTR), axis, time));
            });
        }
        showChart(data);
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

    private void drawOval(Oval oval) {
        gc.setStroke(Color.BLACK);
        Point centerRealPoint = translatePointFromIdeal(oval.getCenter());
        switch (oval.getAlgoritmType()) {
            case STANDARD -> {
                gc.strokeOval(centerRealPoint.getX() - oval.getxAxis(), centerRealPoint.getY() - oval.getyAxis(),
                        oval.getxAxis() * 2, oval.getyAxis() * 2);
            }
            case CANONICAL -> {
                CANONICAL(centerRealPoint.getX(), centerRealPoint.getY(), oval.getxAxis(), oval.getyAxis(), oval.getColor(), true);
            }
            case PARAMETER -> {
                PARAMETER(centerRealPoint.getX(), centerRealPoint.getY(), oval.getxAxis(), oval.getyAxis(), oval.getColor(), true);
            }
            case BREZ -> {
                BREZ(centerRealPoint.getX(), centerRealPoint.getY(), oval.getxAxis(), oval.getyAxis(), oval.getColor(), true);
            }
            case MIDDLE_POINT -> {
                MIDDLE_POINT(centerRealPoint.getX(), centerRealPoint.getY(), oval.getxAxis(), oval.getyAxis(), oval.getColor(), true);
            }
        }
    }

    private void CANONICAL(double x_center, double y_center, double a, double b, Color color, boolean draw) {
        ArrayList<Point> points = new ArrayList<>();
        double a_pow = a * a;
        double b_pow = b * b;
        //Производная при y`=-1, является границей для оптимального рисования
        double limit = Math.round(a_pow / Math.sqrt(a_pow + b_pow));
        for (double x = 0; x < limit + 1; x++) {
            double y = Math.round(Math.sqrt(1 - x * x / a_pow) * b);
            points.add(new Point(x_center + x, y_center + y));
            points.add(new Point(x_center + x, y_center - y));
            points.add(new Point(x_center - x, y_center + y));
            points.add(new Point(x_center - x, y_center - y));
        }
        limit = Math.round(b_pow / Math.sqrt(a_pow + b_pow));
        for (double y = limit; y > -1; y--) {
            double x = Math.round(Math.sqrt(1 - y * y / b_pow) * a);
            points.add(new Point(x_center + x, y_center + y));
            points.add(new Point(x_center + x, y_center - y));
            points.add(new Point(x_center - x, y_center + y));
            points.add(new Point(x_center - x, y_center - y));
        }

        if (draw) {
            points.forEach(point -> {
                drawPoint(point.getX(), point.getY(), color);
            });
        }
    }

    private void PARAMETER(double x_center, double y_center, double a, double b, Color color, boolean draw) {
        ArrayList<Point> points = new ArrayList<>();
        double step;
        if (a > b) {
             step = 1 / a;
        }
        else {
            step = 1 / b;
        }
        for (double teta = 0; teta < Math.PI / 2 + step; teta += step) {
            double x = Math.round(a * Math.cos(teta));
            double y = Math.round(b * Math.sin(teta));
            points.add(new Point(x_center + x, y_center + y));
            points.add(new Point(x_center + x, y_center - y));
            points.add(new Point(x_center - x, y_center + y));
            points.add(new Point(x_center - x, y_center - y));
        }
        if (draw) {
            points.forEach(point -> {
                drawPoint(point.getX(), point.getY(), color);
            });
        }
    }

    private void BREZ(double x_center, double y_center, double a, double b, Color color, boolean draw) {
        // f(x,y)=x^2*b^2+a^2y^2-a^2*b^2=0
        ArrayList<Point> points = new ArrayList<>();
        double x = 0;
        double y = b;
        double a_pow = a * a;
        double b_pow = b * b;
        // error = b^2 * (x + 1)^2 + a^2 * (y - 1)^2-a^2 * b^2
        double d =  a_pow + b_pow - a_pow * 2 * y;
        while (y >= 0) {
            points.add(new Point(x_center + x, y_center + y));
            points.add(new Point(x_center + x, y_center - y));
            points.add(new Point(x_center - x, y_center + y));
            points.add(new Point(x_center - x, y_center - y));
            if (d < 0) { //точка внутри окружности
                double d1 = 2 * d + a_pow * (2 * y - 1);
                if (d1 > 0) { //диагональ
                    x += 1;
                    y -= 1;
                    d += b_pow * 2 * x + b_pow + a_pow - a_pow * y * 2;
                } else {  //горизонталь
                    x += 1;
                    d += b_pow * 2 * x + b_pow;
                }
            }
            else if (d == 0) {  //точка лежит на окружности (диагональ)
                x += 1;
                y -= 1;
                d += b_pow * 2 * x + b_pow + a_pow - a_pow * y * 2;
            }
            else {  //точка вне окружности
                double d1 = 2 * d + b_pow * (-2 * x - 1);
                if (d1< 0) { //диагональ
                    x += 1;
                    y -= 1;
                    d += b_pow * 2 * x + b_pow + a_pow - a_pow * y * 2;
                } else { //вертикаль
                    y -= 1;
                    d += a_pow - a_pow * 2 * y;
                }
            }
        }
        if (draw) {
            points.forEach(point -> {
                drawPoint(point.getX(), point.getY(), color);
            });
        }
    }

    private void MIDDLE_POINT(double x_center, double y_center, double a, double b, Color color, boolean draw) {
        ArrayList<Point> points = new ArrayList<>();

        double pow_a = a * a;
        double pow_b = b * b;

        double limit = Math.round(a / Math.sqrt(1 + pow_b / pow_a)); // производная для ограничения

        double x = 0;
        double y = b;
        points.add(new Point(x + x_center, y + y_center));
        double func = pow_b - Math.round(pow_a * (b - 1 / 4));

        // 1 участок
        while (x <= limit) {
            if (func > 0) { //диагональ
                y -= 1;
                func -= pow_a * y * 2;
            }

            x += 1;
            func += pow_b * (x + x + 1);
            points.add(new Point(x + x_center, y + y_center));
        }

        limit = Math.round(b / Math.sqrt(1 + pow_a / pow_b)); // производная для ограничения
        x = a;
        y = 0;
        points.add(new Point(x + x_center, y + y_center));

        func = pow_a - Math.round(pow_b * (x - 1 / 4));

        // 2 участок
        while (y <= limit) {
            if (func > 0) { //диагональ
                x -= 1;
                func -= 2 * pow_b * x;
            }

            y += 1;
            func += pow_a * (y + y + 1);
            points.add(new Point(x + x_center, y + y_center));
        }

        reflectPointsX(points, y_center);
        reflectPointsY(points, x_center);
        if (draw) {
            points.forEach(point -> {
                drawPoint(point.getX(), point.getY(), color);
            });
        }
    }

        private void drawPoint(double x, double y, Color color) {
        pixelWriter.setColor((int)x, (int)y, color);
    }

    private void showChart(ArrayList<ChartData> data) {
        ChartController chartControllerPane = new ChartController(data);
        Scene scene = new Scene(chartControllerPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(600);
        stage.show();
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

    private void reflectPointsY(ArrayList<Point> pointsArray, double xCenter) {
        double prevLen = pointsArray.size();
        for (int i = 0; i < prevLen; i++) {
            pointsArray.add(new Point(-(pointsArray.get(i).getX() - xCenter) + xCenter, pointsArray.get(i).getY()));
        }
    }


    private void reflectPointsX(ArrayList<Point> pointsArray, double yCenter) {
        double prevLen = pointsArray.size();
        for (int i = 0; i < prevLen; i++) {
            pointsArray.add(new Point(pointsArray.get(i).getX(), -(pointsArray.get(i).getY() - yCenter) + yCenter));
        }
    }
}
