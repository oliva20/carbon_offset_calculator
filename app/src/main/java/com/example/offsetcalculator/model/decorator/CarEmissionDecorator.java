package com.example.offsetcalculator.model.decorator;

public class CarEmissionDecorator extends EmissionDecorator {
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

}
