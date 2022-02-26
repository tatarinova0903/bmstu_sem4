package com.example.lab2;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainController extends AnchorPane {
    private final Button aboutProgramBtn = new Button("О программе");
    private final Button aboutAuthorBtn = new Button("Об авторе");
    private final Button moveBtn = new Button("Переместить");
    private final Text moveXLabel = new Text("x:");
    private final TextField moveXField = new TextField();
    private final Text moveYLabel = new Text("y:");
    private final TextField moveYField = new TextField();
    private final Button scaleBtn = new Button("Масштабировать");
    private final TextField scaleFactorField = new TextField();
    private final Button rotateBtn = new Button("Повернуть");
    private final Text rotateDegreeLabel = new Text("°");
    private final TextField rotateDegreeField = new TextField();
    private final Button cancelBtn = new Button("Отменить");
    private final ResizableCanvas canvas = new ResizableCanvas(this);

    public MainController() {
        HBox aboutMenu = new HBox(aboutAuthorBtn, aboutProgramBtn);
        aboutMenu.setAlignment(Pos.CENTER);
        aboutMenu.setSpacing(10);
        aboutMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        HBox moveBox = new HBox(moveXLabel, moveXField, moveYLabel, moveYField, moveBtn);

        moveBox.setAlignment(Pos.CENTER);
        moveBox.setSpacing(10);
        moveBox.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        HBox scaleBox = new HBox(scaleFactorField, scaleBtn);
        scaleBox.setAlignment(Pos.CENTER);
        scaleBox.setSpacing(10);
        scaleBox.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        HBox rotateBox = new HBox(rotateDegreeField, rotateDegreeLabel, rotateBtn);
        rotateBox.setAlignment(Pos.CENTER);
        rotateBox.setSpacing(10);
        rotateBox.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        HBox scaleRotateBox = new HBox(scaleBox, rotateBox);
        scaleRotateBox.setAlignment(Pos.CENTER);
        scaleRotateBox.setSpacing(15);
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

    void keyboardDidTap(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {

        }
    }

    private void addHandlers() {
        aboutAuthorBtn.setOnAction(actionEvent -> { aboutAuthorDidTap(); });
        aboutProgramBtn.setOnAction(actionEvent -> { aboutProgramBtnDidTap(); });
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