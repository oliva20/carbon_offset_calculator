package com.example.offsetcalculator.model.decorator;

import android.util.Log;

//source: https://www.aef.org.uk/downloads/Grams_CO2_transportmodesUK.pdf
//Convertion formula taken from:  https://www.checkyourmath.com/convert/length/miles_km.php
public class BusEmissionDecorator extends EmissionDecorator {

    public static final String HUMAN_READABLE_NAME = "bus";

    public BusEmissionDecorator(Emission e) {
        super(e);
    }


    @Override
    public Double calculate(Double miles) {
        Log.d("@@@ BusDecorator", "called");
        Log.d("@@@ BusDecorator Miles", String.valueOf(miles));
//        km = mi x 1.609344
        miles = miles *  1.6098144;
        //TODO transform to km and then calculate.
        return super.calculate(miles) + miles * 0.0891; //bus emission factor.
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
