package com.example.lab10;

import java.util.ArrayList;

public class Functions {
    private final ArrayList<FunctionType> functions = new ArrayList<>();

    public Functions() {
        functions.add(FunctionType.FUNC1_SUMXZ);
        functions.add(FunctionType.FUNC2_COSXCOSZ);
        functions.add(FunctionType.FUNC3);
        functions.add(FunctionType.FUNC4);
        functions.add(FunctionType.FUNC5);
        functions.add(FunctionType.FUNC6);
    }

    public ArrayList<FunctionType> getFunctions() {
        return functions;
    }

    public ArrayList<String> getFunctionsStr() {
        ArrayList<String> functions = new ArrayList<>();
        this.functions.forEach(functionType -> {
            switch (functionType) {
                case FUNC1_SUMXZ -> { functions.add("x + z"); }
                case FUNC2_COSXCOSZ -> { functions.add("cos(x) * cos(z)"); }
                case FUNC3 -> { functions.add("5 * sin(cos(x)) * sin(z)"); }
                case FUNC4 -> { functions.add("cos(x) / z"); }
                case FUNC5 -> { functions.add("x^2 / 4 + z^2 / 4"); }
                case FUNC6 -> { functions.add("cos(x) * cos(sin(z))"); }
            }

        });
        return functions;
    }
}

