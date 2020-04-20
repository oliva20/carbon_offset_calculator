package com.example.offsetcalculator.impl;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import com.example.offsetcalculator.model.decorator.BaseEmission;
import com.example.offsetcalculator.model.decorator.BusEmissionDecorator;
import com.example.offsetcalculator.model.decorator.CarEmissionDecorator;
import com.example.offsetcalculator.model.decorator.Emission;
import com.example.offsetcalculator.model.decorator.EmissionDecorator;
import com.example.offsetcalculator.model.entity.CarbonEmission;
import com.example.offsetcalculator.model.factory.EmissionDecoratorFactory;
import com.example.offsetcalculator.model.route.Coordinate;
import com.example.offsetcalculator.model.service.EmissionService;
import com.example.offsetcalculator.rep.EmissionRepository;
import com.example.offsetcalculator.rep.RouteRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/*
    To convert a pound measurement to a ton measurement, divide the weight by the conversion ratio. One ton is equal to 2,000 pounds, so use this simple formula to convert:
    tons = pounds ÷ 2,000
    source: https://www.inchcalculator.com/convert/pound-to-ton/
 */
public class EmissionServiceImpl implements EmissionService {

    private static final Double POUNDS_TO_TONS = 2000.0;
    private RouteRepository mRouteRep;
    private EmissionRepository mEmissionRep;
    private Integer numOfEmissions;

    public EmissionServiceImpl(Application application) {
        mRouteRep = new RouteRepository(application);
        mEmissionRep = new EmissionRepository(application);
    }

    @Override
    public Double getEmissionsTotalDay() {
        //TODO We should use the scaluclate distance method here and s
//        Double total = 0.0; //total in pounds NOT TONS
//        DecimalFormat df = new DecimalFormat("##.##");
//
//        total = total / POUNDS_TO_TONS;
        Double total = 0.0;
        for(CarbonEmission emission : mEmissionRep.getAllEmissions()) {
            Log.d("Got emission", emission.toString());
            total += emission.getEmission();
        }
        return total;
    }

    @Override
    public void createEmissionsFromCoordinates() {
        //TODO this will use routerep to calculate the latest route coordinates and based on their transport type
        //TODO then we insert the new emissions.
        //TODO coordiantes must be called here because they are going to be updated from the transport fragment.
        List<Coordinate> coordinates = mRouteRep.getCoordinatesFromRoute(mRouteRep.getLastInsertedRoute().getId());
        String datePattern = "dd/MM/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        Set<String> humanReadableNames = EmissionDecoratorFactory.getHumanReadableNames();

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

                for(String hrn : humanReadableNames ) {
                    Log.d("@@@ In loop", hrn);

                    if(hrn.equals(cord.getTransportType())) {
                        Log.d("cord type", cord.getTransportType());

                        //this works and gets the car decorator but you must decorate it with the BaseEmission.
                        EmissionDecorator ed = EmissionDecoratorFactory.getDecoForHumanReadableName(hrn);
                        CarbonEmission ce = new CarbonEmission();

                        Double totalCarbon = ed.calculate(getMiles((double) loc1.distanceTo(loc2)));
                        Log.d("Total carbon", String.valueOf(totalCarbon));
                        ce.setEmission(totalCarbon);
                        ce.setDate(df.format(new Date()));
                        mEmissionRep.insert(ce);
                    }
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
        mEmissionRep.deleteAll();
        mRouteRep.deleteAll();
    }


    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    private Double getMiles(Double m) {
    // miles = meters × 0.000621
        return m * 0.000621;
    }
}
