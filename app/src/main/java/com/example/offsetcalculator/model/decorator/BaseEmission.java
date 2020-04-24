package com.example.offsetcalculator.model.decorator;

import android.content.Context;

public class BaseEmission implements Emission {
    @Override
    public Double calculate(Double meters, Context ctx) {
        return 0.0;
    }

    @Override
    public String getType() {
        return "Emission";
    }
}
