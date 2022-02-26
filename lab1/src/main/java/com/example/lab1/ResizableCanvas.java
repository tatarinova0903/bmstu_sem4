package com.example.lab1;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class ResizableCanvas extends Canvas {
    private final GraphicsContext gc = getGraphicsContext2D();
    private double oldWidth = getWidth();
    private double oldHeight = getHeight();
    private final MainController controller;
    private final MainModel model = new MainModel();

    public ResizableCanvas(MainController controller) {
        this.controller = controller;
        model.setCurrent_set(SetNumber.NONE);
        setOnMouseMoved(mouseEvent -> {
            controller.setCurrentMousePosition(
                    mouseEvent.getX() - getWidth() / 2,
                    (mouseEvent.getY() - getHeight() / 2) * (-1)
            );
        });
        setOnMouseClicked(this::onMouseClicked);
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    public MainModel getModel() {
        return model;
    }

    private void draw() {
        this.setScaleX(model.getCurrScale());
        this.setScaleY(model.getCurrScale());
        clip();

        double width = getWidth();
        double height = getHeight();
        if (width == 0 || height == 0) { return; }
        double deltaWidth = width / (oldWidth == 0 ? width : oldWidth);
        double deltaHeight = height / (oldHeight == 0 ? height : oldHeight);
        gc.clearRect(0, 0, width * 3, height * 3);

        gc.setFill(Color.BLACK);
        gc.setLineWidth(0.4 / model.getCurrScale());
        gc.strokeLine(0, height / 2, width, height / 2);
        gc.strokeLine(width / 2, 0, width / 2, height);

//        gc.fillText("0", width / 2, height / 2);

        model.getSet1().forEach(point -> {
            scalePoint(point, deltaWidth, deltaHeight);
            drawPoint(point, SetNumber.FIRST);
        });
        model.getSet2().forEach(point -> {
            scalePoint(point, deltaWidth, deltaHeight);
            drawPoint(point, SetNumber.SECOND);
        });

        if (!model.getCircle().isZero()) {
            Circle circle = model.findCircle();
            if (!circle.isZero() && !circle.getCenter().isInfinity()) {
                drawCircle(circle);
            }
        }

        // scale овала
//        Oval oval = model.getOval();
//        scaleOval(oval, deltaWidth, deltaHeight);
//        drawOval(model.getOval());

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
        else {
            model.getTranslateCoords().clear();
            setTranslateX(model.getTranslateCoords().getX());
            setTranslateY(model.getTranslateCoords().getY());
            model.decrementCurrScale();
        }
        this.setScaleX(model.getCurrScale());
        this.setScaleY(model.getCurrScale());
        clip();
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
                model.addToSet(model.getEditedPoint());
                break;
            case REMOVE_POINT:
                model.addToSet(model.getEditedPoint());
                break;
        }
        draw();
//        model.setCurrent_set(SetNumber.NONE);
    }

    void calculateBtnDidTap() {
        model.calculateBtnDidTap();
        if (!model.getCircle().isZero()) {
            model.setLastAction(LastAction.DRAW_CIRCLE);
            Circle resToShow = new Circle(new Point(
                    model.getCircle().getCenter().getX() - getWidth() / 2,
                    (model.getCircle().getCenter().getY() - getHeight() / 2) * (-1)
            ), model.getCircle().getRadius());
            controller.showResult(resToShow);
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
        double newX, newY;
        switch (direction) {
            case RIGHT -> {
                model.getTranslateCoords().incrementX();
                newX = newXForScale(model.getCurrScale());
                if (newX < 0) {
                    model.getTranslateCoords().decrementX();
                    return;
                }
            }
            case LEFT -> {
                model.getTranslateCoords().decrementX();
                newX = newXForScale(model.getCurrScale());
                double screenWidth = getWidth();
                double shownWidth = (getWidth() - newX) * model.getCurrScale();
                if (screenWidth > shownWidth) {
                    model.getTranslateCoords().incrementX();
                    return;
                }
            }
            case DOWN -> {
                model.getTranslateCoords().incrementY();
                newY = newYForScale(model.getCurrScale());
                if (newY < 0) {
                    model.getTranslateCoords().decrementY();
                    return;
                }
            }
            case UP -> {
                model.getTranslateCoords().decrementY();
                newY = newYForScale(model.getCurrScale());
                double screenHeight = getHeight();
                double shownHeight = (getHeight() - newY) * model.getCurrScale();
                if (screenHeight > shownHeight) {
                    model.getTranslateCoords().incrementY();
                    return;
                }
            }
        }
        setTranslateX(model.getTranslateCoords().getX());
        setTranslateY(model.getTranslateCoords().getY());
        clip();
    }

    void addPoint(double x, double y, SetNumber setNumber) {
        if (!model.contains(new Point(x, y))) {
            model.setCurrent_set(setNumber);
            Point point = new Point(x, y);

            drawPoint(point, setNumber);
            model.addToSet(point);
        }
    }

    private void clip() {
        double newWidth = getWidth() * 3; // isPlus ? getWidth() : getWidth() * (2 - model.getCurrScale());
        double newX = newXForScale(model.getCurrScale());
        double newHeight = getHeight() * 3; // isPlus ? getHeight() : getHeight() * (2 - model.getCurrScale());
        double newY = newYForScale(model.getCurrScale());
        Rectangle rect = new Rectangle(newX, newY, newWidth, newHeight);
        this.setClip(rect);
    }

    private double newXForScale(double scale) {
        double oldWidth = getWidth();
        double newWidth = oldWidth * scale;
        double newX = (newWidth - oldWidth) / 2 - model.getTranslateCoords().getX();
        return newX * oldWidth / newWidth;
    }

    private double newYForScale(Double scale) {
        double oldHeight = getHeight();
        double newHeight = oldHeight * scale;
        double newY = (newHeight - oldHeight) / 2  - model.getTranslateCoords().getY();
        return newY * oldHeight / newHeight;
    }

    private void onMouseClicked(MouseEvent event) {
        if (model.getEditingMode() == EditingMode.POINT_CHOSEN) {
            Point point = model.findClosestAndRemove(new Point(event.getX(), event.getY()));
            if (point.isInfinity()) { return; }
            model.setEditedPoint(point);
            model.setLastAction(LastAction.EDIT_POINT);
            draw();
            gc.setFill(Color.rgb(0, 0, 0, 0.2));
            gc.fillOval(
                    point.getX() - Constants.pointRadius,
                    point.getY() - Constants.pointRadius,
                    Constants.pointDiameter,
                    Constants.pointDiameter
            );
            model.setEditingMode(EditingMode.POINT_SET);
            return;
        }
        if (model.getEditingMode() == EditingMode.POINT_SET) {
            addPoint(event.getX(), event.getY(), model.getSetToEdit());
            model.setEditingMode(EditingMode.POINT_CHOSEN);
            draw();
            return;
        }
        if (model.getCurrent_set() == SetNumber.NONE) { return; }
        model.setLastAction(LastAction.ADD_POINT);
        addPoint(event.getX(), event.getY(), model.getCurrent_set());
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

    private void drawOval(Oval oval) {
        gc.setFill(Color.rgb(0, 128, 0, 0.3));
        double width = oval.getWidth();
        double height = oval.getHeight();
        gc.fillOval(oval.getCenter().getX() - width / 2, oval.getCenter().getY() - height / 2, width, height);
    }

    private void scalePoint(Point point, double deltaX, double deltaY) {
        point.setX(point.getX() * deltaX);
        point.setY(point.getY() * deltaY);
    }

    private void scaleOval(Oval oval, double deltaX, double deltaY) {
        Point center = oval.getCenter();
        oval.setCenter(new Point(center.getX() * deltaX, center.getY() * deltaY));
        oval.setWidth(oval.getWidth() * deltaX);
        oval.setHeight(oval.getHeight() * deltaY);
    }

    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }

    static class Constants {
        static double pointDiameter = 3.0;
        static double pointRadius = Constants.pointDiameter / 2;
    }
}
