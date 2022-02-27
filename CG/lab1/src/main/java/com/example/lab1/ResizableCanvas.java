package com.example.lab1;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

class ResizableCanvas extends Canvas {
    private final GraphicsContext gc = getGraphicsContext2D();
    private double oldWidth = getWidth();
    private double oldHeight = getHeight();
    private final MainController controller;
    private final MainModel model = new MainModel(this);

    public ResizableCanvas(MainController controller) {
        this.controller = controller;
        model.setCurrent_set(SetNumber.NONE);
        setOnMouseMoved(mouseEvent -> {
            Point ideal = translatePointToIdeal(new Point(mouseEvent.getX(), mouseEvent.getY()));
            controller.setCurrentMousePosition(ideal.getX(), ideal.getY());
        });
        setOnMouseClicked(this::onMouseClicked);
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    public MainModel getModel() {
        return model;
    }

    private void draw() {
        double width = getWidth();
        double height = getHeight();
        if (width == 0 || height == 0) { return; }
        double deltaWidth = width / (oldWidth == 0 ? width : oldWidth);
        double deltaHeight = height / (oldHeight == 0 ? height : oldHeight);
        gc.clearRect(0, 0, width * 3, height * 3);

        gc.setFill(Color.BLACK);
        gc.setLineWidth(0.4);
        Point screenCenter = translatePointFromIdeal(new Point(0, 0));
        gc.strokeLine(0, screenCenter.getY(), width, screenCenter.getY());
        gc.strokeLine(screenCenter.getX(), 0, screenCenter.getX(), height);

        model.getSet1().forEach(point -> {
            scalePoint(point.getNotIdeal(), deltaWidth, deltaHeight);
            drawPoint(point.getNotIdeal(), SetNumber.FIRST);
        });
        model.getSet2().forEach(point -> {
            scalePoint(point.getNotIdeal(), deltaWidth, deltaHeight);
            drawPoint(point.getNotIdeal(), SetNumber.SECOND);
        });

        if (!model.getCircle().isZero()) {
            Circle circle = model.findCircle();
            if (!circle.isZero() && !circle.getCenter().isInfinity()) {
                drawCircle(circle);
            }
        }

        oldWidth = width;
        oldHeight = height;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) { return getWidth(); }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

    void aboutProgramBtnDidTap() {
        showInfoAlert("Даны два множества точек на плоскости. " +
                "Выбрать три различные точки первого множества так, чтобы круг, ограниченный окружностью, " +
                "проходящей через эти три точки, " +
                "содержал минимум 80% точек второго множества и имел минимальную площадь."
        );
    }

    void aboutAuthorDidTap() {
        showInfoAlert("Татаринова Дарья ИУ7-44Б");
    }

    void scale(Boolean isPlus) {
        if (isPlus) { model.incrementCurrScale(); }
        else { model.decrementCurrScale(); }
        model.scale();
        draw();
    }

    void firstSetBtnDidTap(ActionEvent event) {
        model.setCurrent_set(SetNumber.FIRST);
    }

    void secondSetBtnDidTap(ActionEvent event) {
        model.setCurrent_set(SetNumber.SECOND);
    }

    void editBtnDidTap() {
        model.setEditingMode(EditingMode.POINT_CHOSEN);
    }

    void inputBtnDidTap() {
        model.setEditingMode(EditingMode.POINT_ADD);
    }

    void cancelBtnDidTap() {
        switch (model.getLastAction()) {
            case NONE:
                break;
            case ADD_POINT:
                model.removePoint();
                break;
            case DRAW_CIRCLE:
                model.setCircle(new Circle());
                break;
            case EDIT_POINT:
                model.removePoint();
                model.addToSet(model.getEditedPoint().getIdeal(), model.getEditedPoint().getNotIdeal());
                break;
            case REMOVE_POINT:
                model.addToSet(model.getEditedPoint().getIdeal(), model.getEditedPoint().getNotIdeal());
                break;
        }
        draw();
    }

    void calculateBtnDidTap() {
        model.calculateBtnDidTap();
        Circle circle = model.getCircle();
        if (!circle.isZero() && !circle.getCenter().isInfinity()) {
            model.setLastAction(LastAction.DRAW_CIRCLE);
            Point translateCircleCenter = translatePointToIdeal(circle.getCenter());
            double radius = circle.getRadius() / model.getCurrScale();
            controller.showResult(translateCircleCenter.getX(), translateCircleCenter.getY(), radius);
        }
        draw();
    }

    void deleteBtnDidTap() {
        model.deleteEditngPoint();
        model.setEditingMode(EditingMode.POINT_CHOSEN);
        model.setLastAction(LastAction.REMOVE_POINT);
        draw();
    }

    void clearBtnDidTap() {
        model.clearAll();
        controller.clearResult();
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
        model.move();
        draw();
    }

    void addPointManually(double x, double y, SetNumber setNumber) {
        Point idealPoint = new Point(x, y);
        Point translatePoint = translatePointFromIdeal(idealPoint);
        if (!model.containsIdeal(idealPoint)) {
            model.setCurrent_set(setNumber);

            drawPoint(translatePoint, setNumber);
            model.addToSet(idealPoint, translatePoint);
        }
    }

    void addPointByCanvas(double x, double y, SetNumber setNumber) {
        Point realPoint = new Point(x, y);
        Point idealPoint = translatePointToIdeal(realPoint);
        if (!model.containsIdeal(idealPoint)) {
            model.setCurrent_set(setNumber);

            drawPoint(realPoint, setNumber);
            model.addToSet(idealPoint, realPoint);
        }
    }

    Point translatePointFromIdeal(Point point) {
        double newX = (point.getX() + model.getTranslateCoords().getX()) * model.getCurrScale();
        double newY = (point.getY() + model.getTranslateCoords().getY()) * model.getCurrScale();
        newX = newX + getWidth() / 2;
        newY = (newY - getHeight() / 2) * (-1);
        return new Point(newX, newY);
    }

    Point translatePointToIdeal(Point point) {
        double newX = point.getX() - getWidth() / 2;
        double newY = point.getY() * (-1) + getHeight() / 2;
        newX = newX / model.getCurrScale() - model.getTranslateCoords().getX();
        newY = newY / model.getCurrScale() - model.getTranslateCoords().getY();
        return new Point(newX, newY);
    }

    private void onMouseClicked(MouseEvent event) {
        requestFocus();
        if (model.getEditingMode() == EditingMode.POINT_CHOSEN) {
            Point notIdealPoint = new Point(event.getX(), event.getY());
            CanvasPoint point = model.findClosestAndRemove(notIdealPoint);
            if (point.getNotIdeal().isInfinity()) { return; }
            model.setEditedPoint(point);
            model.setLastAction(LastAction.EDIT_POINT);
            draw();
            gc.setFill(Color.rgb(0, 0, 0, 0.2));
            gc.fillOval(
                    point.getNotIdeal().getX() - Constants.pointRadius,
                    point.getNotIdeal().getY() - Constants.pointRadius,
                    Constants.pointDiameter,
                    Constants.pointDiameter
            );
            model.setEditingMode(EditingMode.POINT_SET);
            return;
        }
        if (model.getEditingMode() == EditingMode.POINT_SET) {
            draw();
            addPointByCanvas(event.getX(), event.getY(), model.getSetToEdit());
            model.setEditingMode(EditingMode.POINT_CHOSEN);
            return;
        }
        if (model.getCurrent_set() == SetNumber.NONE) { return; }
        model.setLastAction(LastAction.ADD_POINT);
        addPointByCanvas(event.getX(), event.getY(), model.getCurrent_set());
    }

    private void drawCircle(Circle circle) {
        gc.setFill(Color.rgb(0, 128, 0, 0.3));
        double radius = circle.getRadius();
        double diameter = circle.getRadius() * 2;
        gc.fillOval(circle.getCenter().getX() - radius, circle.getCenter().getY() - radius, diameter, diameter);
    }

    private void drawPoint(Point point, SetNumber setNumber) {
        switch (setNumber) {
            case NONE -> gc.setFill(Color.WHITE);
            case FIRST -> gc.setFill(Color.RED);
            case SECOND -> gc.setFill(Color.BLUE);
        }
        gc.fillOval(
                point.getX() - Constants.pointRadius,
                point.getY() - Constants.pointRadius,
                Constants.pointDiameter,
                Constants.pointDiameter
        );
    }

    private void scalePoint(Point point, double deltaX, double deltaY) {
        point.setX(point.getX() - oldWidth / 2 + getWidth() / 2);
        point.setY(point.getY() - oldHeight / 2 + getHeight() / 2);
    }

    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }

    static class Constants {
        static double pointDiameter = 6.0;
        static double pointRadius = Constants.pointDiameter / 2;
    }
}
