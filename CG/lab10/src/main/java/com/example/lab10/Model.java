package com.example.lab10;

import java.util.ArrayList;

public class Model {
    private final TranslateCoords translateCoords = new TranslateCoords(0, 0);
    private double[][] transMatrix = new double[4][4];
    private Functions functions = new Functions();

    public TranslateCoords getTranslateCoords() {
        return translateCoords;
    }

    public double[][] getTransMatrix() {
        return transMatrix;
    }

    public void setTransMatrix(double[][] transMatrix) {
        this.transMatrix = transMatrix;
    }

    public void cancelAll() {

    }

    public void cancel() {

    }

    void createTransMatrix() {
//    Создание транспонированной матрицы
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                transMatrix[i][j] = i == j ? 1 : 0;
            }
        }
    }

    double function(FunctionType type, double x, double z) {
        switch (type) {
            case FUNC1 -> {
                return x + z;
            }
            case FUNC2 -> {
                return Math.cos(x) * Math.cos(z);
            }
        }
        return 0.0;
    }

    void turnMatrix(double [][] matrix) {
//        Повернуть матрицу.
        double[][] result = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result[i][j] += transMatrix[i][k] * matrix[k][j];
                }
            }
        }
        transMatrix = result;
    }
}
