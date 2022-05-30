package com.example.lab11;

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
    private final Text clipLabel = new Text("ОТСЕКАТЕЛЬ:");
    private final Text xClipperLabel = new Text("X:");
    private final TextField xClipperField = new TextField();
    private final Text yClipperLabel = new Text("Y:");
    private final TextField yClipperField = new TextField();
    private final Button lockClipperBtn = new Button("Замкнуть");
    private final Button addPointToClipperBtn = new Button("Добавить точку в отсекатель");
    private final Text pointLabel = new Text("ФИГУРА:");
    private final Text xFigureLabel = new Text("X:");
    private final TextField xFigureField = new TextField();
    private final Text yFigureLabel = new Text("Y:");
    private final TextField yFigureField = new TextField();
    private final Button addPointToFigureBtn = new Button("Добавить точку в фигуру");
    private final Button lockFigureBtn = new Button("Замкнуть");
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

        xClipperField.setMaxWidth(60);
        yClipperField.setMaxWidth(60);
        HBox clipperMenu = new HBox(
                clipLabel,
                xClipperLabel, xClipperField,
                yClipperLabel, yClipperField,
                addPointToClipperBtn, lockClipperBtn
        );
        clipperMenu.setAlignment(Pos.CENTER);
        clipperMenu.setSpacing(10);

        clipperBtn.setToggleGroup(toggleSetGroup);
        figureBtn.setToggleGroup(toggleSetGroup);

        xFigureField.setMaxWidth(60);
        yFigureField.setMaxWidth(60);
        HBox figureMenu = new HBox(
                pointLabel,
                xFigureLabel, xFigureField,
                yFigureLabel, yFigureField,
                addPointToFigureBtn, lockFigureBtn
        );
        figureMenu.setAlignment(Pos.CENTER);
        figureMenu.setSpacing(10);

        HBox actionMenu = new HBox(
                resColorLabel, resColorComboBox,
                clipperBtn, figureBtn, clipBtn
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
                if (xClipperField.focusedProperty().get()) {
                    yClipperField.requestFocus();
                } else if (yClipperField.isFocused()) {
                    xClipperField.requestFocus();
                } else if (xFigureField.isFocused()) {
                    yFigureField.requestFocus();
                } else if (yFigureField.isFocused()) {
                    xFigureField.requestFocus();
                }
            }
            case ENTER -> {
                if (xFigureField.isFocused() || yFigureField.isFocused()) {
                    requestAddPointToFigureBtn();
                } else if (xClipperField.isFocused() || yClipperField.isFocused()) {
                    requestAddPointToClipperBtn();
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
                xClipperField, yClipperField,
                xFigureField, yFigureField
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
        addPointToClipperBtn.setOnAction(actionEvent -> {
            requestAddPointToClipperBtn();
            canvas.requestFocus();
        });
        addPointToFigureBtn.setOnAction(actionEvent -> {
            requestAddPointToFigureBtn();
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
        lockClipperBtn.setOnAction(actionEvent -> {
            canvas.lockClipperBtnDidTap();
            canvas.requestFocus();
        });
        lockFigureBtn.setOnAction(actionEvent -> {
            canvas.lockFigureBtnDidTap();
            canvas.requestFocus();
        });
    }

    private void requestAddPointToClipperBtn() {
        if (xClipperField.getText().isEmpty() || yClipperField.getText().isEmpty()) {
            return;
        }
        int x = Integer.parseInt(xClipperField.getText());
        int y = Integer.parseInt(yClipperField.getText());
        canvas.addPointToClipperBtnDidTap(x, y);
    }

    private void requestAddPointToFigureBtn() {
        if (xFigureField.getText().isEmpty() || yFigureField.getText().isEmpty()) {
            return;
        }
        int x = Integer.parseInt(xFigureField.getText());
        int y = Integer.parseInt(yFigureField.getText());
        canvas.addPointToFigureBtnDidTap(x, y);
    }

    private void aboutProgramBtnDidTap() {
        showInfoAlert("Алгоритм отсечения многоугольника выпуклым окном (алгоритм Сазерленда-Ходжмена)");
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
