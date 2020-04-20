package com.example.offsetcalculator.model.decorator;

import com.example.offsetcalculator.model.entity.CarbonEmission;

public class CarEmissionDecorator extends EmissionDecorator {

    public static final String HUMAN_READABLE_NAME = "car";

    public CarEmissionDecorator() {

    }

    public CarEmissionDecorator(Emission e) {
        super(e);
    }

    @Override
    public Double calculate(Double miles) {
        return super.calculate(miles) + miles * 8.76 / 20; //this being the emission factor for car, Also 20 is the average mile to the gallon
    }

    @Override
    public String getType() {
        return super.getType() + "Car"; //change it to CarEmissionDecorator.class.getFullyQualified name.
    }

    @Override
    public String getHumanReadableName() {
        return HUMAN_READABLE_NAME;
    }
}
