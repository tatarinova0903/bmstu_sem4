package com.example.lab3;

import java.util.ArrayList;

enum AlgoritmType {
    STANDARD, CDA, BREZ_DOUBLE, BREZ_INT, BREZ_STEP, VU
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
        if (alg == "Брезенхем действительные числа") { return AlgoritmType.BREZ_DOUBLE; }
        if (alg == "Брезенхем целые числа") { return AlgoritmType.BREZ_INT; }
        if (alg == "Брезенхем с устранением ступенчатости") { return AlgoritmType.BREZ_STEP; }
        if (alg == "ВУ") { return AlgoritmType.VU; }
        return AlgoritmType.STANDARD;
    }
}
