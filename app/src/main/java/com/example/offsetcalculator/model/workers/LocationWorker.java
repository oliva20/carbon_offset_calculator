package com.example.offsetcalculator.model.workers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class LocationWorker extends Worker implements LocationListener{
    private Context context;

    private LocationManager locationManager;
    private Criteria criteria;
    private String provider;

    private Location location;
    private Double lon = 0.0;
    private Double lat = 0.0;

    public LocationWorker(@NonNull Context ctx, @NonNull WorkerParameters workerParams) {
        super(ctx, workerParams);
        this.context = ctx;

        setupLocationManagerAndProvider();
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("doWork() CALLED", "Started doing work");

        try {
            //TODO: This is not getting the updated coordinates, it's just getting the first coordinates
            if (provider != null){
                location = locationManager.getLastKnownLocation(provider);
            } else {
                Log.d("ERROR", "Provider is null");
                //tell the user to allow the app to use location services
                alertError("Please allow location; Provider is null");
                return Result.failure();
            }
        } catch (SecurityException e) {
            Log.d("Location Access Error: ", e.toString());
        }

        if (location != null) {
            lat = location.getLatitude();
            Log.d("LAT", lat.toString());

            lon = location.getLongitude();
            Log.d("LON", lon.toString());
        }
        return Result.success();
    }

    public void alertError(final CharSequence text){
        //TODO: This must be sent to the UI thread, not the background thread.
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable r = new Runnable() {
            @Override
            public void run() {
                //@@@ use a toast for now
                int duration = Toast.LENGTH_LONG;
                Toast.makeText(context, text, duration).show();
            }
        };

        handler.post(r);
    }

    public void setupLocationManagerAndProvider(){
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);

        //get best provider based on criteria
        provider = locationManager.getBestProvider(criteria, false);

    }



    @Override
    public void onStopped() {
        super.onStopped();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Location changed", "Lat: " + location.getLatitude() + " Lon: " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
