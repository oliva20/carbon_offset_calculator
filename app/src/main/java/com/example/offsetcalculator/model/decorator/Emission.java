package com.example.offsetcalculator.model.decorator;

import android.content.Context;

public interface Emission {
    Double calculate(Double meters, Context ctx);
    String getType(); //get the emission type, car, bus whatever
}
