package com.example.lab10;

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
    private final Text resColorLabel = new Text("Цвет результата:");
    private final ComboBox resColorComboBox = new ComboBox<>();
    private final Text functionTypeLabel = new Text("Функция:");
    private final ComboBox functionTypeComboBox = new ComboBox<>();
    private final Text limitsLabel = new Text("ПРЕДЕЛЫ");
    private final Text xLimitsLabel = new Text("X:");
    private final Text xStartLabel = new Text("от: ");
    private final TextField xStartField = new TextField();
    private final Text xEndLabel = new Text("до: ");
    private final TextField xEndField = new TextField();
    private final Text xStepLabel = new Text("шаг:");
    private final TextField xStepField = new TextField();
    private final Text zLimitsLabel = new Text("Z:");
    private final Text zStartLabel = new Text("от: ");
    private final TextField zStartField = new TextField();
    private final Text zEndLabel = new Text("до: ");
    private final TextField zEndField = new TextField();
    private final Text zStepLabel = new Text("шаг:");
    private final TextField zStepField = new TextField();
    private final Text rotateLabel = new Text("ВРАЩЕНИЕ");
    private final Text xRotateLabel = new Text("OX:");
    private final TextField xRotateField = new TextField();
    private final Text yRotateLabel = new Text("OY:");
    private final TextField yRotateField = new TextField();
    private final Text zRotateLabel = new Text("OZ:");
    private final TextField zRotateField = new TextField();
    private final Text scaleLabel = new Text("МАСШТАБИРОВАНИЕ");
    private final Text kScaleLabel = new Text("K:");
    private final TextField kScaleField = new TextField();
    private final Button addPointToFigureBtn = new Button("Добавить точку в фигуру");
    private final Button lockFigureBtn = new Button("Замкнуть");
    private final Button drawBtn = new Button("Нарисовать");
    private final ToggleGroup toggleSetGroup = new ToggleGroup();
    private final ResizableCanvas canvas = new ResizableCanvas(this);

    private final CustomColor colors = new CustomColor();
    private final Functions functions = new Functions();

    public MainController() {
        HBox commonActionsMenu = new HBox(cancelBtn, cancelAllBtn, plusBtn, minusBtn, aboutAuthorBtn, aboutProgramBtn, currMousePositionLabel);
        commonActionsMenu.setAlignment(Pos.CENTER);
        commonActionsMenu.setSpacing(10);

        colors.getColorsStr().forEach(color -> {
            resColorComboBox.getItems().add(color);
        });
        resColorComboBox.getSelectionModel().selectFirst();

        functions.getFunctionsStr().forEach(functionType -> {
            functionTypeComboBox.getItems().add(functionType);
        });
        functionTypeComboBox.getSelectionModel().selectFirst();

        xStartField.setMaxWidth(60);
        xEndField.setMaxWidth(60);
        xStepField.setMaxWidth(60);
        zStartField.setMaxWidth(60);
        zEndField.setMaxWidth(60);
        zStepField.setMaxWidth(60);
        HBox limitsMenu = new HBox(
                limitsLabel, xLimitsLabel,
                xStartLabel, xStartField, xEndLabel, xEndField, xStepLabel, xStepField,
                zLimitsLabel, zStartLabel, zStartField,
                zEndLabel, zEndField, zStepLabel, zStepField
        );
        limitsMenu.setAlignment(Pos.CENTER);
        limitsMenu.setSpacing(10);

        HBox rotateMenu = new HBox(
                rotateLabel,
                xRotateLabel, xRotateField,
                yRotateLabel, yRotateField,
                zRotateLabel, zRotateField
        );
        rotateMenu.setAlignment(Pos.CENTER);
        rotateMenu.setSpacing(10);

        kScaleField.setMaxWidth(60);
        HBox scaleMenu = new HBox(
                scaleLabel, kScaleLabel, kScaleField
        );
        scaleMenu.setAlignment(Pos.CENTER);
        scaleMenu.setSpacing(10);

        HBox actionMenu = new HBox(
                resColorLabel, resColorComboBox,
                functionTypeLabel, functionTypeComboBox,
                drawBtn
        );
        actionMenu.setAlignment(Pos.CENTER);
        actionMenu.setSpacing(10);

        VBox main = new VBox(commonActionsMenu, limitsMenu, rotateMenu, scaleMenu, actionMenu, canvas);
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
                if (xStartField.focusedProperty().get()) { xEndField.requestFocus(); }
                else if (xEndField.isFocused()) { zStartField.requestFocus(); }
                else if (zStartField.isFocused()) { zEndField.requestFocus(); }
            }
            case ENTER -> {

            }
            case LEFT -> canvas.goTo(Direction.LEFT);
            case RIGHT -> canvas.goTo(Direction.RIGHT);
            case UP -> canvas.goTo(Direction.UP);
            case DOWN -> canvas.goTo(Direction.DOWN);
        }
    }

    Color getResColor() {
        return colors.getColors().get(resColorComboBox.getSelectionModel().getSelectedIndex());
    }

    private void addHandlers() {
        ArrayList<TextField> textFields = new ArrayList<>(Arrays.asList(
                xStartField, xEndField, zStartField, zEndField, xStepField, zStepField
        ));
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
            requestDrawBtnDidTap();
            canvas.requestFocus();
        });
    }

    private void requestDrawBtnDidTap() {

    }

    private void aboutProgramBtnDidTap() {
        showInfoAlert("Алгоритм плавающего горизонта");
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