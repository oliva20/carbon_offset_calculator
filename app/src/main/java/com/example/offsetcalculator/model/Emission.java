package com.example.offsetcalculator.model;


public interface Emission {
    Integer getId();
    void calculateEmission();
    Double getTotalEmission();
    Double totalEmissionToTons();
}
