package com.example.lab4;

import java.util.ArrayList;

enum AlgoritmType {
    STANDARD, CANONICAL, PARAMETER, BREZ, MIDDLE_POINT
}

public class Algoritm {
    private final ArrayList<String> algoritms = new ArrayList<>();

    public Algoritm() {
        algoritms.add("Стандартный");
        algoritms.add("Каноническое уравнение");
        algoritms.add("Параметрическое уравнение");
        algoritms.add("Алгоритма Брезенхема");
        algoritms.add("Алгоритм средней точки");
    }

    public ArrayList<String> getAlgoritms() {
        return algoritms;
    }

    public AlgoritmType getAlgoritm(String alg) {
        if (alg == "Стандартный") { return AlgoritmType.STANDARD; }
        if (alg == "Каноническое уравнение") { return AlgoritmType.CANONICAL; }
        if (alg == "Параметрическое уравнение") { return AlgoritmType.PARAMETER; }
        if (alg == "Алгоритма Брезенхема") { return AlgoritmType.BREZ; }
        if (alg == "Алгоритм средней точки") { return AlgoritmType.MIDDLE_POINT; }
        return AlgoritmType.STANDARD;
    }

    public static String getName(AlgoritmType algoritmType) {
        switch (algoritmType) {
            case STANDARD -> { return "Стандартный"; }
            case CANONICAL -> { return "Каноническое уравнение"; }
            case PARAMETER -> { return "Параметрическое уравнение"; }
            case BREZ -> { return "Алгоритма Брезенхема"; }
            case MIDDLE_POINT -> { return "Алгоритм средней точки"; }
        }
        return "";
    }
}