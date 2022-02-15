package com.example.lab1;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class MainController extends AnchorPane {
    private final Button plusBtn = new Button("+");
    private final Button minusBtn = new Button("-");
    private final Button cancelBtn = new Button("Отменить");
    private final Button inputFirstSetBtn = new Button("Ввести 1 множество");
    private final Button inputSecondSetBtn = new Button("Ввести 2 множество");
    private final Button editFirstBtn = new Button("Редактировать 1 множество");
    private final Button editSecondBtn = new Button("Редактировать 2 множество");
    private final Button calculateBtn = new Button("Вычислить");
    private final Button addToFirstSetBtn = new Button("Добавить в 1 множество");
    private final Button addToSecondSetBtn = new Button("Добавить во 2 множество");
    private final Text coordXLabel = new Text("X:");
    private final TextField coordXField = new TextField();
    private final Text coordYLabel = new Text("Y:");
    private final TextField coordYField = new TextField();
    private final ResizableCanvas canvas = new ResizableCanvas();

    public MainController() {
        HBox mainMenu = new HBox(
                plusBtn,
                minusBtn,
                cancelBtn,
                inputFirstSetBtn,
                inputSecondSetBtn,
                editFirstBtn,
                editSecondBtn,
                calculateBtn
        );
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setSpacing(10);
        mainMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        coordXField.setMaxWidth(60);
        coordYField.setMaxWidth(60);
        HBox editMenu = new HBox(coordXLabel, coordXField, coordYLabel, coordYField, addToFirstSetBtn, addToSecondSetBtn);
        editMenu.setAlignment(Pos.CENTER);
        editMenu.setSpacing(10);
        editMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        VBox main = new VBox(mainMenu, editMenu, canvas);
        main.setSpacing(5);
        this.getChildren().add(main);

        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());

        addHandlers();
    }

    void addHandlers() {
        cancelBtn.setOnAction(actionEvent -> { canvas.cancelBtnDidTap(); });
        inputFirstSetBtn.setOnAction(actionEvent -> { canvas.inputFirstSetBtnDidTap(actionEvent); });
        inputSecondSetBtn.setOnAction(actionEvent -> { canvas.inputSecondSetBtnDidTap(actionEvent); });
        calculateBtn.setOnAction(actionEvent -> { canvas.calculateBtnDidTap(); });
        addToFirstSetBtn.setOnAction(actionEvent -> {
            double xCoord = Double.parseDouble(coordXField.getText());
            double yCoord = Double.parseDouble(coordYField.getText());
            canvas.addPoint(xCoord, yCoord, SetNumber.FIRST);
        });
        addToSecondSetBtn.setOnAction(actionEvent -> {
            double xCoord = Double.parseDouble(coordXField.getText());
            double yCoord = Double.parseDouble(coordYField.getText());
            canvas.addPoint(xCoord, yCoord, SetNumber.SECOND);
        });
        editFirstBtn.setOnAction(actionEvent -> { canvas.editBtnDidTap(SetNumber.FIRST); });
        editSecondBtn.setOnAction(actionEvent -> { canvas.editBtnDidTap(SetNumber.SECOND); });
        plusBtn.setOnAction(actionEvent -> { canvas.scale(true); });
        minusBtn.setOnAction(actionEvent -> { canvas.scale(false); });
    }
}