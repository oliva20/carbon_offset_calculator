package com.thinkarbon.offsetcalculator.model.decorator;

import android.content.Context;

//source: https://www.aef.org.uk/downloads/Grams_CO2_transportmodesUK.pdf
//Convertion formula taken from:  https://www.checkyourmath.com/convert/length/miles_km.php
public class BusEmissionDecorator extends EmissionDecorator {

    private static final String HUMAN_READABLE_NAME = "bus";

    public BusEmissionDecorator(Emission e) {
        super(e);
    }


    @Override
    public Double calculate(Double miles, Context ctx) {
//        km = mi x 1.609344
        miles = miles *  1.6098144;
        return super.calculate(miles, ctx) + miles * 0.0891; //bus emission factor.
    }

    @Override
    public String getType() {
        return super.getType() + "Bus";
    }

    @Override
    public String getHumanReadableName() {
        return HUMAN_READABLE_NAME;
    }
}
