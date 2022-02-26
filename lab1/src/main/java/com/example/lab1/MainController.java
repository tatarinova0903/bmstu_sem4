package com.example.lab1;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class MainController extends AnchorPane {
    private final Button aboutProgramBtn = new Button("О программе");
    private final Button aboutAuthorBtn = new Button("Об авторе");
    private final Text currMousePositionLabel = new Text();
    private final Button plusBtn = new Button("+");
    private final Button minusBtn = new Button("-");
    private final Button cancelBtn = new Button("Отменить");
    private final ToggleGroup toggleSetGroup = new ToggleGroup();
    private final ToggleButton firstSetBtn = new ToggleButton("1 множество");
    private final ToggleButton secondSetBtn = new ToggleButton("2 множество");
    private final ToggleGroup toggleActionGroup = new ToggleGroup();
    private final ToggleButton inputSetBtn = new ToggleButton("Ввести");
    private final ToggleButton editBtn = new ToggleButton("Редактировать");
    private final Button clearBtn = new Button("Очистить");
    private final Button calculateBtn = new Button("Вычислить");
    private final Button addToFirstSetBtn = new Button("Добавить в 1 множество");
    private final Button addToSecondSetBtn = new Button("Добавить во 2 множество");
    private final Text coordXLabel = new Text("X:");
    private final TextField coordXField = new TextField();
    private final Text coordYLabel = new Text("Y:");
    private final TextField coordYField = new TextField();
    private final Text resLabel = new Text("RES:");
    private final TextField resXField = new TextField();
    private final TextField resYField = new TextField();
    private final TextField resRadiusField = new TextField();
    private final ResizableCanvas canvas = new ResizableCanvas(this);

    public MainController() {
        firstSetBtn.setToggleGroup(toggleSetGroup);
        secondSetBtn.setToggleGroup(toggleSetGroup);
        HBox aboutMenu = new HBox(aboutAuthorBtn, aboutProgramBtn, currMousePositionLabel);
        aboutMenu.setAlignment(Pos.CENTER);
        aboutMenu.setSpacing(10);
        aboutMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        inputSetBtn.setToggleGroup(toggleActionGroup);
        editBtn.setToggleGroup(toggleActionGroup);
        HBox mainMenu = new HBox(
                plusBtn,
                minusBtn,
                cancelBtn,
                inputSetBtn, editBtn,
                firstSetBtn, secondSetBtn,
                calculateBtn
        );
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setSpacing(10);
        mainMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        coordXField.setMaxWidth(60);
        coordYField.setMaxWidth(60);
        resXField.setMaxWidth(70);
        resYField.setMaxWidth(70);
        resRadiusField.setMaxWidth(80);
        HBox editMenu = new HBox(
                coordXLabel,
                coordXField,
                coordYLabel,
                coordYField,
                addToFirstSetBtn,
                addToSecondSetBtn,
                resLabel,
                resXField,
                resYField,
                resRadiusField,
                clearBtn
                );
        editMenu.setAlignment(Pos.CENTER);
        editMenu.setSpacing(5);
        editMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        VBox main = new VBox(aboutMenu, mainMenu, editMenu, canvas);
        main.setSpacing(5);
        this.getChildren().add(main);

        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());

        addHandlers();
    }

    void showResult(Circle circle) {
        String resX = String.format("x: %.2f", circle.getCenter().getX());
        String resY = String.format("y: %.2f", circle.getCenter().getY());
        String resRadius = String.format("rad: %.2f", circle.getRadius());
        resXField.setText(resX);
        resYField.setText(resY);
        resRadiusField.setText(resRadius);
    }

    void keyboardDidTap(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case BACK_SPACE -> canvas.deleteBtnDidTap();
            case RIGHT -> canvas.goTo(Direction.RIGHT);
            case LEFT -> canvas.goTo(Direction.LEFT);
            case UP -> canvas.goTo(Direction.UP);
            case DOWN -> canvas.goTo(Direction.DOWN);
        }
    }

    void setCurrentMousePosition(double x, double y) {
        currMousePositionLabel.setText(String.format("%.2f; %.2f", x, y));
    }

    private void addHandlers() {
        cancelBtn.setOnAction(actionEvent -> { canvas.cancelBtnDidTap(); });
        firstSetBtn.setOnAction(actionEvent -> { canvas.firstSetBtnDidTap(actionEvent); });
        secondSetBtn.setOnAction(actionEvent ->  { canvas.secondSetBtnDidTap(actionEvent); });
        inputSetBtn.setOnAction(actionEvent -> { canvas.inputBtnDidTap(); });
        calculateBtn.setOnAction(actionEvent -> { canvas.calculateBtnDidTap(); });
        addToFirstSetBtn.setOnAction(actionEvent -> {
            double xCoord = Double.parseDouble(coordXField.getText()) + canvas.getWidth() / 2;
            double yCoord = Double.parseDouble(coordYField.getText()) * (-1) + canvas.getHeight() / 2;
            canvas.addPoint(xCoord, yCoord, SetNumber.FIRST);
        });
        addToSecondSetBtn.setOnAction(actionEvent -> {
            double xCoord = Double.parseDouble(coordXField.getText()) + canvas.getWidth() / 2;
            double yCoord = Double.parseDouble(coordYField.getText()) * (-1) + canvas.getHeight() / 2;
            canvas.addPoint(xCoord, yCoord, SetNumber.SECOND);
        });
        editBtn.setOnAction(actionEvent -> { canvas.editBtnDidTap(); });
        plusBtn.setOnAction(actionEvent -> { canvas.scale(true); });
        minusBtn.setOnAction(actionEvent -> { canvas.scale(false); });
        aboutAuthorBtn.setOnAction(actionEvent -> { canvas.aboutAuthorDidTap(); });
        aboutProgramBtn.setOnAction(actionEvent -> { canvas.aboutProgramBtnDidTap(); });
        clearBtn.setOnAction(actionEvent ->  {
            canvas.clearBtnDidTap();
        });
        addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                keyboardDidTap(ke);
            }
        });
    }
}