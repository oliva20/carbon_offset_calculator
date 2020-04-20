package com.example.offsetcalculator.model.decorator;

//source: https://www.aef.org.uk/downloads/Grams_CO2_transportmodesUK.pdf
public class BusEmissionDecorator extends EmissionDecorator {

    public BusEmissionDecorator(Emission e) {
        super(e);
    }


    //TODO not tested
    @Override
    public Double calculate(Double km) {
        return super.calculate(km) + km * 0.0891; //bus emission factor.
    }

    @Override
    public String getType() {
        return super.getType() + "Bus";
    }
}
