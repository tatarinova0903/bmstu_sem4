package com.example.lab4;

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
    private final Text segmentColorLabel = new Text("Цвет эллипса:");
    private final ComboBox ovalColorComboBox = new ComboBox<>();
    private final Text backgroundColorLabel = new Text("Цвет фона:");
    private final ComboBox backgroundColorComboBox = new ComboBox<>();
    private final Text algoritmsColorLabel = new Text("Алгоритм:");
    private final ComboBox algoritmsComboBox = new ComboBox<>();
    private final Text centerLabel = new Text("Центр:");
    private final Text centerXLabel = new Text("x:");
    private final TextField centerXField = new TextField();
    private final Text centerYLabel = new Text("y:");
    private final TextField centerYField = new TextField();
    private final Text radiusLabel = new Text("ПОЛУОСИ:");
    private final Text xAxisLabel = new Text("x:");
    private final TextField xAxisField = new TextField();
    private final Text yAxisLabel = new Text("y:");
    private final TextField yAxisField = new TextField();
    private final Button drawBtn = new Button("Построить эллипс");
    private final Text lengthLabel = new Text("ДЛИНА:");
    private final TextField lengthField = new TextField();
    private final Text stepLabel = new Text("ШАГ:");
    private final TextField stepField = new TextField();
    private final Button drawPuchokBtn = new Button("Построить спектр");
    private final Button drawChartBtn = new Button("Построить диаграмму");
    private final ResizableCanvas canvas = new ResizableCanvas(this);

    private final CustomColor colors = new CustomColor();
    private final Algoritm algoritms = new Algoritm();

    public MainController() {
        HBox commonActionsMenu = new HBox(cancelBtn, cancelAllBtn, plusBtn, minusBtn, aboutAuthorBtn, aboutProgramBtn, currMousePositionLabel);
        commonActionsMenu.setAlignment(Pos.CENTER);
        commonActionsMenu.setSpacing(10);
        commonActionsMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        colors.getColorsStr().forEach(color -> {
            backgroundColorComboBox.getItems().add(color);
            ovalColorComboBox.getItems().add(color);
        });
        algoritms.getAlgoritms().forEach(algoritm -> {
            algoritmsComboBox.getItems().add(algoritm);
        });
        backgroundColorComboBox.getSelectionModel().select(1);
        ovalColorComboBox.getSelectionModel().selectFirst();
        algoritmsComboBox.getSelectionModel().selectFirst();

        HBox segmentActionsMenu = new HBox(
                segmentColorLabel, ovalColorComboBox,
                backgroundColorLabel, backgroundColorComboBox,
                algoritmsColorLabel, algoritmsComboBox
        );
        segmentActionsMenu.setAlignment(Pos.CENTER);
        segmentActionsMenu.setSpacing(10);
        segmentActionsMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        centerXField.setMaxWidth(60);
        centerYField.setMaxWidth(60);
        xAxisField.setMaxWidth(60);
        yAxisField.setMaxWidth(60);
        HBox inputMenu = new HBox(
                centerLabel, centerXLabel, centerXField, centerYLabel, centerYField,
                radiusLabel, xAxisLabel, xAxisField, yAxisLabel, yAxisField,
                drawBtn
        );
        inputMenu.setAlignment(Pos.CENTER);
        inputMenu.setSpacing(10);
        inputMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        lengthField.setMaxWidth(60);
        stepField.setMaxWidth(60);
        HBox puchokMenu = new HBox(
                lengthLabel, lengthField,
                stepLabel, stepField,
                drawPuchokBtn, drawChartBtn
        );
        puchokMenu.setAlignment(Pos.CENTER);
        puchokMenu.setSpacing(10);
        puchokMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        VBox main = new VBox(commonActionsMenu, segmentActionsMenu, inputMenu, puchokMenu, canvas);
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
                if (centerXField.focusedProperty().get()) { centerYField.requestFocus(); }
                else if (centerYField.isFocused()) { xAxisField.requestFocus(); }
                else if (xAxisField.isFocused()) { yAxisField.requestFocus(); }
                else if (yAxisField.isFocused()) { centerXField.requestFocus(); }
                else if (lengthField.isFocused()) { stepField.requestFocus(); }
                else if (stepField.isFocused()) { lengthField.requestFocus(); }
            }
            case ENTER -> {
                if (centerXField.isFocused() || centerYField.isFocused() ||
                        xAxisField.isFocused() || yAxisField.isFocused()) {
                    requestDrawBtn();
                }
                else if (lengthField.isFocused() || stepField.isFocused()) {
                    requestDrawPuchokBtn();
                }
            }
            case LEFT -> canvas.goTo(Direction.LEFT);
            case RIGHT -> canvas.goTo(Direction.RIGHT);
            case UP -> canvas.goTo(Direction.UP);
            case DOWN -> canvas.goTo(Direction.DOWN);
        }
    }

    private void addHandlers() {
        ArrayList<TextField> textFields = new ArrayList<>(Arrays.asList(centerXField, centerYField, xAxisField, yAxisField, lengthField, stepField));
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
        drawPuchokBtn.setOnAction(actionEvent -> {
            requestDrawPuchokBtn();
            canvas.requestFocus();
        });
        drawChartBtn.setOnAction(actionEvent -> {
            if (lengthField.getText().isEmpty() || stepField.getText().isEmpty()) {
                return;
            }
            int length = Integer.parseInt(lengthField.getText());
            double step = Double.parseDouble(stepField.getText());
            AlgoritmType algoritm = algoritms.getAlgoritm(algoritmsComboBox.getSelectionModel().getSelectedItem().toString());
            canvas.drawChartBtnDidTap(length, step, algoritm);
            canvas.requestFocus();
        });
    }

    private void requestDrawBtn() {
        if (centerXField.getText().isEmpty() || centerYField.getText().isEmpty() ||
                xAxisField.getText().isEmpty() || yAxisField.getText().isEmpty()) {
            return;
        }
        double centerX = Double.parseDouble(centerXField.getText());
        double centerY = Double.parseDouble(centerYField.getText());
        double xAxis = Double.parseDouble(xAxisField.getText());
        double yAxis = Double.parseDouble(yAxisField.getText());
        Color segmentColor = colors.getColors().get(ovalColorComboBox.getSelectionModel().getSelectedIndex());
        Color canvasColor = colors.getColors().get(backgroundColorComboBox.getSelectionModel().getSelectedIndex());
        AlgoritmType algoritm = algoritms.getAlgoritm(algoritmsComboBox.getSelectionModel().getSelectedItem().toString());
        canvas.drawBtnDidTap(new Point(centerX, centerY), xAxis, yAxis, algoritm, segmentColor, canvasColor);
    }

    private void requestDrawPuchokBtn() {
        if (lengthField.getText().isEmpty() || stepField.getText().isEmpty()) {
            return;
        }
        int length = Integer.parseInt(lengthField.getText());
        double step = Math.toRadians(Double.parseDouble(stepField.getText()));
        Color segmentColor = colors.getColors().get(ovalColorComboBox.getSelectionModel().getSelectedIndex());
        Color canvasColor = colors.getColors().get(backgroundColorComboBox.getSelectionModel().getSelectedIndex());
        AlgoritmType algoritm = algoritms.getAlgoritm(algoritmsComboBox.getSelectionModel().getSelectedItem().toString());
//        canvas.drawPuchokBtnDidTap(length, step, algoritm, segmentColor, canvasColor);
    }

    private void aboutProgramBtnDidTap() {
        showInfoAlert("Реализовать различные алгоритмы построения одиночных эллипсов. Эллипс задается координатой центра и двумя полуосями.\n" +
                "\n" +
                "Сравнить визуальные характеристики эллипсов, построенных разными алгоритмами, с помощью построения спектра с заданным шагом.\n" +
                "\n" +
                "- Каноническое уравнение\n" +
                "- Параметрическое уравнение\n" +
                "- алгоритм Брезенхема\n" +
                "- алгоритм средней точки\n" +
                "\n" +
                "Построение гистограмм по затраченному времени.");
    }

    private void aboutAuthorDidTap() {
        showInfoAlert("Татаринова Дарья ИУ7-44Б");
    }

    void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }
}