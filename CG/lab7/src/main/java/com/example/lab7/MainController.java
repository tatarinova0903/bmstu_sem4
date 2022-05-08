package com.example.lab7;

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
    private final Text clipLabel = new Text("Отсекатель:");
    private final Text xLeftLabel = new Text("Xл:");
    private final TextField xLeftField = new TextField();
    private final Text xRightLabel = new Text("Xп:");
    private final TextField xRightField = new TextField();
    private final Text yUpLabel = new Text("Yв:");
    private final TextField yUpField = new TextField();
    private final Text yDownLabel = new Text("Yн:");
    private final TextField yDownField = new TextField();
    private final Button drawClipperBtn = new Button("Нарисовать отсекатель");
    private final Text pointLabel = new Text("Точка:");
    private final Text xLabel = new Text("x:");
    private final TextField xField = new TextField();
    private final Text yLabel = new Text("y:");
    private final TextField yField = new TextField();
    private final Button addPointBtn = new Button("Добавить точку");
    private final Button clipBtn = new Button("Обрезать");
    private final ToggleGroup toggleSetGroup = new ToggleGroup();
    private final ToggleButton clipperBtn = new ToggleButton("Отсекатель");
    private final ToggleButton figureBtn = new ToggleButton("Фигура");
    private final ResizableCanvas canvas = new ResizableCanvas(this);

    private final CustomColor colors = new CustomColor();

    public MainController() {
        HBox commonActionsMenu = new HBox(cancelBtn, cancelAllBtn, plusBtn, minusBtn, aboutAuthorBtn, aboutProgramBtn, currMousePositionLabel);
        commonActionsMenu.setAlignment(Pos.CENTER);
        commonActionsMenu.setSpacing(10);

        colors.getColorsStr().forEach(color -> {
            resColorComboBox.getItems().add(color);
        });
        resColorComboBox.getSelectionModel().selectFirst();

        xLeftField.setMaxWidth(60);
        xRightField.setMaxWidth(60);
        yUpField.setMaxWidth(60);
        yDownField.setMaxWidth(60);
        HBox clipperMenu = new HBox(
                resColorLabel, resColorComboBox,
                clipLabel,
                xLeftLabel, xLeftField,
                xRightLabel, xRightField,
                yDownLabel, yDownField,
                yUpLabel, yUpField,
                drawClipperBtn
        );
        clipperMenu.setAlignment(Pos.CENTER);
        clipperMenu.setSpacing(10);

        clipperBtn.setToggleGroup(toggleSetGroup);
        figureBtn.setToggleGroup(toggleSetGroup);

        HBox figureMenu = new HBox(
                pointLabel,
                xLabel, xField,
                yLabel, yField,
                addPointBtn, clipBtn
        );
        figureMenu.setAlignment(Pos.CENTER);
        figureMenu.setSpacing(10);

        HBox actionMenu = new HBox(
                clipperBtn, figureBtn
        );
        actionMenu.setAlignment(Pos.CENTER);
        actionMenu.setSpacing(10);

        VBox main = new VBox(commonActionsMenu, clipperMenu, figureMenu, actionMenu, canvas);
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
                if (xLeftField.focusedProperty().get()) { xRightField.requestFocus(); }
                else if (xRightField.isFocused()) { yDownField.requestFocus(); }
                else if (yDownField.isFocused()) { yUpField.requestFocus(); }
                else if (yUpField.isFocused()) { xLeftField.requestFocus(); }
                else if (xField.isFocused()) { yField.requestFocus(); }
                else if (yField.isFocused()) { xField.requestFocus(); }
            }
            case ENTER -> {
                if (xField.isFocused() || yField.isFocused()) {
                    requestAddPointBtn();
                }
                else if (xLeftField.isFocused() || xRightField.isFocused() ||
                            yUpField.isFocused() || yDownField.isFocused()) {
                    requestDrawClipperBtn();
                }
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
                xLeftField, xRightField,
                yUpField, yDownField,
                xField, yField
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
        drawClipperBtn.setOnAction(actionEvent -> {
            requestDrawClipperBtn();
            canvas.requestFocus();
        });
        addPointBtn.setOnAction(actionEvent -> {
            requestAddPointBtn();
            canvas.requestFocus();
        });
        clipperBtn.setOnAction(actionEvent -> {
            canvas.clipperBtnDidTap();
            canvas.requestFocus();
        });
        figureBtn.setOnAction(actionEvent -> {
            canvas.figureBtnDidTap();
            canvas.requestFocus();
        });
        clipBtn.setOnAction(actionEvent -> {
            canvas.clipBtnDidTap();
            canvas.requestFocus();
        });
    }

    private void requestDrawClipperBtn() {
        if (xLeftField.getText().isEmpty() || xRightField.getText().isEmpty() ||
                yUpField.getText().isEmpty() || yDownField.getText().isEmpty()) {
            return;
        }
        int xLeft = Integer.parseInt(xLeftField.getText());
        int xRight = Integer.parseInt(xRightField.getText());
        int yUp = Integer.parseInt(yUpField.getText());
        int yDown = Integer.parseInt(yDownField.getText());
        canvas.drawClipperBtnDidTap(xLeft, xRight, yUp, yDown);
    }

    private void requestAddPointBtn() {
        if (xField.getText().isEmpty() || yField.getText().isEmpty()) {
            return;
        }
        int x = Integer.parseInt(xField.getText());
        int y = Integer.parseInt(yField.getText());
        canvas.addPointBtnDidTap(x, y);
    }

    private void aboutProgramBtnDidTap() {
        showInfoAlert("Простой алгоритм отсечения отрезка");
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