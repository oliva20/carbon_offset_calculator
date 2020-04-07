package com.example.offsetcalculator.model.service;

public interface EmissionService {
    Integer getNumRegisteredEmissions();
    Double getTotalEmissions();
    Double getAvgEmissions();
    void insertHomeEmission();
    void insertDietEmission();
    void deleteAllEmissions();
}
