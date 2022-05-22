package com.example.lab9;

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
        if (!checkClipper(model.getClipper())) {
            controller.showInfoAlert("Остекатель невыпуклый");
            return;
        }
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
        ArrayList<Vector> normals_list = getNormalList(clipper);
        ArrayList<Point> res = cut_figure(figure, clipper, normals_list);
        model.lock(res);
        gc.setLineWidth(3);
        drawFigure(res, controller.getResColor());
        gc.setLineWidth(1);
    }

    ArrayList<Point> cut_figure(ArrayList<Point> figure, ArrayList<Point> clipper, ArrayList<Vector> normals) {
        ArrayList<Point> res = figure;

        for (int i = 0; i < clipper.size() - 1; ++i) {
            Segment cur_edge = new Segment(
                    clipper.get(i),
                    clipper.get((i + 1) % clipper.size())
            );

            res = edgeCutFigure(res, cur_edge, normals.get(i));

            if (res.size() < 3) {
                return new ArrayList<Point>();
            }
        }

        return res;
    }

    ArrayList<Point> edgeCutFigure(ArrayList<Point> figure, Segment edge, Vector normal)
    {
        ArrayList<Point> res = new ArrayList<>();

        if (figure.size() < 3) {
            return new ArrayList<Point>();
        }

        boolean prevCheck = checkPoint(figure.get(0), edge.getStart(), edge.getEnd());

        for (int i = 1; i < figure.size() + 1; ++i) {
            boolean curCheck = checkPoint(figure.get(i % figure.size()), edge.getStart(), edge.getEnd());

            if (prevCheck) {
                if (curCheck) {
                    res.add(figure.get(i % figure.size()));
                } else {
                    res.add(findIntersection(
                            new Segment(figure.get(i - 1), figure.get(i % figure.size())), edge, normal)
                    );
                }
            } else {
                if (curCheck) {
                    res.add(findIntersection(
                            new Segment(figure.get(i - 1), figure.get(i % figure.size())), edge, normal)
                    );
                    res.add(figure.get(i % figure.size()));
                }
            }

            prevCheck = curCheck;
        }

        return res;
    }

    Point findIntersection(Segment section, Segment edge, Vector normal)
    {
        Vector wi = new Vector(edge.getStart(), section.getStart());
        Vector d = new Vector(section.getStart(), section.getEnd());
        double Wck = scalarMult(wi, normal);
        double Dck = scalarMult(d, normal);

        Point diff = new Point(section.getDX(), section.getDY());

        double t = - Wck / Dck;

        return new Point(
                section.getStart().getX() + diff.getX() * t,
                section.getStart().getY() + diff.getY() * t
        );
    }

    boolean checkPoint(Point point, Point p1, Point p2) {
        return vectMult(new Vector(p1, p2), new Vector(p1, point)) <= 0;
    }

    ArrayList<Vector> getNormalList(ArrayList<Point> points) {
        int len = points.size();
        ArrayList<Vector> res = new ArrayList<>();
        for (int i = 0; i < len; ++i)
            res.add(
                    getNormal(points.get(i), points.get((i + 1) % len),
                            points.get((i + 2) % len))
            );

        return res;
    }

    private Vector getNormal(Point p1, Point p2, Point cp) {
        Vector vect = new Vector(p2.getX() - p1.getX(), p2.getY() - p1.getY());
        Vector norm;
        if (vect.getA() == 0) {
            norm = new Vector(1, 0);
        } else {
            norm = new Vector(-vect.getB() / vect.getA(), 1);
        }

        if (scalarMult(new Vector(cp.getX() - p2.getX(), cp.getY() - p2.getY()), norm) < 0 ) {
            norm.setA(-norm.getA());
            norm.setB(-norm.getB());
        }

        return norm;
    }

    private double scalarMult(Vector a, Vector b) {
        return a.getA() * b.getA() + a.getB() * b.getB();
    }

    private double vectMult(Vector v1, Vector v2) {
        return v1.vectMult(v2);
    }

    private boolean checkClipper(ArrayList<Point> clipper) {
        if (clipper.size() < 3) {
            return false;
        }

        int sign = 1;
        Vector v21 = new Vector(clipper.get(2).getX() - clipper.get(1).getX(), clipper.get(2).getY() - clipper.get(1).getY());
        Vector v10 = new Vector(clipper.get(1).getX() - clipper.get(0).getX(), clipper.get(1).getY() - clipper.get(0).getY());
        if (vectMult(v21, v10) < 0) {
            sign = -1;
        }

        //   В цикле проверяем совпадения знаков векторных произведений
        //   всех пар соседних ребер со знаком первого
        //   векторного произведения
        int len = clipper.size();
        for (int i = 3; i < len + 3; ++i)
        {
            if (sign * vectMult(
                    new Vector(clipper.get(i % len), clipper.get((i - 1) % len)),
                    new Vector(clipper.get((i - 1) % len), clipper.get((i - 2) % len))) < 0) {
                return false;
            }
        }

        return true;
    }
}

