package com.example.lab11;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainController mainPane = new MainController();
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setTitle("Лабораторная работа №11");
        stage.setMinHeight(700);
        stage.setMinWidth(600);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.DOWN ||
                    keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.UP) {
                mainPane.keyboardDidTap(keyEvent);
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}