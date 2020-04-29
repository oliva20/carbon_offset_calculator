package com.example.offsetcalculator.model.service;

import com.example.offsetcalculator.model.entity.EmissionScale;

public interface EmissionService {
    Integer getNumRegisteredEmissions();
    Double getEmissionsTotalDay();
    void createEmissionsFromCoordinates();
    void createEmissionForFoodType(String foodType, Double grams); //{fruit, vegetables, redMeat, whiteMeat
    EmissionScale compareEmissions();
    void deleteAllEmissionsAndRoutes();
}
