package com.example.offsetcalculator.model.emission;


public interface Emission {
    Integer getId();
    void calculateEmission();
    Double getTotalEmission();
    Double totalEmissionToTons();
}
