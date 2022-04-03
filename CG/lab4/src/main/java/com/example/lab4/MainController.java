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
    private final Text ovalColorLabel = new Text("Цвет эллипса:");
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
    private final Text axisLabel = new Text("ПОЛУОСИ:");
    private final Text xAxisLabel = new Text("x:");
    private final TextField xAxisField = new TextField();
    private final Text yAxisLabel = new Text("y:");
    private final TextField yAxisField = new TextField();
    private final Button drawEllipsBtn = new Button("Построить эллипс");
    private final Text diametrLabel = new Text("ДИАМЕТР:");
    private final TextField diametrField = new TextField();
    private final Button drawCircleBtn = new Button("Построить окружность");
    private final Text axisSpectrLabel = new Text("ПОЛУОСИ:");
    private final TextField xAxisSpectrField = new TextField();
    private final TextField yAxisSpectrField = new TextField();
    private final Text stepLabel = new Text("ШАГ:");
    private final TextField stepField = new TextField();
    private final Text spectrAmountLabel = new Text("КОЛИЧЕСТВО: ");
    private final TextField spectrAmountField = new TextField();
    private final Button drawPuchokBtn = new Button("Построить спектр");
    private final Button drawChartBtn = new Button("Построить диаграмму");
    private final ResizableCanvas canvas = new ResizableCanvas(this);

    private final CustomColor colors = new CustomColor();
    private final Algoritm algoritms = new Algoritm();

    public MainController() {
        HBox commonActionsMenu = new HBox(cancelBtn, cancelAllBtn, plusBtn, minusBtn, aboutAuthorBtn, aboutProgramBtn, currMousePositionLabel);
        commonActionsMenu.setAlignment(Pos.CENTER);
        commonActionsMenu.setSpacing(10);

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

        HBox ovalActionsMenu = new HBox(
                ovalColorLabel, ovalColorComboBox,
                backgroundColorLabel, backgroundColorComboBox,
                algoritmsColorLabel, algoritmsComboBox
        );
        ovalActionsMenu.setAlignment(Pos.CENTER);
        ovalActionsMenu.setSpacing(10);

        centerXField.setMaxWidth(60);
        centerYField.setMaxWidth(60);
        HBox centerMenu = new HBox(centerLabel, centerXLabel, centerXField, centerYLabel, centerYField);
        centerMenu.setSpacing(10);

        xAxisField.setMaxWidth(60);
        yAxisField.setMaxWidth(60);
        HBox ellipsInputMenu = new HBox(axisLabel, xAxisLabel, xAxisField, yAxisLabel, yAxisField, drawEllipsBtn);
        ellipsInputMenu.setSpacing(10);

        diametrField.setMaxWidth(60);
        HBox circleInputMenu = new HBox(diametrLabel, diametrField, drawCircleBtn);
        circleInputMenu.setSpacing(10);

        HBox inputMenu = new HBox(ellipsInputMenu, circleInputMenu);
        inputMenu.setSpacing(40);

        xAxisSpectrField.setMaxWidth(60);
        yAxisSpectrField.setMaxWidth(60);
        stepField.setMaxWidth(60);
        spectrAmountField.setMaxWidth(60);
        HBox spectrMenu = new HBox(
                axisSpectrLabel, xAxisLabel, xAxisSpectrField, yAxisLabel, yAxisSpectrField,
                stepLabel, stepField,
                spectrAmountLabel, spectrAmountField,
                drawPuchokBtn, drawChartBtn
        );
        spectrMenu.setSpacing(10);

        VBox main = new VBox(commonActionsMenu, ovalActionsMenu, centerMenu, inputMenu, spectrMenu, canvas);
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
                if (centerXField.focusedProperty().get()) { centerYField.requestFocus(); }
                else if (centerYField.isFocused()) { xAxisField.requestFocus(); }
                else if (xAxisField.isFocused()) { yAxisField.requestFocus(); }
                else if (yAxisField.isFocused()) { diametrField.requestFocus(); }
                else if (diametrField.isFocused()) { centerXField.requestFocus(); }
                else if (xAxisSpectrField.isFocused()) { yAxisSpectrField.requestFocus(); }
                else if (yAxisSpectrField.isFocused()) { stepField.requestFocus(); }
                else if (stepField.isFocused()) { spectrAmountField.requestFocus(); }
                else if (spectrAmountField.isFocused()) { xAxisSpectrField.requestFocus(); }
            }
            case ENTER -> {
                if (xAxisField.isFocused() || yAxisField.isFocused()) {
                    requestDrawBtn(Figure.ELLIPS);
                }
                else if (diametrField.isFocused()) {
                    requestDrawBtn(Figure.CIRCLE);
                }
                else if (xAxisSpectrField.isFocused() || stepField.isFocused()) {
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
        ArrayList<TextField> textFields = new ArrayList<>(Arrays.asList(centerXField, centerYField,
                xAxisField, yAxisField,
                xAxisSpectrField, yAxisSpectrField, stepField, spectrAmountField,
                diametrField
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
        drawEllipsBtn.setOnAction(actionEvent -> {
            requestDrawBtn(Figure.ELLIPS);
            canvas.requestFocus();
        });
        drawCircleBtn.setOnAction(actionEvent -> {
            requestDrawBtn(Figure.CIRCLE);
            canvas.requestFocus();
        });
        drawPuchokBtn.setOnAction(actionEvent -> {
            requestDrawPuchokBtn();
            canvas.requestFocus();
        });
        drawChartBtn.setOnAction(actionEvent -> {
            if (xAxisSpectrField.getText().isEmpty() || yAxisSpectrField.getText().isEmpty() ||
                    stepField.getText().isEmpty()) {
                return;
            }
            int xAxisLen = Integer.parseInt(xAxisSpectrField.getText());
            int yAxisLen = Integer.parseInt(yAxisSpectrField.getText());
            double step = Math.toRadians(Double.parseDouble(stepField.getText()));
            int spectrAmount = Integer.parseInt(spectrAmountField.getText());
            Color segmentColor = colors.getColors().get(ovalColorComboBox.getSelectionModel().getSelectedIndex());
            Color canvasColor = colors.getColors().get(backgroundColorComboBox.getSelectionModel().getSelectedIndex());
            AlgoritmType algoritm = algoritms.getAlgoritm(algoritmsComboBox.getSelectionModel().getSelectedItem().toString());
            canvas.drawChartBtnDidTap(xAxisLen, yAxisLen, step, spectrAmount, algoritm, segmentColor, canvasColor);
            canvas.requestFocus();
        });
    }

    private void requestDrawBtn(Figure figure) {
        if (centerXField.getText().isEmpty() || centerYField.getText().isEmpty() ||
                xAxisField.getText().isEmpty() || yAxisField.getText().isEmpty()) {
            return;
        }
        double centerX = Double.parseDouble(centerXField.getText());
        double centerY = Double.parseDouble(centerYField.getText());
        Color segmentColor = colors.getColors().get(ovalColorComboBox.getSelectionModel().getSelectedIndex());
        Color canvasColor = colors.getColors().get(backgroundColorComboBox.getSelectionModel().getSelectedIndex());
        AlgoritmType algoritm = algoritms.getAlgoritm(algoritmsComboBox.getSelectionModel().getSelectedItem().toString());
        switch (figure) {
            case ELLIPS -> {
                double xAxis = Double.parseDouble(xAxisField.getText()) / 2;
                double yAxis = Double.parseDouble(yAxisField.getText()) / 2;
                canvas.drawBtnDidTap(new Point(centerX, centerY), xAxis, yAxis, algoritm, segmentColor, canvasColor);
            }
            case CIRCLE -> {
                double diametr = Double.parseDouble(diametrField.getText());
                canvas.drawBtnDidTap(new Point(centerX, centerY), diametr, diametr, algoritm, segmentColor, canvasColor);
            }
        }
    }

    private void requestDrawPuchokBtn() {
        if (xAxisSpectrField.getText().isEmpty() || yAxisSpectrField.getText().isEmpty() ||
                spectrAmountField.getText().isEmpty() || stepField.getText().isEmpty()) {
            return;
        }
        int xAxisLen = Integer.parseInt(xAxisSpectrField.getText()) / 2;
        int yAxisLen = Integer.parseInt(yAxisSpectrField.getText()) / 2;
        double step = Double.parseDouble(stepField.getText());
        int spectrAmount = Integer.parseInt(spectrAmountField.getText());
        Color segmentColor = colors.getColors().get(ovalColorComboBox.getSelectionModel().getSelectedIndex());
        Color canvasColor = colors.getColors().get(backgroundColorComboBox.getSelectionModel().getSelectedIndex());
        AlgoritmType algoritm = algoritms.getAlgoritm(algoritmsComboBox.getSelectionModel().getSelectedItem().toString());
        canvas.drawSpectrBtnDidTap(xAxisLen, yAxisLen, step, spectrAmount, algoritm, segmentColor, canvasColor);
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