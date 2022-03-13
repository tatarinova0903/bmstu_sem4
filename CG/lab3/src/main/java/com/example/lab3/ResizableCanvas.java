package com.example.lab3;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import java.lang.Math;

class ResizableCanvas extends Canvas {
    private final int INTENSITY = 255;
    private final GraphicsContext gc = getGraphicsContext2D();
    private final PixelWriter pixelWriter = gc.getPixelWriter();
    private double oldWidth = getWidth();
    private double oldHeight = getHeight();
    private final Model model = new Model();
    private Color lastBackgroundColor = Color.WHITE;
    private Color backgroundColor = Color.WHITE;
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
        if (isPlus) { model.incrementCurrScale(); }
        else { model.decrementCurrScale(); }
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
            case BREZ_DOUBLE -> {
                BREZ_DOUBLE(startRealPoint, endRealPoint, true, segment.getColor());
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
        int i = 1, step = 1;
        while (i <= L + 1) {
            if (draw) {
                drawPoint(Math.round(x), Math.round(y), color, INTENSITY);
            }
            if (!draw && i <= L) {
                if (!((Math.round(x + sx) == Math.round(x) && Math.round(y + sy) != Math.round(y)) ||
                        (Math.round(x + sx) != Math.round(x) && Math.round(y + sy) == Math.round(y)))) {
                    step += 1;
                }
            }
            x += sx;
            y += sy;
            i += 1;
        }
        if (!draw) {
            return step;
        } else {
            return -1;
        }
    }

    private int BREZ_INT(Point p_start, Point p_end, boolean draw, Color color) {
        if (isSegmentDegenerate(p_start, p_end, draw)) {
            return -1;
        }
        double dx = p_end.getX() - p_start.getX();
        double dy = p_end.getY() - p_start.getY();
//        Вычисление шага изменение каждой координаты пикселя
        int s_x = (int) Math.signum(dx);
        int s_y = (int) Math.signum(dy);
//        Вычисление модуля приращения.
        dx = Math.abs(dx);
        dy = Math.abs(dy);
//        Обмен местами координат в случае m > 1 (тангенс)
        int change = 0;
        if (dy >= dx) {
            double temp = dx;
            dx = dy;
            dy = temp;
            change = 1;
        }
//        Иницилизация начального значения ошибки.
        double m = 2 * dy; // в случае FLOAT m = dy / dx
        double m1 = 2 * dx;
        double e = m - dx; // в случае FLOAT e = m - 0.5
//        Инициализации начальных значений текущего пикселя
        int x = (int) Math.round(p_start.getX());
        int y = (int) Math.round(p_start.getY());
//        Цикл от i = 1 до i = dx + 1 с шагом 1
        int i = 1, step = 1, x_buf = x, y_buf = y;
        while (i <= dx + 1) {
            if (draw) {
                drawPoint(x, y, color, INTENSITY);
            }
            if (e >= 0) {
                if (change == 1) {
                    x += s_x;
                } else {
                    y += s_y;
                }
                e -= m1; // в случае FLOAT e -=1
            }
            if (e <= 0) {
                if (change == 1) {
                    y += s_y;
                } else {
                    x += s_x;
                }
                e += m;
            }
            i += 1;
            if (!draw) {
                if (!((x_buf == x && y_buf != y) || (x_buf != x && y_buf == y))) {
                    step += 1;
                }
                x_buf = x;
                y_buf = y;
            }
        }
        if (!draw) {
            return step;
        } else {
            return -1;
        }
    }

    private int BREZ_DOUBLE(Point p_start, Point p_end, boolean draw, Color color) {
        if (isSegmentDegenerate(p_start, p_end, draw)) {
            return -1;
        }
        double dx = p_end.getX() - p_start.getX();
        double dy = p_end.getY() - p_start.getY();

        int s_x = (int) Math.signum(dx);
        int s_y = (int) Math.signum(dy);

        dx = Math.abs(dx);
        dy = Math.abs(dy);
//        Обмен местами координат в случае m > 1 (тангенс)
        int change = 0;
        if (dy >= dx) {
            double temp = dx;
            dx = dy;
            dy = temp;
            change = 1;
        }

//        Иницилизация начального значения ошибки.
        double m = dy / dx; // в случае INT m = 2 * dy
        double e = m - 0.5; // в случае INT e = m - dx
//        Инициализации начальных значений текущего пикселя
        int x = (int) Math.round(p_start.getX());
        int y = (int) Math.round(p_start.getY());
//        Цикл от i = 1 до i = dx + 1 с шагом 1
        int i = 1, step = 1, x_buf = x, y_buf = y;
        while (i <= dx + 10) {
            if (draw) {
                drawPoint(x, y, color, INTENSITY);
            }
//            Вычисление координат и ошибки для след пикселя.
            if (e >= 0) {
                if (change == 1) {
                    x += s_x;
                }
                else {
                    y += s_y;
                }
                e -= 1; //в случае INT e -=2 * dx
            }
            if (e <= 0) {
                if (change == 1) {
                    y += s_y;
                } else {
                    x += s_x;
                }
                e += m;
            }
            i += 1;
            if (!draw) {
                if (!((x_buf == x && y_buf != y) || (x_buf != x && y_buf == y))) {
                    step += 1;
                }
                x_buf = x;
                y_buf = y;
            }
        }
        if (!draw) {
            return step;
        }
        return -1;
    }

