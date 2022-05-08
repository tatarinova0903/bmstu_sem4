package com.example.lab7;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainController mainPane = new MainController();
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setTitle("Лабораторная работа №7");
        stage.setMinHeight(700);
        stage.setMinWidth(600);
//        scene.setOnKeyPressed(keyEvent -> {
//            mainPane.keyboardDidTap(keyEvent);
//        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}