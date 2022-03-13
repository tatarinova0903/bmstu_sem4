package com.example.lab3;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class MainController extends AnchorPane {
    private final Button aboutProgramBtn = new Button("О программе");
    private final Button aboutAuthorBtn = new Button("Об авторе");
    private final Button plusBtn = new Button("+");
    private final Button minusBtn = new Button("-");
    private final Text currMousePositionLabel = new Text("");
    private final Button cancelBtn = new Button("Отменить");
    private final Button cancelAllBtn = new Button("Сбросить");
    private final Text segmentColorLabel = new Text("Цвет отрезка:");
    private final ComboBox segmentColorComboBox = new ComboBox<>();
    private final Text backgroundColorLabel = new Text("Цвет фона:");
    private final ComboBox backgroundColorComboBox = new ComboBox<>();
    private final Text algoritmsColorLabel = new Text("Алгоритм:");
    private final ComboBox algoritmsComboBox = new ComboBox<>();
    private final Text startLabel = new Text("НАЧАЛО:");
    private final Text startXLabel = new Text("x:");
    private final TextField startXField = new TextField();
    private final Text startYLabel = new Text("y:");
    private final TextField startYField = new TextField();
    private final Text endLabel = new Text("КОНЕЦ:");
    private final Text endXLabel = new Text("x:");
    private final TextField endXField = new TextField();
    private final Text endYLabel = new Text("y:");
    private final TextField endYField = new TextField();
    private final Button drawBtn = new Button("Построить");
    private final ResizableCanvas canvas = new ResizableCanvas(this);

    private final CustomColor colors = new CustomColor();
    private final Algoritm algoritms = new Algoritm();

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

        colors.getColorsStr().forEach(color -> {
            backgroundColorComboBox.getItems().add(color);
            segmentColorComboBox.getItems().add(color);
        });
        algoritms.getAlgoritms().forEach(algoritm -> {
            algoritmsComboBox.getItems().add(algoritm);
        });
        backgroundColorComboBox.getSelectionModel().select(1);
        segmentColorComboBox.getSelectionModel().selectFirst();
        algoritmsComboBox.getSelectionModel().selectFirst();

        HBox segmentActionsMenu = new HBox(
                segmentColorLabel, segmentColorComboBox,
                backgroundColorLabel, backgroundColorComboBox,
                algoritmsColorLabel, algoritmsComboBox
        );
        segmentActionsMenu.setAlignment(Pos.CENTER);
        segmentActionsMenu.setSpacing(10);
        segmentActionsMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        startXField.setMaxWidth(60);
        startYField.setMaxWidth(60);
        endXField.setMaxWidth(60);
        endYField.setMaxWidth(60);
        HBox inputMenu = new HBox(
                startLabel, startXLabel, startXField, startYLabel, startYField,
                endLabel, endXLabel, endXField, endYLabel, endYField,
                drawBtn
        );
        inputMenu.setAlignment(Pos.CENTER);
        inputMenu.setSpacing(10);
        inputMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        VBox main = new VBox(aboutMenu, commonActionsMenu, segmentActionsMenu, inputMenu, canvas);
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
                if (startXField.focusedProperty().get()) { startYField.requestFocus(); }
                else if (startYField.isFocused()) { endXField.requestFocus(); }
                else if (endXField.isFocused()) { endYField.requestFocus(); }
                else if (endYField.isFocused()) { startXField.requestFocus(); }
            }
            case ENTER -> {
                if (!startXField.getText().isEmpty() && !startYField.getText().isEmpty() &&
                !endXField.getText().isEmpty() && !endYField.getText().isEmpty()) {
                    requestDrawBtn();
                }
            }
            case LEFT -> canvas.goTo(Direction.LEFT);
            case RIGHT -> canvas.goTo(Direction.RIGHT);
            case UP -> canvas.goTo(Direction.UP);
            case DOWN -> canvas.goTo(Direction.DOWN);
        }
    }

    private void addHandlers() {
        ArrayList<TextField> textFields = new ArrayList<>(Arrays.asList(startXField, startYField, endXField, endYField));
        textFields.forEach(textField -> {
            textField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    keyboardDidTap(event);
                }
            });
        });
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
            canvas.requestFocus();
        });
        cancelAllBtn.setOnAction(actionEvent -> {
            canvas.cancelAllBtnDidTap();
            canvas.requestFocus();
        });
        drawBtn.setOnAction(actionEvent -> {
            requestDrawBtn();
            canvas.requestFocus();
        });
    }

    private void requestDrawBtn() {
        if (startXField.getText().isEmpty() || startYField.getText().isEmpty() ||
                endXField.getText().isEmpty() || endYField.getText().isEmpty()) {
            return;
        }
        double startX = Double.parseDouble(startXField.getText());
        double startY = Double.parseDouble(startYField.getText());
        double endX = Double.parseDouble(endXField.getText());
        double endY = Double.parseDouble(endYField.getText());
        Color segmentColor = colors.getColors().get(segmentColorComboBox.getSelectionModel().getSelectedIndex());
        Color canvasColor = colors.getColors().get(backgroundColorComboBox.getSelectionModel().getSelectedIndex());
        AlgoritmType algoritm = algoritms.getAlgoritm(algoritmsComboBox.getSelectionModel().getSelectedItem().toString());
        canvas.drawBtnDidTap(new Point(startX, startY), new Point(endX, endY), algoritm, segmentColor, canvasColor);
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

    void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }
}