package com.example.offsetcalculator.impl;

import android.app.Application;

import com.example.offsetcalculator.model.emission.AirEmission;
import com.example.offsetcalculator.model.emission.BusEmission;
import com.example.offsetcalculator.model.emission.CarEmission;
import com.example.offsetcalculator.model.emission.Emission;
import com.example.offsetcalculator.model.route.Coordinate;
import com.example.offsetcalculator.model.route.Route;
import com.example.offsetcalculator.model.service.EmissionService;
import com.example.offsetcalculator.rep.AirEmissionRepository;
import com.example.offsetcalculator.rep.BusEmissionRepository;
import com.example.offsetcalculator.rep.CarEmissionRepository;
import com.example.offsetcalculator.rep.RouteRepository;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
/*
    To convert a pound measurement to a ton measurement, divide the weight by the conversion ratio. One ton is equal to 2,000 pounds, so use this simple formula to convert:
    tons = pounds ÷ 2,000
    source: https://www.inchcalculator.com/convert/pound-to-ton/
 */
public class EmissionServiceImpl implements EmissionService {

    private static final Double POUNDS_TO_TONS = 2000.0;
    private CarEmissionRepository mCarRep;
    private BusEmissionRepository mBusRep;
    private AirEmissionRepository mAirRep;
    private RouteRepository mRouteRep;

    private List<AirEmission> airEmissions;
    private List<BusEmission> busEmissions;
    private List<CarEmission> carEmissions;

    private Integer numOfEmissions;

    public EmissionServiceImpl(Application application) {
        mAirRep = new AirEmissionRepository(application);
        mBusRep = new BusEmissionRepository(application);
        mCarRep = new CarEmissionRepository(application);
        mRouteRep = new RouteRepository(application);

        airEmissions = mAirRep.getAllAirEmissions();
        busEmissions = mBusRep.getAllBusEmissions();
        carEmissions = mCarRep.getAllCarEmissions();

        numOfEmissions = mCarRep.getNumOfEmissions() + mAirRep.getNumOfEmissions() + mBusRep.getNumOfEmissions();
    }

    @Override
    public Double getTotalEmissions() {

        Double total = 0.0; //total in pounds NOT TONS
        DecimalFormat df = new DecimalFormat("##.##");

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

        total = total / POUNDS_TO_TONS;

        return Double.valueOf(df.format(total));
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

    @Override
    public void deleteAllEmissions() {
        mBusRep.deleteAllEmissions();
        mCarRep.deleteAllEmissions();
        mAirRep.deleteAllEmissions();
        mRouteRep.deleteAll();
    }

    @Override
    public List<Route> getAllRoutes() {
        return mRouteRep.getmAllRoutes();
    }

    @Override
    public List<Coordinate> getCoordinatesFromRoute(Route route) { //might change this to get coordinates from the last route
        return mRouteRep.getCoordinatesFromRoute(route);
    }

    @Override
    public Route getLastInsertedRoute() {
        return mRouteRep.getLastInsertedRoute();
    }
}
