package com.example.offsetcalculator.model.decorator;

public abstract class EmissionDecorator implements Emission {

    private Emission decoratedEmission;

    EmissionDecorator(Emission e) {
        this.decoratedEmission = e;
    }

    @Override
    public Double calculate(Double meters) {
        return decoratedEmission.calculate(meters);
    }

    @Override
    public String getType() {
        return decoratedEmission.getType();
    }

    public abstract String getHumanReadableName();
}
