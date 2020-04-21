package com.example.offsetcalculator.model.decorator;

public class BaseEmission implements Emission {
    @Override
    public Double calculate(Double meters) {
        return 0.0;
    }

    @Override
    public String getType() {
        return "Emission";
    }
}
