package com.example.lab1;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainController extends AnchorPane {
    private Button cancelBtn;
    private Button inputFirstSetBtn;
    private Button inputSecondSetBtn;
    private Button editBtn;
    private Button calculateBtn;
    private ResizableCanvas canvas;

    public MainController() {
        canvas = new ResizableCanvas();
        canvas.setHeight(200);

        cancelBtn = new Button("Отменить");
        inputFirstSetBtn = new Button("Ввести 1 множество");
        inputSecondSetBtn = new Button("Ввести 2 множество");
        editBtn = new Button("Редактировать");
        calculateBtn = new Button("Вычислить");

        HBox menu = new HBox(cancelBtn, inputFirstSetBtn, inputSecondSetBtn, editBtn, calculateBtn);
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(10);

        VBox main = new VBox(menu, canvas);
        this.getChildren().add(main);

        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
    }

    void addHandlers() {
        cancelBtn.setOnAction(event -> { cancelBtnDidTap(); });
        inputFirstSetBtn.setOnAction(event -> { canvas.inputFirstSetBtn(event); });
        inputSecondSetBtn.setOnAction(event -> { canvas.inputSecondSetBtn(event); });
    }

    void cancelBtnDidTap() {

    }
}




//public class MainController {
//    @FXML
//    private Button calculateButton;
//
//    @FXML
//    private Button enterSetButton1;
//
//    @FXML
//    private Button enterSetButton2;
//
//    @FXML
//    private Button revertButton;
//
//    @FXML
//    private Canvas canvas;
//
//    private GraphicsContext gc;
//    private MainModel model;
//
//    @FXML
//    void initialize() {
//        model = new MainModel();
//        gc = canvas.getGraphicsContext2D();
//        gc.setFill(Color.WHITE);
//    }
//
//    @FXML
//    void onMouseClicked(MouseEvent event) {
//        int diameter = 5;
//        Point point = new Point((int)event.getX(), (int)event.getY());
//        gc.fillOval(point.getX(), point.getY(), diameter, diameter);
//        model.addToSet(point);
//    }
//
//    @FXML
//    void enterSetButton1Tap(ActionEvent event) {
//        gc.setFill(Color.CORAL);
//        model.setCurrent_set(SetNumber.FIRST);
//    }
//
//    @FXML
//    void enterSetButton2Tap(ActionEvent event) {
//        gc.setFill(Color.OLIVE);
//        model.setCurrent_set(SetNumber.SECOND);
//    }
//}