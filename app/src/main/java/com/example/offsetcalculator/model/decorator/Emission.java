package com.example.offsetcalculator.model.decorator;

public interface Emission {
    Double calculate(Double meters);
    String getType(); //get the emission type, car, bus whatever
}
