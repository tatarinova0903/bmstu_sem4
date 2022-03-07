package com.example.lab3;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private final Text currMousePositionLabel = new Text("");
    private final Button cancelBtn = new Button("Отменить");
    private final Button cancelAllBtn = new Button("Сбросить");
    private final ResizableCanvas canvas = new ResizableCanvas(this);

    public MainController() {
        HBox aboutMenu = new HBox(aboutAuthorBtn, aboutProgramBtn, currMousePositionLabel);
        aboutMenu.setAlignment(Pos.CENTER);
        aboutMenu.setSpacing(10);
        aboutMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        HBox commonActionsMenu = new HBox(cancelBtn, cancelAllBtn, plusBtn, minusBtn);
        commonActionsMenu.setAlignment(Pos.CENTER);
        commonActionsMenu.setSpacing(10);
        commonActionsMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        VBox main = new VBox(aboutMenu, commonActionsMenu, canvas);
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

            }
            case LEFT -> canvas.goTo(Direction.LEFT);
            case RIGHT -> canvas.goTo(Direction.RIGHT);
            case UP -> canvas.goTo(Direction.UP);
            case DOWN -> canvas.goTo(Direction.DOWN);
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
        plusBtn.setOnAction(actionEvent -> {
            canvas.scale(true);
            canvas.requestFocus();
        });
        minusBtn.setOnAction(actionEvent -> {
            canvas.scale(false);
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
        showInfoAlert("Реализовать различные алгоритмы построения одиночных отрезков. Отрезок задается координатой начала, координатой конца и цветом.\n" +
                "\n" +
                "Сравнить визуальные характеристики отрезков, построенных разными алгоритмами, с помощью построения пучка отрезков, с заданным шагом.\n" +
                "\n" +
                "Сравнение со стандартным алгоритмом. Задаются начальные и конечные координаты; рисуется отрезок разными методами. Отрисовка отрезка другим цветом и методом поверх первого, для проверки совпадения. Предоставить пользователю возможность выбора двух цветов – цвета фона и цвета рисования. Алгоритмы выбирать из выпадающего списка.\n" +
                "\n" +
                "- ЦДА\n" +
                "- Брезенхем действительные числа\n" +
                "- Брезенхем целые числа\n" +
                "- Брезенхем с устранением ступенчатости\n" +
                "- ВУ\n" +
                "\n" +
                "Построение гистограмм по количеству ступенек в зависимости от угла наклона.");
    }

    private void aboutAuthorDidTap() {
        showInfoAlert("Татаринова Дарья ИУ7-44Б");
    }

    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }
}