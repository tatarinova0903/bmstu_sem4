package com.example.lab1;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class ResizableCanvas extends Canvas {
    private final GraphicsContext gc = getGraphicsContext2D();
    private double oldWidth = getWidth();
    private double oldHeight = getHeight();
    private final MainModel model = new MainModel();

    public ResizableCanvas() {
        model.setCurrent_set(SetNumber.NONE);
        setOnMouseClicked(this::onMouseClicked);
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    private void draw() {
        double width = getWidth();
        double height = getHeight();
        if (width == 0 || height == 0) { return; }
        double deltaWidth = width / (oldWidth == 0 ? width : oldWidth);
        double deltaHeight = height / (oldHeight == 0 ? height : oldHeight);
        gc.clearRect(0, 0, width, height);

        if (!model.getSet1().isEmpty()) { model.setCurrent_set(SetNumber.FIRST); }
        model.getSet1().forEach(point -> {
            scalePoint(point, deltaWidth, deltaHeight);
            drawPoint(point);
        });
        if (!model.getSet2().isEmpty()) { model.setCurrent_set(SetNumber.SECOND);}
        model.getSet2().forEach(point -> {
            scalePoint(point, deltaWidth, deltaHeight);
            drawPoint(point);
        });

        // scale коружности
//        drawCircle(model.getCircle());

        // scale овала
        Oval oval = model.getOval();
        scaleOval(oval, deltaWidth, deltaHeight);
        drawOval(model.getOval());

        oldWidth = width;
        oldHeight = height;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

    void scale(Boolean isPlus) {
        if (isPlus) { model.incrementCurrScale(); }
        else { model.decrementCurrScale(); }
        this.setScaleX(model.getCurrScale());
        this.setScaleY(model.getCurrScale());
        double newWidth = isPlus ? getWidth() : getWidth() * (2 - model.getCurrScale());
        double newX = newXForScale(model.getCurrScale());
        double newHeight = isPlus ? getHeight() : getHeight() * (2 - model.getCurrScale());
        double newY = newYForScale(model.getCurrScale());
        Rectangle rect = new Rectangle(newX, newY, newWidth, newHeight);
        this.setClip(rect);
    }

    void inputFirstSetBtnDidTap(ActionEvent event) {
        model.setCurrent_set(SetNumber.FIRST);
    }

    void inputSecondSetBtnDidTap(ActionEvent event) {
        model.setCurrent_set(SetNumber.SECOND);
    }

    void editBtnDidTap(SetNumber setNumber) {
        model.setCurrent_set(setNumber);
        model.setIsEditing(EditingMode.POINT_CHOSEN);
    }

    void cancelBtnDidTap() {
        switch (model.getLastAction()) {
            case NONE:
                break;
            case ADD_POINT:
                model.removePoint();
            case DRAW_CIRCLE:
                model.setCircle(new Circle());
                break;
            case EDIT_POINT:
                model.removePoint();
                model.addToSet(model.getEditedPoint());
                break;
        }
        draw();
        model.setCurrent_set(SetNumber.NONE);
    }

    void calculateBtnDidTap() {
        model.calculateBtnDidTap();
        if (!model.getCircle().isZero()) {
            model.setLastAction(LastAction.DRAW_CIRCLE);
        }
        draw();
    }

    void addPoint(double x, double y, SetNumber setNumber) {
        model.setCurrent_set(setNumber);
        Point point = new Point(x, y);
        drawPoint(point);
        model.addToSet(point);
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

    private void onMouseClicked(MouseEvent event) {
        if (model.getIsEditing() == EditingMode.POINT_CHOSEN) {
            Point point = model.findClosestAndRemove(new Point(event.getX(), event.getY()));
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
            model.setIsEditing(EditingMode.POINT_SET);
            return;
        }
        if (model.getIsEditing() == EditingMode.POINT_SET) {
            addPoint(event.getX(), event.getY(), model.getSetToEdit());
            model.setIsEditing(EditingMode.NONE);
            model.setSetToEdit(SetNumber.NONE);
            draw();
            return;
        }
        model.setLastAction(LastAction.ADD_POINT);
        addPoint(event.getX(), event.getY(), model.getCurrent_set());
    }

    private void drawCircle(Circle circle) {
        gc.setFill(Color.rgb(0, 128, 0, 0.3));
        double radius = circle.getRadius();
        double diameter = circle.getRadius() * 2;
        gc.fillOval(circle.getCenter().getX() - radius, circle.getCenter().getY() - radius, diameter, diameter);
    }

    private void drawPoint(Point point) {
        switch (model.getCurrent_set()) {
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

    static class Constants {
        static double pointDiameter = 6.0;
        static double pointRadius = Constants.pointDiameter / 2;
    }
}
