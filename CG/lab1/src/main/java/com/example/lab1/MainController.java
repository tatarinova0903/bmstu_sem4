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
    private final Button addToSetBtn = new Button("Добавить");
    private final Text coordXLabel = new Text("X:");
    private final TextField coordXField = new TextField();
    private final Text coordYLabel = new Text("Y:");
    private final TextField coordYField = new TextField();
    private final Text resLabel = new Text("RES:");
    private final Text res = new Text("");
    private final ResizableCanvas canvas = new ResizableCanvas(this);

    public MainController() {
        HBox aboutMenu = new HBox(aboutAuthorBtn, aboutProgramBtn, currMousePositionLabel);
        aboutMenu.setAlignment(Pos.CENTER);
        aboutMenu.setSpacing(10);
        aboutMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        HBox setBox = new HBox(firstSetBtn, secondSetBtn);
        setBox.setAlignment(Pos.CENTER);
        setBox.setSpacing(0);
        setBox.setStyle("-fx-border-color: #ffffff;");
        setBox.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        HBox actionBox = new HBox(inputSetBtn, editBtn);
        actionBox.setAlignment(Pos.CENTER);
        actionBox.setSpacing(0);
        actionBox.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        firstSetBtn.setToggleGroup(toggleSetGroup);
        secondSetBtn.setToggleGroup(toggleSetGroup);
        inputSetBtn.setToggleGroup(toggleActionGroup);
        editBtn.setToggleGroup(toggleActionGroup);
        HBox mainMenu = new HBox(
                plusBtn,
                minusBtn,
                cancelBtn,
                actionBox,
                setBox,
                calculateBtn
        );
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setSpacing(10);
        mainMenu.getChildren().forEach(element -> {
            element.setFocusTraversable(false);
        });

        coordXField.setMaxWidth(60);
        coordYField.setMaxWidth(60);
        HBox editMenu = new HBox(
                coordXLabel,
                coordXField,
                coordYLabel,
                coordYField,
                addToSetBtn,
                clearBtn,
                resLabel,
                res
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

    void showResult(double x, double y, double radius) {
        String resCircle = String.format("x: %.2f y: %.2f rad: %.2f", x, y, radius);
        res.setText(resCircle);
    }

    void clearResult() {
        res.setText("");
    }

    void keyboardDidTap(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.TAB) {
            if (coordXField.focusedProperty().get()) {
                coordYField.requestFocus();
            } else if (coordYField.focusedProperty().get()) {
                coordXField.requestFocus();
            }
        }
        if (coordXField.focusedProperty().get() || coordYField.focusedProperty().get()) { return; }
        switch (keyEvent.getCode()) {
            case BACK_SPACE -> canvas.deleteBtnDidTap();
            case RIGHT -> canvas.goTo(Direction.RIGHT);
            case LEFT -> canvas.goTo(Direction.LEFT);
            case UP -> canvas.goTo(Direction.UP);
            case DOWN -> canvas.goTo(Direction.DOWN);
        }
    }

    void setCurrentMousePosition(double x, double y) {
        currMousePositionLabel.setText(String.format("%.0f; %.0f", x, y));
    }

    private void addHandlers() {
        cancelBtn.setOnAction(actionEvent -> {
            canvas.cancelBtnDidTap();
            canvas.requestFocus();
        });
        firstSetBtn.setOnAction(actionEvent -> {
            canvas.firstSetBtnDidTap(actionEvent);
            canvas.requestFocus();
        });
        secondSetBtn.setOnAction(actionEvent ->  {
            canvas.secondSetBtnDidTap(actionEvent);
            canvas.requestFocus();
        });
        inputSetBtn.setOnAction(actionEvent -> {
            canvas.inputBtnDidTap();
            canvas.requestFocus();
        });
        calculateBtn.setOnAction(actionEvent -> {
            canvas.calculateBtnDidTap();
            canvas.requestFocus();
        });
        addToSetBtn.setOnAction(actionEvent -> {
            if (coordXField.getText().isEmpty() || coordYField.getText().isEmpty() || canvas.getModel().getCurrent_set() == SetNumber.NONE) { return; }
            double xCoord = Double.parseDouble(coordXField.getText());
            double yCoord = Double.parseDouble(coordYField.getText());
            canvas.addPointManually(xCoord, yCoord, canvas.getModel().getCurrent_set());
            canvas.getModel().setLastAction(LastAction.ADD_POINT);
            canvas.requestFocus();
        });
        editBtn.setOnAction(actionEvent -> {
            canvas.editBtnDidTap();
            canvas.requestFocus();
        });
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