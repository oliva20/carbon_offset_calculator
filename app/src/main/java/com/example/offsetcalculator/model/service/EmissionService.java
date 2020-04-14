package com.example.offsetcalculator.model.service;

import com.example.offsetcalculator.model.route.Coordinate;
import com.example.offsetcalculator.model.route.Route;

import java.util.List;

public interface EmissionService {
    Integer getNumRegisteredEmissions();
    Double getEmissionsTotalDay();
    void createEmissionsFromCoordinates();
    void deleteAllEmissionsAndRoutes();
}
