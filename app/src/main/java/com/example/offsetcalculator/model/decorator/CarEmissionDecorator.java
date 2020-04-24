package com.example.offsetcalculator.model.decorator;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.offsetcalculator.R;
/*
* source; https://carbonfund.org/calculation-methods/
* */

public class CarEmissionDecorator extends EmissionDecorator {

    private static final String HUMAN_READABLE_NAME = "car";


    public CarEmissionDecorator(Emission e) {
        super(e);
    }

    @Override
    public Double calculate(Double miles, Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("com.example.offsetcalculator",
                                                                                Context.MODE_PRIVATE);
        Double fuelFactor = 8.78; //default for gasoline kgs of co2
        String fuelType = prefs.getString(ctx.getResources().getString(R.string.fuelKey), "gasoline");

        if(fuelType.equals("diesel")) {
            fuelFactor = 10.21;
            Log.d("@@@ CarDecorator", "Fuel type diesel");
        } else {
            Log.d("@@@ CarDecorator", "Fuel type gasoline");

        }

        Log.d("@@@ CarDecorator", "called");
        Log.d("@@@ CarDecorator Miles", String.valueOf(miles));
        return super.calculate(miles, ctx) + miles * fuelFactor / 20; //this being the emission factor for car, Also 20 is the average mile to the gallon
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
