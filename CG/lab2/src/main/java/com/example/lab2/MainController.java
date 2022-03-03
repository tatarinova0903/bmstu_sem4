package com.example.lab2;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainController extends AnchorPane {
    private final Button aboutProgramBtn = new Button("О программе");
    private final Button aboutAuthorBtn = new Button("Об авторе");
    private final Button plusBtn = new Button("+");
    private final Button minusBtn = new Button("-");
    private final Button moveBtn = new Button("Переместить");
    private final Text moveXLabel = new Text("dx:");
    private final TextField moveXField = new TextField();
    private final Text moveYLabel = new Text("dy:");
    private final TextField moveYField = new TextField();
    private final Button scaleBtn = new Button("Масштабировать");
    private final Text scaleXLabel = new Text("kx:");
    private final TextField scaleFactorXField = new TextField();
    private final Text scaleYLabel = new Text("ky:");
    private final TextField scaleFactorYField = new TextField();
    private final Button rotateBtn = new Button("Повернуть");
    private final Text rotateXLabel = new Text("x:");
    private final TextField rotateXField = new TextField();
    private final Text rotateYLabel = new Text("y:");
    private final TextField rotateYField = new TextField();
    private final Text rotateDegreeLabel = new Text("°");
    private final TextField rotateDegreeField = new TextField();
    private final Button cancelBtn = new Button("Отменить");
    private final Text currMousePositionLabel = new Text("");
    private final Button cancelAllBtn = new Button("Сбросить");
    private final ResizableCanvas canvas = new ResizableCanvas(this);

    public MainController() {
        HBox aboutMenu = new HBox(aboutAuthorBtn, aboutProgramBtn, currMousePositionLabel, rotateXLabel, rotateXField, rotateYLabel, rotateYField);
        aboutMenu.setAlignment(Pos.CENTER);
        aboutMenu.setSpacing(10);
        aboutMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        HBox moveBox = new HBox(cancelBtn, cancelAllBtn, minusBtn, plusBtn, moveXLabel, moveXField, moveYLabel, moveYField, moveBtn);
        moveXField.setMaxWidth(60);
        moveYField.setMaxWidth(60);
        moveBox.setAlignment(Pos.CENTER);
        moveBox.setSpacing(10);
        moveBox.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        HBox scaleBox = new HBox(scaleXLabel, scaleFactorXField, scaleYLabel, scaleFactorYField, scaleBtn);
        scaleFactorXField.setMaxWidth(60);
        scaleFactorYField.setMaxWidth(60);
        scaleBox.setAlignment(Pos.CENTER);
        scaleBox.setSpacing(5);
        scaleBox.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        HBox rotateBox = new HBox(rotateDegreeField, rotateDegreeLabel, rotateBtn);
        rotateXField.setMaxWidth(60);
        rotateYField.setMaxWidth(60);
        rotateDegreeField.setMaxWidth(60);
        rotateBox.setAlignment(Pos.CENTER);
        rotateBox.setSpacing(5);
        rotateBox.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        HBox scaleRotateBox = new HBox(scaleBox, rotateBox);
        scaleRotateBox.setAlignment(Pos.CENTER);
        scaleRotateBox.setSpacing(20);
        scaleRotateBox.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        VBox main = new VBox(aboutMenu, moveBox, scaleRotateBox, canvas);
        main.setSpacing(5);
        this.getChildren().add(main);

        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());

        addHandlers();
    }

    void setCurrentMousePosition(double x, double y) {
        currMousePositionLabel.setText(String.format("x:%.0f y:%.0f", x, y));
    }

    void keyboardDidTap(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case TAB -> {
                if (moveXField.focusedProperty().get()) { moveYField.requestFocus(); }
                else if (moveYField.focusedProperty().get()) { moveXField.requestFocus(); }
                else if (scaleFactorXField.focusedProperty().get()) { scaleFactorYField.requestFocus(); }
                else if (scaleFactorYField.focusedProperty().get()) { scaleFactorXField.requestFocus(); }
            }
            case LEFT -> canvas.moveBtnDidTap(-10, 0);
            case RIGHT -> canvas.moveBtnDidTap(10, 0);
            case UP -> canvas.moveBtnDidTap(0, 10);
            case DOWN -> canvas.moveBtnDidTap(0, -10);
//            case ENTER -> {
//                if (moveXField.focusedProperty().get() || moveYField.focusedProperty().get()) {
//                    if (!moveXField.getText().isEmpty() && !moveYField.getText().isEmpty()) {
//                        double dx = Double.parseDouble(moveXField.getText());
//                        double dy = Double.parseDouble(moveYField.getText());
//                        canvas.moveBtnDidTap(dx, dy);
//                        canvas.requestFocus();
//                    }
//                }
//                else if (scaleFactorXField.focusedProperty().get() || scaleFactorYField.focusedProperty().get()) {
//                    if (!scaleFactorXField.getText().isEmpty() && !scaleFactorYField.getText().isEmpty()) {
//                        double dx = Double.parseDouble(scaleFactorXField.getText());
//                        double dy = Double.parseDouble(scaleFactorYField.getText());
//                        canvas.scaleBtnDidTap(dx, dy, );
//                        canvas.requestFocus();
//                    }
//                }
//            }
        }
    }

    private void addHandlers() {
        aboutAuthorBtn.setOnAction(actionEvent -> {
            aboutAuthorDidTap();
            canvas.requestFocus();
        });
        aboutProgramBtn.setOnAction(actionEvent -> {
            aboutProgramBtnDidTap();
            canvas.requestFocus();
        });
        moveBtn.setOnAction(actionEvent -> {
            if (moveXField.getText().isEmpty() || moveYField.getText().isEmpty()) { return; }
            double dx = Double.parseDouble(moveXField.getText());
            double dy = Double.parseDouble(moveYField.getText());
            canvas.moveBtnDidTap(dx, dy);
            canvas.requestFocus();
        });
        rotateBtn.setOnAction(actionEvent -> {
            if (rotateDegreeField.getText().isEmpty()) { return; }
            double degree = Double.parseDouble(rotateDegreeField.getText());
            double x = Double.parseDouble(rotateXField.getText());
            double y = Double.parseDouble(rotateYField.getText());
            canvas.rotateBtnDidTap(x, y, degree);
            canvas.requestFocus();
        });
        scaleBtn.setOnAction(actionEvent -> {
            if (scaleFactorXField.getText().isEmpty()) { return; }
            double dx = Double.parseDouble(scaleFactorXField.getText());
            double dy = Double.parseDouble(scaleFactorYField.getText());
            double x = Double.parseDouble(rotateXField.getText());
            double y = Double.parseDouble(rotateYField.getText());
            canvas.scaleBtnDidTap(dx, dy, x, y);
            canvas.requestFocus();
        });
        addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) { keyboardDidTap(ke); }
        });
        plusBtn.setOnAction(actionEvent -> {
            canvas.scaleBtnDidTap(1.1, 1.1, 0, 0);
            canvas.requestFocus();
        });
        minusBtn.setOnAction(actionEvent -> {
            canvas.scaleBtnDidTap(0.9, 0.9, 0, 0);
            canvas.requestFocus();
        });
        cancelBtn.setOnAction(actionEvent -> {
            canvas.cancelBtnDidTap();
        });
        cancelAllBtn.setOnAction(actionEvent -> {
            canvas.cancelAllBtnDidTap();
        });
    }

    private void aboutProgramBtnDidTap() {
        showInfoAlert("Нарисовать исходный рисунок, затем его переместить, промасштабировать, повернуть.");
    }

    private void aboutAuthorDidTap() {
        showInfoAlert("Татаринова Дарья ИУ7-44Б");
    }

    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }
}