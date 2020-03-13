package com.example.offsetcalculator.model.workers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.offsetcalculator.R;

import static android.content.Context.LOCATION_SERVICE;

public class LocationWorker extends Worker implements LocationListener{
    private Context context;
    private LocationManager locationManager;
    private Location location;
    private Double lon = 0.0;
    private Double lat = 0.0;
    private String provider;
    public LocationWorker(@NonNull Context ctx, @NonNull WorkerParameters workerParams) {
        super(ctx, workerParams);
        this.context = ctx;
    }


    @NonNull
    @Override
    public Result doWork() {
        //@@@ TODO: This isn't getting the coords anymore for some reason.
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        provider = locationManager.getBestProvider(criteria, false);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            provider = LocationManager.PASSIVE_PROVIDER;
        }

        try {
            location = locationManager.getLastKnownLocation(provider);
        } catch (SecurityException e) {
            System.out.println(e);
        }

        if(location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
        }

        displayNotification("Location", "Lon: " + lon + " " + "lat: " + lat);
        return Result.success();
    }

    private void displayNotification(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }

    @Override
    public void onLocationChanged(Location location) {

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
