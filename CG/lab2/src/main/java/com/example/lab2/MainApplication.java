package com.example.lab2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainController mainPane = new MainController();
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setTitle("Лабораторная работа №2");
        stage.setMinHeight(500);
        stage.setMinWidth(600);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                mainPane.keyboardDidTap(ke);
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}