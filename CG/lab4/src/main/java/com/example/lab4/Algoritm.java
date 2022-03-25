package com.example.lab4;

import java.util.ArrayList;

enum AlgoritmType {
    STANDARD
}

public class Algoritm {
    private final ArrayList<String> algoritms = new ArrayList<>();

    public Algoritm() {
        algoritms.add("Стандартный");
        algoritms.add("")
    }

    public ArrayList<String> getAlgoritms() {
        return algoritms;
    }

    public AlgoritmType getAlgoritm(String alg) {
        if (alg == "Стандартный") { return AlgoritmType.STANDARD; }
        return AlgoritmType.STANDARD;
    }

    public static String getName(AlgoritmType algoritmType) {
        switch (algoritmType) {
            case STANDARD -> { return "Стандартный"; }
        }
        return "";
    }
}