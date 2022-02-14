package com.example.lab1;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainController extends AnchorPane {
    private Button plusBtn = new Button("+");
    private Button minusBtn = new Button("-");
    private Button cancelBtn = new Button("Отменить");
    private Button inputFirstSetBtn = new Button("Ввести 1 множество");
    private Button inputSecondSetBtn = new Button("Ввести 2 множество");
    private Button editBtn = new Button("Редактировать");
    private Button calculateBtn = new Button("Вычислить");
    private Button addToFirstSetBtn = new Button("Добавить в 1 множество");
    private Button addToSecondSetBtn = new Button("Добавить во 2 множество");
    private Text coordXLabel = new Text("X:");
    private TextField coordXField = new TextField();
    private Text coordYLabel = new Text("Y:");
    private TextField coordYField = new TextField();
    private ResizableCanvas canvas = new ResizableCanvas();

    public MainController() {
        canvas.setHeight(200);

        HBox mainMenu = new HBox(plusBtn, minusBtn, cancelBtn, inputFirstSetBtn, inputSecondSetBtn, editBtn, calculateBtn);
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
        editBtn.setOnAction(actionEvent -> { canvas.editBtnDidTap(); });
        plusBtn.setOnAction(actionEvent -> { canvas.scale(true); });
        minusBtn.setOnAction(actionEvent -> { canvas.scale(false); });
    }
}