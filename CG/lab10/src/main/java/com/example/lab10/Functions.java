package com.example.lab10;

import java.util.ArrayList;

public class Functions {
    private final ArrayList<FunctionType> functions = new ArrayList<>();

    public Functions() {
        functions.add(FunctionType.FUNC1);
        functions.add(FunctionType.FUNC2);
    }

    public ArrayList<FunctionType> getFunctions() {
        return functions;
    }

    public ArrayList<String> getFunctionsStr() {
        ArrayList<String> functions = new ArrayList<>();
        this.functions.forEach(functionType -> {
            switch (functionType) {
                case FUNC1 -> { functions.add("x + z"); }
                case FUNC2 -> { functions.add("cos(x) * cos(z)"); }
            }

        });
        return functions;
    }
}

