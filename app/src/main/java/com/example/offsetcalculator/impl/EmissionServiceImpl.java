package com.example.offsetcalculator.impl;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import com.example.offsetcalculator.model.emission.AirEmission;
import com.example.offsetcalculator.model.emission.BusEmission;
import com.example.offsetcalculator.model.emission.CarEmission;
import com.example.offsetcalculator.model.emission.Emission;
import com.example.offsetcalculator.model.emission.EmissionTotal;
import com.example.offsetcalculator.model.route.Coordinate;
import com.example.offsetcalculator.model.service.EmissionService;
import com.example.offsetcalculator.rep.AirEmissionRepository;
import com.example.offsetcalculator.rep.BusEmissionRepository;
import com.example.offsetcalculator.rep.CarEmissionRepository;
import com.example.offsetcalculator.rep.EmissionTotalRepository;
import com.example.offsetcalculator.rep.RouteRepository;

import java.text.DecimalFormat;
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
    private EmissionTotalRepository mEmissionRep;

    private List<AirEmission> airEmissions;
    private List<BusEmission> busEmissions;
    private List<CarEmission> carEmissions;

    private Integer numOfEmissions;

    public EmissionServiceImpl(Application application) {
        mAirRep = new AirEmissionRepository(application);
        mBusRep = new BusEmissionRepository(application);
        mCarRep = new CarEmissionRepository(application);
        mRouteRep = new RouteRepository(application);
        mEmissionRep = new EmissionTotalRepository(application);

        airEmissions = mAirRep.getAllAirEmissions();
        busEmissions = mBusRep.getAllBusEmissions();
        carEmissions = mCarRep.getAllCarEmissions();

        numOfEmissions = mCarRep.getNumOfEmissions() + mAirRep.getNumOfEmissions() + mBusRep.getNumOfEmissions();
    }

    @Override
    public Double getEmissionsTotalDay() {
        Double x = 0.0;
        try {
            EmissionTotal emission  = mEmissionRep.getTodayEmission();
            x = emission.getTotal();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return x;
    }

    @Override
    public void createEmissionsFromCoordinates() {
        //TODO this will use routerep to calculate the latest route coordinates and based on their transport type
        //TODO then we insert the new emissions.
        //TODO coordiantes must be called here because they are going to be updated from the transport fragment.
        List<Coordinate> coordinates = mRouteRep.getCoordinatesFromRoute(mRouteRep.getLastInsertedRoute().getId());

        for(int i=0; i < coordinates.size(); i++){
            if(i+1 != coordinates.size()){ //if we are not dealing with last the coordinate of the array
                Coordinate cord = coordinates.get(i);
                Location loc1 = new Location("Point A"); //we must translate the coordinate object to location object so that it can be used in fused location client
                loc1.setLatitude(cord.getLatitude());
                loc1.setLongitude(cord.getLongitude());

                Coordinate cord2 = coordinates.get(i+1);
                Location loc2 = new Location("Point B");
                loc2.setLatitude(cord2.getLatitude());
                loc2.setLongitude(cord2.getLongitude());

                switch (cord.getTransportType()){
                    case "car":
                        CarEmission carEmission = new CarEmission(getMiles(loc1.distanceTo(loc2)), 2.0);
                        mCarRep.insert(carEmission);
                        break;
                    case "bus":
                        BusEmission busEmission = new BusEmission(getMiles(loc1.distanceTo(loc2)));
                        mBusRep.insert(busEmission);
                        break;
                    case "airplane":
                        AirEmission airEmission = new AirEmission(getMiles(loc1.distanceTo(loc2)));
                        mAirRep.insert(airEmission);
                        break;
                }
            }
        }

    }

    @Override
    public Integer getNumRegisteredEmissions() {
        return numOfEmissions;
    }

    @Override
    public void deleteAllEmissionsAndRoutes() {
        mBusRep.deleteAllEmissions();
        mCarRep.deleteAllEmissions();
        mAirRep.deleteAllEmissions();
        mRouteRep.deleteAll();
    }

    private Double getMiles(float m) {
    // miles = meters × 0.000621
        return m * 0.000621;
    }
}
