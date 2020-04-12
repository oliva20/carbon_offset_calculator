package com.example.offsetcalculator.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.offsetcalculator.model.route.Coordinate;
import com.example.offsetcalculator.model.route.Route;
import com.example.offsetcalculator.rep.RouteRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocationService extends Service {

    // Binder given to clients of this service
    private final IBinder binder = new LocalBinder();
    private FusedLocationProviderClient mFusedLocationClient;

    private final static long UPDATE_INTERVAL = (60 * 1000) * 5;  /* UPDATE EVERY 5 MINS */
    private final static long FASTEST_INTERVAL = 2000; /* 2 seconds */
    private final static double MILES_CONVERSATION = 0.00062137;
    private final static int COORDINATE_INTERVAL = 15; //every 15 location updates, save one coordinate

    private Route route;
    private RouteRepository routeRepository;
    private List<Coordinate> mCoordinates;
    private LocationCallback locationCallback;

    private int count = 0; //count how many times the locatin has been requested
    private List<GeoPoint> geoPoints;

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public LocationService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocationService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        routeRepository = new RouteRepository(getApplication());

        //initialize a route here because it needs be initialized before the coordinates this method increments the id
        if (Build.VERSION.SDK_INT >= 26) { // android demands that api levels above 26 require that the user is notified with the service.
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "My Channel",
                    NotificationManager
                            .IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LocationService", "Service has been destroyed!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("LocationService", "Service has started!");
        return START_NOT_STICKY;
    }

    public void startLocationTracking() {

        geoPoints = new ArrayList<GeoPoint>();

        //create a new rooute and generate an id
        route = new Route(routeRepository.generateId(), new Date().toString());

        // ---------------------------------- LocationRequest ------------------------------------
        // Create the location request to start receiving updates
        LocationRequest mLocationRequestHighAccuracy = new LocationRequest();
        //@@@ might want to change as it uses a lot of battery
        mLocationRequestHighAccuracy.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequestHighAccuracy.setInterval(UPDATE_INTERVAL);
        mLocationRequestHighAccuracy.setFastestInterval(FASTEST_INTERVAL);


        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Location", "getLocation: stopping the location service.");
            stopSelf();
            return;
        }
        Log.d("Location", "getLocation: getting location information.");

        final List<Coordinate> coordinates = new ArrayList<>();

        locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        Location location = locationResult.getLastLocation();
                        if (location != null) {
                            Coordinate coordinate = new Coordinate(location.getLatitude(), location.getLongitude(), route.getId());

                            //here we should create a new live list of coordinates being retrived to send to the fragment
                            geoPoints.add(new GeoPoint(location.getLatitude(), location.getLongitude()));

                            if(!coordinates.contains(coordinate)) {
                                //here we only want to add the coordinate evey 5 requests. Otherwise there will be too many.
                                if(count == COORDINATE_INTERVAL) {
                                    coordinates.add(coordinate);
                                    Log.d("Coordinate added", coordinate.toString());
                                    count = 0;
                                } else {
                                    count++;
                                    Log.d("Coordinate", "Not added");
                                }
                            }
                        }
                    }
                };

        mFusedLocationClient.requestLocationUpdates(mLocationRequestHighAccuracy, locationCallback, Looper.myLooper());

        //set the global scope list to be equal to the generated list on the method
        mCoordinates = coordinates;
    }

    public List<GeoPoint> stopTrackingAndSave(){

        System.out.println("LOCATION SERVICE LOGGING");

        Log.d("NEW ROUTE", route.toString());

        for(Coordinate coordinate : mCoordinates) {
            Log.d("Got", coordinate.toString());
        }

        //Stop location client from getting updates
        mFusedLocationClient.removeLocationUpdates(locationCallback);
        routeRepository.insert(route, mCoordinates);

        //Always clear the coordinate list because the service does not get killed!
        mCoordinates.clear();

        return geoPoints;
    }
}
