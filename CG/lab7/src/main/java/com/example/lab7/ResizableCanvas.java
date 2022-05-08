package com.example.lab7;

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

        drawClipper(model.getClipper());
        drawFigure(model.getFigure(), Color.BLACK);
        clipBtnDidTap();

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

    public void drawClipperBtnDidTap(int xLeft, int xRight, int yUp, int yDown) {
        model.setClipper(new Clipper(xLeft, xRight, yUp, yDown));
        drawClipper(model.getClipper());
    }

    public void addPointBtnDidTap(double x, double y) {
        model.addPointToFigure(new Point(x, y));
        Point point = translatePointFromIdeal(new Point(x, y));
        drawPoint((int) point.getX(), (int) point.getY(), Color.BLACK);
        drawFigure(model.getFigure(), Color.BLACK);
    }

    public void clipBtnDidTap() {
        clip();
        gc.setLineWidth(3);
        drawFigure(model.getRes(), controller.getResColor());
        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
    }

    private void clip() {
        int segmentsCount = model.getFigure().size();
        Clipper clipper = model.getClipper();
        ArrayList<Segment> figure = model.getFigure();
        Point Q = null;
        int j = 0;
        for (int i = 0; i < segmentsCount; i++) {
            boolean punkt12 = false;
            Point P1 = figure.get(i).getStart();
            Point P2 = figure.get(i).getEnd();

            // Вычисление кодов концов отрезка T1, T2.
            int T1_1 = (P1.getX() < clipper.getxLeft()) ? 1 : 0;
            int T1_2 = (P1.getX() > clipper.getxRight()) ? 1 : 0;
            int T1_3 = (P1.getY() < clipper.getyDown()) ? 1 : 0;
            int T1_4 = (P1.getY() > clipper.getyUp()) ? 1 : 0;

            int T2_1 = (P2.getX() < clipper.getxLeft()) ? 1 : 0;
            int T2_2 = (P2.getX() > clipper.getxRight()) ? 1 : 0;
            int T2_3 = (P2.getY() < clipper.getyDown()) ? 1 : 0;
            int T2_4 = (P2.getY() > clipper.getyUp()) ? 1 : 0;

            int S1 = T1_1 + T1_2 + T1_3 + T1_4;
            int S2 = T2_1 + T2_2 + T2_3 + T2_4;

            // Установка признака видимости отрезка pr =1
            int pr = 1;

            // Задание начального значения  тангенса  угла  наклона отрезка m=10^30
            // (большое число,  вначале предполагается,  что отрезок вертикальный).
            double m = Math.pow(10, 30);

            if (S1 == 0 && S2 == 0) { // отрезок видимый
                addToRes(P1, P2);
                // TODO: - Переход к 31.
                continue;
            }

            int PL = T1_1 * T2_1 + T1_2 * T2_2 + T1_3 * T2_3 + T1_4 * T2_4;

            if (PL != 0) { // отрезок невидим
                pr = -1;
                // TODO: - Переход к 31.
                continue;
            }

            // 9. Проверка видимости первого конца отрезка
            if (S1 == 0) { // первый конец видим
                addToRes(P1);
                Q = P2;
                j = 2;
                // TODO: - Переход к 15.
            } else if (S2 == 0) { // второй конец видим
                addToRes(P2);
                Q = P1;
                j = 2;
                // TODO: - Переход к 15.
            } else {
                j = 0; // Установка начального значения шага отсечения j=0.
                punkt12 = true;
            }
            while (true) {
                if (punkt12) {
                    j = j + 1; // Вычисление текущего номера шага отсечения i=i+1.
                    if (j > 2) { // Проверка завершения процедуры отсечения:  если i>2, то переход к п.31
                        // TODO: - Переход к 31.
                        break;
                    }
                    // Занесение  в  рабочую  переменную  Q координат i-ой вершины Q=Pi.
                    if (j == 1) { Q = P1; }
                    if (j == 2) { Q = P2; }
                }
                // 15. Определение   расположения   отрезка:   если  X2=X1 (отрезок вертикальный),  то
                // TODO: - переход к п.23
                // (не  может  быть пересечения с левой и правой границами отсекателя).
                if (Math.abs(P1.getX() - P2.getX()) > 1e-6) {
                    // 16. Вычисление тангенса угла наклона отрезка.
                    m = (P2.getY() - P1.getY()) / (P2.getX() - P1.getX());
                    // 17. Проверка возможности пересечения с  левой  границей отсекателя:
                    // если Qx>Xл (пересечения нет), то переход к п.20.
                    if (Q.getX() <= clipper.getxLeft()) {
                        // 18. Вычисление  ординаты  точки  пересечения отрезка с левой границей отсекателя
                        double Yp = m * (clipper.getxLeft() - Q.getX()) + Q.getY();
                        // 19. Проверка корректности найденного пересечения:
                        // если (Yр>=Yн)&(Yp<=Yв) == истина (пересечение корректное), то выполнение следующих действий:
                        // Ri.x =Xл,  Ri..y =Yр (занесение полученных координат в результат), переход к п.12.
                        if (Yp >= clipper.getyDown() && Yp <= clipper.getyUp()) {
                            addToRes(new Point(clipper.getxLeft(), (int) Yp));
                            punkt12 = true;
                            continue;
                        }
                    }
                    // 20. Проверка возможности пересечения отрезка с правой границей отсекателя:
                    // если  Qx<Xп (пересечения  нет),  то переход к п.23.
                    if (Q.getX() >= clipper.getxRight()) {
                        // 21. Вычисление  ординаты  точки  пересечения  с  правой границей:
                        double Yp = m * (clipper.getxRight() - Q.getX()) + Q.getY();
                        // 22. Проверка корректности найденного пересечения:
                        // если (Yр>=Yн)& (Yр<=Yв) == истина (пересечение корректно),
                        // то выполнение следующих действий Ri.x =Xп, (занесение полученных координат в результат),
                        // переход  к п.12.
                        if (Yp >= clipper.getyDown() && Yp <= clipper.getyUp()) {
                            addToRes(new Point(clipper.getxRight(), (int) Yp));
                            punkt12 = true;
                            continue;
                        }
                    }
                }
                // 23. Проверка горизонтальности отрезка: если m=0, то переход к п.12.
                if (Math.abs(m) < 1e-6) {
                    punkt12 = true;
                    continue;
                }
                // 24. Проверка возможности пересечения с верхней границей отсекателя:
                // если Qy <Yв (пересечения нет), то переход к п.27.
                if (Q.getY() >= clipper.getyUp()) {
                    // 25. Вычисление  абсциссы  точки  пересечения  с верхней границей: Xр=(Yв --Qy)/m+Qx.
                    double Xp = (clipper.getyUp() - Q.getY()) / m + Q.getX();
                    // 26. Проверка корректности найденного пересечения:
                    // если (Xр>=Xл)&(Xр<=Xп) == истина (пересечение  корректно),
                    // то выполнение следующих действий:  Ri.x=Xр;  Ri.y=Yв  (занесение полученных координат в результат);
                    // переход к п. 12.
                    if (Xp >= clipper.getxLeft() && Xp <= clipper.getxRight()) {
                        addToRes(new Point((int) Xp, clipper.getyUp()));
                        punkt12 = true;
                        continue;
                    }
                }
                // 27. Проверка возможности пересечения с нижней границей отсекателя:
                // если Qx>Yн (пересечения нет), то переход к п. 30 (вершина невидима и ни одно пересечение
                // не является корректным, следовательно, отрезок невидим).
                if (Q.getY() > clipper.getyDown()) {
                    break;
                }
                // 28. Вычисление  абсциссы  точки  пересечения  с  нижней границей: Xр=(Yн --Qy)/m+Qx.
                double Xp = (clipper.getyDown() - Q.getY()) / m + Q.getX();
                // 29. Проверка корректности найденного пересечения: (Xр>=Xл)&(Xр<=Xп) == истина (пересечение корректно),
                // то выполнение следующих действий: Ri.x=Xр;  Ri.y=Yв (занесение полученных координат в результат);
                // переход к п. 12.
                if (Xp >= clipper.getxLeft() && Xp <= clipper.getxRight()) {
                    addToRes(new Point((int) Xp, clipper.getyDown()));
                    punkt12 = true;
                    continue;
                } else { break; }
            }
        }
    }

    private void addToRes(Point point) {
        model.addPointToRes(point);
    }

    private void addToRes(Point p1, Point p2) {
        model.addSegmentToRes(new Segment(p1, p2));
    }

    private void onMouseClicked(MouseEvent mouseEvent) {
        if (model.getAction() == null) { return; }
        switch (model.getAction()) {
            case FIGURE -> {
                Point ideal = translatePointFromReal(new Point(mouseEvent.getX(), mouseEvent.getY()));
                addPointBtnDidTap(ideal.getX(), ideal.getY());
            }
            case CLIPPER -> {
                Point ideal = translatePointFromReal(new Point(mouseEvent.getX(), mouseEvent.getY()));
                if (model.setClippPoint(ideal)) {
                    drawClipper(model.getClipper());
                }
            }
        }
    }

    private void drawClipper(Clipper clipper) {
        if (!clipper.exists()) { return; }
        Point leftDown = new Point(clipper.getxLeft(), clipper.getyDown());
        Point leftUp = new Point(clipper.getxLeft(), clipper.getyUp());
        Point rightDown = new Point(clipper.getxRight(), clipper.getyDown());
        Point rightUp = new Point(clipper.getxRight(), clipper.getyUp());
        ArrayList<Segment> newClipper = new ArrayList<>();
        newClipper.add(new Segment(leftDown, leftUp));
        newClipper.add(new Segment(leftUp, rightUp));
        newClipper.add(new Segment(rightUp, rightDown));
        newClipper.add(new Segment(rightDown, leftDown));
        drawFigure(newClipper, Color.BLACK);
    }

    private void drawFigure(ArrayList<Segment> figure, Color color) {
        gc.setStroke(color);
        figure.forEach(segment -> {
            Point start = translatePointFromIdeal(segment.getStart());
            Point end = translatePointFromIdeal(segment.getEnd());
            gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
        });
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
}
