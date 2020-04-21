package com.example.offsetcalculator.model.decorator;

import android.util.Log;

//source: https://www.aef.org.uk/downloads/Grams_CO2_transportmodesUK.pdf
public class BusEmissionDecorator extends EmissionDecorator {

    public static final String HUMAN_READABLE_NAME = "bus";

    public BusEmissionDecorator(Emission e) {
        super(e);
    }


    @Override
    public Double calculate(Double miles) {
        Log.d("@@@ BusDecorator", "called");
        Log.d("@@@ BusDecorator Miles", String.valueOf(miles));

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
