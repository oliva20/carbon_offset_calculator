package com.example.offsetcalculator.model.service;

public interface EmissionService {
    Integer getNumRegisteredEmissions();
    Double getEmissionsTotalDay();
    void createEmissionsFromCoordinates();
    void createEmissionForFoodType(String foodType, Double grams); //{fruit, vegetables, redMeat, whiteMeat
    void deleteAllEmissionsAndRoutes();
}
