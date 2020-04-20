package com.example.offsetcalculator.impl;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import com.example.offsetcalculator.model.decorator.BaseEmission;
import com.example.offsetcalculator.model.decorator.BusEmissionDecorator;
import com.example.offsetcalculator.model.decorator.CarEmissionDecorator;
import com.example.offsetcalculator.model.decorator.Emission;
import com.example.offsetcalculator.model.entity.CarbonEmission;
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

                //TODO Make it data driven not hard coded.
                //TODO Emission decorator factory that would return list of decorator in the system.
                //instead of switch we would use try{ EmissionDecorator.forName("org.solent.CarEmissionDecorator").newInstance(); } catch ...
                switch (cord.getTransportType()){
                    case "car": //TODO instead of using a case statemen
                        Emission e = new CarEmissionDecorator(new BaseEmission());
                        CarbonEmission ce = new CarbonEmission();
                        DecimalFormat decimalFormat = new DecimalFormat("##.##");
                        //TODO conversion is not ok
                        //round to 2 decimal places
                        Double totalCarbon = e.calculate(getMiles((double) loc1.distanceTo(loc2)));
                        ce.setEmission(Double.valueOf(decimalFormat.format(totalCarbon)));
                        ce.setDate(df.format(new Date()));
                        mEmissionRep.insert(ce);
                        Log.d("Inserting car emission", ce.toString());
                        break;

                    case "bus":
                        Emission be = new BusEmissionDecorator(new BaseEmission());
                        CarbonEmission bce = new CarbonEmission();
                        Double tc = (be.calculate((double) loc1.distanceTo(loc2)) * 100) / 100;
                        bce.setEmission(tc);
                        mEmissionRep.insert(bce);
                        Log.d("Inserting bus emission", bce.toString());
                        break;
                    case "airplane":
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