    private int BREZ_SMOOTH(Point p_start, Point p_end, boolean draw, Color color) {
        if (isSegmentDegenerate(p_start, p_end, draw)) {
            return -1;
        }
        int I = 255;
        double dx = p_end.getX() - p_start.getX();
        double dy = p_end.getY() - p_start.getY();

        int s_x = (int) Math.signum(dx);
        int s_y = (int) Math.signum(dy);
        dx = Math.abs(dx);
        dy = Math.abs(dy);
//        Обмен местами координат в случае m > 1 (тангенс)
        int change = 0;
        if (dy >= dx) {
            double temp = dx;
            dx = dy;
            dy = temp;
            change = 1;
        }
//        Иницилизация начального значения ошибки.
        double m = dy / dx;
        double e = I / 2;
//        Инициализации начальных значений текущего пикселя
        int x = (int) Math.round(p_start.getX());
        int y = (int) Math.round(p_start.getY());
//        Вычисление скорректированного  значения  тангенса  угла наклона m и коэффициента W.
        m *= I;
        double W = I - m;
//        Высвечивание пиксела  с координатами  (X,Y) интенсивностью E(f).
        if (draw) {
            drawPoint(x, y, color, Math.round(e));
        }
//        Цикл от i = 1 до i = dx + 1 с шагом 1
        int i = 1, step = 1, x_buf = x, y_buf = y;
        while (i <= dx) {
            if (e < W) {
                if (change == 0) {
                    x += s_x;
                } else {
                    y += s_y;
                }
                e += m;
            } else {
                x += s_x;
                y += s_y;
                e -= W;
            }
            if (draw) {
                drawPoint(x, y, color, Math.round(e));
            }
            if (!draw) {
                if (!((x_buf == x && y_buf != y) || (x_buf != x && y_buf == y))) {
                    step += 1;
                }
                x_buf = x;
                y_buf = y;
            }
            i += 1;
        }
        if (!draw) {
            return step;
        }
        return -1;
    }

    private int VU(Point p_start, Point p_end, boolean draw, Color color) {
        if (isSegmentDegenerate(p_start, p_end, draw)) {
            return -1;
        }
        int Imax = 255;
        double dx = p_end.getX() - p_start.getX();
        double dy = p_end.getY() - p_start.getY();
        double m = 1;
        int shag = 1, step = 1;
        if (Math.abs(dy) > Math.abs(dx)) {
            if (dy != 0) {
                m = dx / dy;
            }
            double m1 = m;
            if (p_start.getY() > p_end.getY()) {
                m1 *= -1;
                shag *= -1;
            }
            for(int y = (int) Math.round(p_start.getY()); shag < 0 ? (y > Math.round(p_end.getY()) + 1) : (y < Math.round(p_end.getY()) + 1); y += shag) {
                double d1 = p_start.getX() - Math.floor(p_start.getX());
                double d2 = 1 - d1;
                if (draw) {
                    // нижняя точка
                    drawPoint(p_start.getX(), y, color, Math.round(Math.abs(d2) * Imax));
                    // верхняя точка
                    drawPoint(p_start.getX() + 1, y, color, Math.round(Math.abs(d1) * Imax));
                }
                if (!draw && y < Math.round(p_end.getY())) {
                    if ((int)p_start.getX() != (int)(p_start.getX() + m)) {
                        step += 1;
                    }
                }
                p_start.setX(p_start.getX() + m1);
            }
        }
        else {
            if (dx != 0) {
                m = dy / dx;
            }
            double m1 = m;
            if (p_start.getX() > p_end.getX()) {
                shag *= -1;
                m1 *= -1;
            }
            for (int x = (int) Math.round(p_start.getX()); shag < 0 ? (x > Math.round(p_end.getX()) + 1) : (x < Math.round(p_end.getX()) + 1); x += shag) {
                double d1 = p_start.getY() - Math.floor(p_start.getY());
                double d2 = 1 - d1;
                if (draw) {
                    //нижняя точка
                    drawPoint(x, p_start.getY(), color, Math.round(Math.abs(d2) * Imax));
                    //верхняя точка
                    drawPoint(x, p_start.getY() + 1, color, Math.round(Math.abs(d1) * Imax));
                }
                if (!draw && x < Math.round(p_end.getX())) {
                    if ((int) p_start.getY() != (int) (p_start.getY() + m)) {
                        step += 1;
                    }
                }
                p_start.setY(p_start.getY() + m1);
            }
        }
        if (!draw) {
            return step;
        }
        return -1;
    }

    private void drawPoint(double x, double y, Color color, double intensity)
    {
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
}
