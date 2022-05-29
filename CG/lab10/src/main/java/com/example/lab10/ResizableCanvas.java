package com.example.lab10;

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
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    private void draw() {
        clear();

//        oldWidth = width;
//        oldHeight = height;
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

    void drawBtnDidTap() {
        build_graph(true, 1);
    }

    void build_graph(boolean new_graph, double scale_param) {
        clear();

        if (new_graph) {
            model.createTransMatrix();
        }

        FunctionType f = getFunc();

        Limit x_limits = getXLimit(), z_limits = getZLimit();

        ArrayList<Double> highHorizon = new ArrayList<>();
        for (int i = 0; i < getWidth() + 1; i++) {
            highHorizon.add(0.0);
        }
        ArrayList<Double> lowHorizon = new ArrayList<>();
        for (int i = 0; i < getWidth() + 1; i++) {
            lowHorizon.add(getHeight());
        }

//        Горизонт
        for (double z = z_limits.getStart(); z < z_limits.getEnd() + z_limits.getStep(); z += z_limits.getStep()) {
            draw_horizon(f, highHorizon, lowHorizon, x_limits, z, scale_param);
        }

//        Границы горизонта
        draw_horizon_limits(f, x_limits, z_limits, scale_param);
    }

    private void draw_horizon(
            FunctionType functionType,
            ArrayList<Double> high_horizon,
            ArrayList<Double> low_horizon,
            Limit limits,
            double z,
            double scale_param
    ) {
//        Рисование горизонта

        Point3D prev = new Point3D();

        for (double x = limits.getStart(); x < limits.getEnd() + limits.getStep(); x += limits.getStep()) {
            double f = model.function(functionType, x, z);
            Point3D cur = trans_dot(new Point3D(x, f, z), scale_param);

            if (prev.exists()) {
                draw_horizon_part(prev, cur, high_horizon, low_horizon);
            }

            prev = cur;
        }
    }

    private void draw_horizon_limits(FunctionType f, Limit x_limits, Limit z_limits, double scale_param) {
        Color color = controller.getResColor();

        for (double z = z_limits.getStart(); z < z_limits.getEnd() + z_limits.getStep(); z += z_limits.getStep()) {
            double f_res = model.function(f, x_limits.getStart(), z);
            Point3D dot1 = trans_dot(new Point3D(x_limits.getStart(), f_res, z), scale_param);
            f_res = model.function(f, x_limits.getStart(),z + x_limits.getStep());
            Point3D dot2 = trans_dot(new Point3D(x_limits.getStart(), f_res, z + x_limits.getStep()), scale_param);

            drawLine(new Point(dot1.getX(), dot1.getY()), new Point(dot2.getX(), dot2.getY()), color);

            f_res = model.function(f, x_limits.getEnd(), z);
            dot1 = trans_dot(new Point3D(x_limits.getEnd(), f_res, z), scale_param);
            f_res = model.function(f, x_limits.getEnd(), z + x_limits.getStep());
            dot2 = trans_dot(new Point3D(x_limits.getEnd(), f_res, z + x_limits.getStep()), scale_param);

            drawLine(new Point(dot1.getX(), dot1.getY()), new Point(dot2.getX(), dot2.getY()), color);
        }
    }

    private Point3D trans_dot(Point3D dot, double scale_param) {
//        Транспонирование точки

        Double[] dotCopy = new Double[4];
        dotCopy[0] = dot.getX();
        dotCopy[1] = dot.getY();
        dotCopy[2] = dot.getZ();
        dotCopy[3] = 1.0;

        Double[] res_dot = new Double[4];
        for (int i = 0; i < 4; i++) {
            res_dot[i] = 0.0;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res_dot[i] += dotCopy[j] * model.getTransMatrix()[j][i];
            }
        }

        for (int i = 0; i < 3; i++) {
            res_dot[i] *= scale_param;
        }

        res_dot[0] += getWidth() / 2;
        res_dot[1] += getHeight() / 2;

        return new Point3D(res_dot[0], res_dot[1], res_dot[2]);
    }

    private void draw_horizon_part(Point3D dot1, Point3D dot2, ArrayList<Double> high_horizon, ArrayList<Double> low_horizon) {
//        Рисование горизонтальной части

        if (dot1.getX() > dot2.getX()) {
            Point3D temp = dot1;
            dot1 = dot2;
            dot2 = temp;
        }

        double dx = dot2.getX() - dot1.getX();
        double dy = dot2.getY() - dot1.getY();

        double l = Math.max(dx, dy);

        dx /= l;
        dy /= l;

        double x = dot1.getX();
        double y = dot1.getY();

        for (int i = 0; i < (int) l + 1; i++) {
            if (!draw_dot(Math.round(x), y, high_horizon, low_horizon)){
                return;
            }
            x += dx;
            y += dy;
        }
    }

    private boolean draw_dot(double x, double y, ArrayList<Double> high_horizon, ArrayList<Double> low_horizon) {
//        Рисование точки
        if (!is_visible(new Point(x, y))) {
            return false;
        }
        if (y > high_horizon.get((int)x)) {
            high_horizon.set((int)(x), y);
            drawPixel(x, y);
        } else if (y < low_horizon.get((int)(x))){
            low_horizon.set((int)x, y);
            drawPixel(x, y);
        }
        return true;
    }

    private boolean is_visible(Point dot) {
//        Проверка точки на видимость
        return (0 <= dot.getX() && dot.getX() <= getWidth()) && // todo: abs() < EPS
                (0 <= dot.getY() && dot.getY() <= getHeight());
    }

    private Limit getXLimit() {
        return controller.getXLimit();
    }

    private Limit getZLimit() {
        return controller.getZLimit();
    }

    private FunctionType getFunc() {
        return controller.getFunc();
    }

    private void clear() {
        double width = getWidth();
        double height = getHeight();
        if (width == 0 || height == 0) { return; }

        gc.clearRect(0, 0, width, height);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
    }

    private void drawPixel(double x, double y) {
        Color color = controller.getResColor();
        pixelWriter.setColor((int)x, (int)y, color);
    }

    private void drawLine(Point start, Point end, Color color) {
        gc.setStroke(color);
        gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
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
}


