package com.example.lab10;

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
            case FUNC1_SUMXZ -> {
                return x + z;
            }
            case FUNC2_COSXCOSZ -> {
                return Math.cos(x) * Math.cos(z);
            }
            case FUNC3 -> {
                return Math.sin(Math.cos(x)) * Math.sin(z);
            }
            case FUNC4 -> {
                return Math.cos(x) / z;
            }
            case FUNC5 -> {
                return (x * x / 4) + (z * z / 4);
            }
            case FUNC6 -> {
                return Math.cos(x) * Math.cos(Math.sin(z));
            }
            case FUNC7 -> {
                return Math.cos(x) * z / 3;
            }
            case FUNC8 -> {
                return Math.sin(x * x + z * z);
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
