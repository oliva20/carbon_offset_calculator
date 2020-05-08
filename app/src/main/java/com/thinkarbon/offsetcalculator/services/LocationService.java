package com.thinkarbon.offsetcalculator.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.thinkarbon.offsetcalculator.R;
import com.thinkarbon.offsetcalculator.model.route.Coordinate;
import com.thinkarbon.offsetcalculator.model.route.Route;
import com.thinkarbon.offsetcalculator.rep.RouteRepository;
import com.thinkarbon.offsetcalculator.ui.fragments.TransportFragment;
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
    private List<GeoPoint> routePoints;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //if the SDK is above oreo
            System.out.println("@@@ start own foreground");
            startMyOwnForeground();
        } else {
            Intent notificationIntent = new Intent(this, TransportFragment.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("ThinKarbon")
                .setContentText("Ready to track.")
                .setContentIntent(pendingIntent).build();

            startForeground(1337, notification);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    public void startLocationTracking() {

        routePoints = new ArrayList<GeoPoint>();

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
                    Manifest.permission.ACCESS_FINE_LOCATION) != 
                                        PackageManager.PERMISSION_GRANTED) {

            stopSelf();
            return;

        }

        final List<Coordinate> coordinates = new ArrayList<>();

        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    Coordinate coordinate = new Coordinate(location.getLatitude(), 
                            location.getLongitude(), route.getId());
                    //here we should create a new live list of coordinates 
                    //being retrived to send to the fragment
                    routePoints.add(new GeoPoint(location.getLatitude(), 
                                location.getLongitude()));
                    if(!coordinates.contains(coordinate)) {
                        //here we only want to add the coordinate evey 5 requests. 
                        //Otherwise there will be too many.
                        if(count == COORDINATE_INTERVAL) {
                            System.out.println("Location has started tracking: ");
                            System.out.println("Coordinate: " + coordinate.toString());
                            coordinates.add(coordinate);
                            count = 0;
                        } else {
                            count++;
                        }
                    }
                }
            }
        };

        // TODO maybe request locations with gps as well.
        mFusedLocationClient.requestLocationUpdates(mLocationRequestHighAccuracy, 
                locationCallback, Looper.myLooper());

        //set the global scope list to be equal to the generated list on the method
        mCoordinates = coordinates;
    }

    public List<GeoPoint> stopTrackingAndSave(){

        System.out.println("LOCATION SERVICE LOGGING");

        //Stop location client from getting updates
        mFusedLocationClient.removeLocationUpdates(locationCallback);
        routeRepository.insert(route, mCoordinates);

        //Always clear the coordinate list because the service does not get killed!
        mCoordinates.clear();

        return routePoints;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "com.example.offsetcalculator";
        String channelName = "Location Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, 
                channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) 
            getSystemService(Context.NOTIFICATION_SERVICE);

        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat
                                         .Builder(this, NOTIFICATION_CHANNEL_ID);

        Notification notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
            .setContentTitle("Route tracking has started!")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .build();

        startForeground(2, notification);
    }

}
