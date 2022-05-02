package com.example.lab6;

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
    private final Text borderColorLabel = new Text("Цвет границы:");
    private final ComboBox borderColorComboBox = new ComboBox<>();
    private final Text colorLabel = new Text("Цвет заполнения:");
    private final ComboBox colorComboBox = new ComboBox<>();
    private final Text pointLabel = new Text("Точка:");
    private final Text xLabel = new Text("x:");
    private final TextField xField = new TextField();
    private final Text yLabel = new Text("y:");
    private final TextField yField = new TextField();
    private final Button addPointBtn = new Button("Добавить");
    private final Button lockBtn = new Button("Замкнуть");
    private final Button drawBtn = new Button("Закрасить");
    private final RadioButton timeRadioBtn = new RadioButton("С задержкой");
    private final TextField timeLabel = new TextField();
    private final ResizableCanvas canvas = new ResizableCanvas(this);

    private final CustomColor colors = new CustomColor();

    public MainController() {
        HBox commonActionsMenu = new HBox(cancelBtn, cancelAllBtn, plusBtn, minusBtn, aboutAuthorBtn, aboutProgramBtn, currMousePositionLabel);
        commonActionsMenu.setAlignment(Pos.CENTER);
        commonActionsMenu.setSpacing(10);

        colors.getColorsStr().forEach(color -> {
            borderColorComboBox.getItems().add(color);
            colorComboBox.getItems().add(color);
        });

        borderColorComboBox.getSelectionModel().selectFirst();
        colorComboBox.getSelectionModel().select(1);

        HBox colorsMenu = new HBox(
                borderColorLabel, borderColorComboBox,
                colorLabel, colorComboBox
        );
        colorsMenu.setAlignment(Pos.CENTER);
        colorsMenu.setSpacing(10);

        xField.setMaxWidth(60);
        yField.setMaxWidth(60);
        timeLabel.setMaxWidth(35);
        HBox figureMenu = new HBox(pointLabel, xLabel, xField, yLabel, yField, addPointBtn, lockBtn, timeRadioBtn, drawBtn, timeLabel);
        figureMenu.setSpacing(10);

        HBox inputMenu = new HBox(colorsMenu, figureMenu);
        inputMenu.setSpacing(40);

        VBox main = new VBox(commonActionsMenu, colorsMenu, figureMenu, inputMenu, canvas);
        main.setSpacing(5);
        configure(main);
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
                if (xField.focusedProperty().get()) { yField.requestFocus(); }
                else if (yField.isFocused()) { xField.requestFocus(); }
            }
            case ENTER -> {
                if (xField.isFocused() || yField.isFocused()) {
                    requestAddPointBtn();
                }
            }
            case LEFT -> canvas.goTo(Direction.LEFT);
            case RIGHT -> canvas.goTo(Direction.RIGHT);
            case UP -> canvas.goTo(Direction.UP);
            case DOWN -> canvas.goTo(Direction.DOWN);
        }
    }

    Color getBorderColor() {
        return colors.getColors().get(borderColorComboBox.getSelectionModel().getSelectedIndex());
    }

    Color getFigureColor() {
        return colors.getColors().get(colorComboBox.getSelectionModel().getSelectedIndex());
    }

    boolean isWithoutTimeSleep() {
        return !timeRadioBtn.isSelected();
    }

    public void setTime(long time) {
        timeLabel.setText(Long.toString(time) + "мс");
    }

    private void addHandlers() {
        ArrayList<TextField> textFields = new ArrayList<>(Arrays.asList(xField, yField));
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
        addPointBtn.setOnAction(actionEvent -> {
            requestAddPointBtn();
            canvas.requestFocus();
        });
        lockBtn.setOnAction(actionEvent -> {
            requestAddPointBtn();
            Color borderColor = colors.getColors().get(borderColorComboBox.getSelectionModel().getSelectedIndex());
            canvas.lockFigureBtnDidTap(borderColor);
        });
    }

    private void requestDrawBtn() {
        canvas.fillBtnDidTap();
    }

    private void requestAddPointBtn() {
        if (xField.getText().isEmpty() || yField.getText().isEmpty()) {
            return;
        }
        int xCoord = Integer.parseInt(xField.getText());
        int yCoord = Integer.parseInt(yField.getText());
        Color figureColor = colors.getColors().get(colorComboBox.getSelectionModel().getSelectedIndex());
        canvas.addPointBtnDidTap(xCoord, yCoord, figureColor);
    }

    private void aboutProgramBtnDidTap() {
        showInfoAlert("Реализация и исследование  алгоритма построчного затравочного заполнения");
    }

    private void configure(HBox box) {
        box.setAlignment(Pos.CENTER);
        box.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
            if (element instanceof HBox) {
                configure((HBox) element);
            }
            if (element instanceof VBox) {
                configure((VBox) element);
            }
        });
    }

    private void configure(VBox box) {
        box.setAlignment(Pos.CENTER);
        box.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
            if (element instanceof VBox) {
                configure((VBox) element);
            }
            if (element instanceof HBox) {
                configure((HBox) element);
            }
        });
    }

    private void aboutAuthorDidTap() {
        showInfoAlert("Татаринова Дарья ИУ7-44Б");
    }

    void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }
}