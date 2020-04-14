package com.example.offsetcalculator.model.service;

public interface EmissionService {
    Integer getNumRegisteredEmissions();
    Double getEmissionsTotalDay();
    void createEmissionsFromCoordinates();
    void deleteAllEmissionsAndRoutes();
}
