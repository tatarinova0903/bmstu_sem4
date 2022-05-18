package com.example.lab8;

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

    public void drawAddPointToClipperBtnDidTap(int x, int y) {
        model.setAction(Action.CLIPPER);
        model.addPointToClipper(new Point(x, y));
        drawClipper(model.getClipper());
    }

    public void addPointBtnDidTap(double x, double y) {
        model.setAction(Action.FIGURE);
        model.addPointToFigure(new Point(x, y));
        Point point = translatePointFromIdeal(new Point(x, y));
        drawPoint((int) point.getX(), (int) point.getY(), Color.BLACK);
        drawFigure(model.getFigure(), Color.BLACK);
    }

    public void clipBtnDidTap() {
        if (!check_clipper(model.getClipper())) {
            controller.showInfoAlert("Остекатель невыпуклый");
            return;
        }
        model.setClipBtnDidTap(true);
        clip();
        gc.setLineWidth(3);
        drawFigure(model.getRes(), controller.getResColor());
        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
    }

    public void lockBtnDidTap() {
        model.lockClipper();
        drawClipper(model.getClipper());
    }

    private boolean check_clipper(ArrayList<Point> clipper) {
        if (clipper.size() < 3) {
            return false;
        }

        int sign = 1;
        Vector v21 = new Vector(clipper.get(2).getX() - clipper.get(1).getX(), clipper.get(2).getY() - clipper.get(1).getY());
        Vector v10 = new Vector(clipper.get(1).getX() - clipper.get(0).getX(), clipper.get(1).getY() - clipper.get(0).getY());
        if (vect_mult(v21, v10) < 0) {
            sign = -1;
        }

        //   В цикле проверяем совпадения знаков векторных произведений
        //   всех пар соседних ребер со знаком первого
        //   векторного произведения
        int len = clipper.size();
        for (int i = 3; i < len + 3; ++i)
        {
            if (sign * vect_mult(
                    new Vector(clipper.get(i % len), clipper.get((i - 1) % len)),
                    new Vector(clipper.get((i - 1) % len), clipper.get((i - 2) % len))) < 0) {
                return false;
            }
        }

        return true;
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
                model.addPointToClipper(ideal);
                drawClipper(model.getClipper());
            }
        }
    }

    private void drawClipper(ArrayList<Point> clipper) {
        int pointsCount = clipper.size();
        for (int i = 0; i < pointsCount - 1; i++) {
            Point start = translatePointFromIdeal(clipper.get(i));
            Point end = translatePointFromIdeal(clipper.get(i + 1));
            gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
        }
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

    private void clip() {
        for (Segment segment : model.getFigure()) {
            Segment res = clip_line(segment, model.getClipperSize());
            if (res.getStart().exists()) {
                model.addSegmentToRes(res);
            }
        }
    }

    private Segment clip_line(Segment line, int count) {
//        Вычисление директрисы заданного отрезка:
//        D = P_2-P_1

        ArrayList<Point> clipper = model.getClipper();

        Vector d = new Vector(line.getEnd().getX() - line.getStart().getX(),
                line.getEnd().getY() - line.getStart().getY());

//        Инициализация пределов значений параметра t при условии,
//        что отрезок полностью видим:
//        t_н=0,t_к=1
        double top = 0;
        double bottom = 1;

//        Начало цикла по всем сторонам отсекателя.
//        Для каждой i-ой стороны отсекателя выполнить следующие действия:
        for (int i = 0; i < clipper.size() - 1; i++) {
//            print(i)
//            Вычисление вектора внутренней нормали к очередной
//            i - ой стороне отсекателя -N_вi
            Point p0 = clipper.get(i);
            Point p1 = clipper.get(i + 1);
            Point p2 = clipper.get(i + 1 == count ? 1 : i + 2);
            Vector norm = normal(p0, p1, p2);

//            Вычисление вектора W_i = P_1 - f_i(f_i берем за вершины стороны)
            Vector w = new Vector(
                    line.getStart().getX() - p0.getX(),
                    line.getStart().getY() - p0.getY()
            );

//            Вычисление скалярного произведения векторов:
//            W_iскал = W_i N_вi
//            D_скал = DN_вi
            double d_scal = scalar_mult(d, norm);
            double w_scal = scalar_mult(w, norm);

//            Если D_скал = 0, Если W_скi > 0, то отрезок
//            (точка) видим(-а) относительно текущей стороны отсекателя
//            Проверка видимости точки, в которую выродился отрезок, или проверка видимости произвольной
//            точки отрезка в случае его параллельности стороне отсекателя:если W_скi<0, то отрезок(точка)
//            невидим(-а).Если W_скi > 0, то отрезок(точка) видим(-а) относительно текущей
//            стороны отсекателя.

            if (d_scal == 0) {
                if (w_scal < 0) { // невидима
                    return new Segment(); // todo: - 7.
                } else {
                    continue;
                }
            }

//            Вычисление параметра t:
//            t = -W_скi / D_ск
            double param = -w_scal / d_scal;


            if (d_scal > 0) { // нижняя граница видимости
                if (param <= 1) {
                    top = Math.max(top, param);
                } else {
                    return new Segment(); // todo: - 7.
                }
            } else if (d_scal < 0) { // верхняя граница видимости
                if (param >= 0) {
                    bottom = Math.min(bottom, param);
                } else {
                    return new Segment();
                }
            }

//            Проверка фактической видимости отсечённого отрезка. Если t_н > t_в, то выход
            if (top > bottom) {
                break;
            }
        }
//        Проверка фактической видимости отсечённого отрезка.
//        Если t_н≤t_в, то изобразить отрезок в
//        интервале от P(t_н ) до P(t_н ).
//        TOP - нижнее BOTTOM вернее
        if (top <= bottom) {
            return new Segment(
                    new Point(
                            Math.round(line.getStart().getX() + d.getA() * top),
                            Math.round(line.getStart().getY() + d.getB() * top)
                    ),
                    new Point(
                            Math.round(line.getStart().getX() + d.getA() * bottom),
                            Math.round(line.getStart().getY() + d.getB() * bottom)
                    )
            );
        }

        return new Segment();
    }

    private Vector normal(Point p1, Point p2, Point cp) {
        Vector vect = new Vector(p2.getX() - p1.getX(), p2.getY() - p1.getY());
        Vector norm;
        if (vect.getA() == 0) {
            norm = new Vector(1, 0);
        } else {
            norm = new Vector(-vect.getB() / vect.getA(), 1);
        }

        if (scalar_mult(new Vector(cp.getX() - p2.getX(), cp.getY() - p2.getY()), norm) < 0 ) {
            norm.setA(-norm.getA());
            norm.setB(-norm.getB());
        }

        return norm;
    }

    private double scalar_mult(Vector a, Vector b) {
        return a.getA() * b.getA() + a.getB() * b.getB();
    }

    private double vect_mult(Vector v1, Vector v2) {
        return v1.getA() * v2.getB() - v1.getB() * v2.getA();
    }
}
