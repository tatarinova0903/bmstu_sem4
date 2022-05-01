package com.example.lab5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.lab5.Direction.*;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainController mainPane = new MainController();
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setTitle("Лабораторная работа №5");
        stage.setMinHeight(700);
        stage.setMinWidth(600);
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case LEFT, RIGHT, UP, DOWN -> mainPane.keyboardDidTap(keyEvent);
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}