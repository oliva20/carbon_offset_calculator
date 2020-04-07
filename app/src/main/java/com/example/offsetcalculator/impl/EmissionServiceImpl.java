package com.example.offsetcalculator.impl;

import android.app.Application;

import com.example.offsetcalculator.model.emission.AirEmission;
import com.example.offsetcalculator.model.emission.BusEmission;
import com.example.offsetcalculator.model.emission.CarEmission;
import com.example.offsetcalculator.model.emission.Emission;
import com.example.offsetcalculator.model.route.Route;
import com.example.offsetcalculator.model.service.EmissionService;
import com.example.offsetcalculator.rep.AirEmissionRepository;
import com.example.offsetcalculator.rep.BusEmissionRepository;
import com.example.offsetcalculator.rep.CarEmissionRepository;
import com.example.offsetcalculator.rep.RouteRepository;

import java.util.ArrayList;
import java.util.List;

public class EmissionServiceImpl implements EmissionService {

    private CarEmissionRepository mCarRep;
    private BusEmissionRepository mBusRep;
    private AirEmissionRepository mAirRep;

    private List<AirEmission> airEmissions;
    private List<BusEmission> busEmissions;
    private List<CarEmission> carEmissions;

    private Integer numOfEmissions;

    public EmissionServiceImpl(Application application) {
        mAirRep = new AirEmissionRepository(application);
        mBusRep = new BusEmissionRepository(application);
        mCarRep = new CarEmissionRepository(application);

        airEmissions = mAirRep.getAllAirEmissions();
        busEmissions = mBusRep.getAllBusEmissions();
        carEmissions = mCarRep.getAllCarEmissions();

        numOfEmissions = mCarRep.getNumOfEmissions() + mAirRep.getNumOfEmissions() + mBusRep.getNumOfEmissions();
    }

    @Override
    public Double getTotalEmissions() {

        Double total = 0.0;

        if(!airEmissions.isEmpty()) {
            for (Emission emission : airEmissions) {
                total += emission.getTotal();
            }
        }

        if(!busEmissions.isEmpty()) {
            for(Emission emission : busEmissions) {
                total += emission.getTotal();
            }
        }

        if(!carEmissions.isEmpty()) {
            for (Emission emission : carEmissions) {
                total += emission.getTotal();
            }
        }


        return total;
    }

    @Override
    public Double getAvgEmissions() {
        Double size = Double.valueOf(numOfEmissions); //get the average emissions from all emissions registered total/size

        return getTotalEmissions() / size;
    }

    @Override
    public void insertHomeEmission() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void insertDietEmission() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getNumRegisteredEmissions() {
        return numOfEmissions;
    }
}
