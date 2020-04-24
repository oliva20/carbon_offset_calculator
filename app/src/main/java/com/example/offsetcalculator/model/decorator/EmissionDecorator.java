package com.example.offsetcalculator.model.decorator;

import android.content.Context;

public abstract class EmissionDecorator implements Emission {

    private Emission decoratedEmission;

    EmissionDecorator(Emission e) {
        this.decoratedEmission = e;
    }

    @Override
    public Double calculate(Double meters, Context ctx) {
        return decoratedEmission.calculate(meters, ctx);
    }

    @Override
    public String getType() {
        return decoratedEmission.getType();
    }

    public abstract String getHumanReadableName();
}
