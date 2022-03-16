package com.example.lab3;

import java.util.ArrayList;

enum AlgoritmType {
    STANDARD, CDA, BREZ_FLOAT, BREZ_INT, BREZ_SMOOTH, VU
}

public class Algoritm {
    private final ArrayList<String> algoritms = new ArrayList<>();

    public Algoritm() {
        algoritms.add("Стандартный");
        algoritms.add("ЦДА");
        algoritms.add("Брезенхем действительные числа");
        algoritms.add("Брезенхем целые числа");
        algoritms.add("Брезенхем с устранением ступенчатости");
        algoritms.add("ВУ");
    }

    public ArrayList<String> getAlgoritms() {
        return algoritms;
    }

    public AlgoritmType getAlgoritm(String alg) {
        if (alg == "Стандартный") { return AlgoritmType.STANDARD; }
        if (alg == "ЦДА") { return AlgoritmType.CDA; }
        if (alg == "Брезенхем действительные числа") { return AlgoritmType.BREZ_FLOAT; }
        if (alg == "Брезенхем целые числа") { return AlgoritmType.BREZ_INT; }
        if (alg == "Брезенхем с устранением ступенчатости") { return AlgoritmType.BREZ_SMOOTH; }
        if (alg == "ВУ") { return AlgoritmType.VU; }
        return AlgoritmType.STANDARD;
    }

    public static String getName(AlgoritmType algoritmType) {
        switch (algoritmType) {
            case STANDARD -> { return "Стандартный"; }
            case VU -> { return "ВУ"; }
            case CDA -> { return "ЦДА"; }
            case BREZ_INT -> { return "Брезенхем целые числа"; }
            case BREZ_FLOAT -> { return "Брезенхем действительные числа"; }
            case BREZ_SMOOTH -> { return "Брезенхем с устранением ступенчатости"; }
        }
        return "";
    }
}
