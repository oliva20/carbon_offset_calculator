package com.example.offsetcalculator.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.offsetcalculator.BuildConfig;
import com.example.offsetcalculator.R;
import com.example.offsetcalculator.model.route.Coordinate;
import com.example.offsetcalculator.rep.RouteRepository;
import com.example.offsetcalculator.services.LocationService;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.advancedpolyline.MonochromaticPaintList;

import java.util.ArrayList;
import java.util.List;

public class TransportFragment extends Fragment implements View.OnClickListener, LocationListener {

    private Boolean clicked = false;
    private MapView map;
    private RouteRepository routeRepository;
    private Button btn;
    private Marker currentLocationMarker; //marker responsible for the current location in the map
    private ItemizedIconOverlay<OverlayItem> items;
    private Polyline line;
    private GeoPoint currentLocation;


    private LocationService mService;
    private boolean mBound = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get the current user's location before starting anything so that the map knows where to center
        LocationManager locationManager=(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
            if( location != null ) {
                currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                Log.d("Current location", currentLocation.toString());
            }
        } catch (SecurityException e) {
            System.out.println(e.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // this is for the map to work
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        routeRepository = new RouteRepository(getActivity().getApplication());
        View view = inflater.inflate(R.layout.fragment_transport, container, false);

        //click listeners
        btn = view.findViewById(R.id.start_tracking);
        btn.setOnClickListener(this);

        return view;
    }

    // map config must be after the view is created
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupMap();
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(this.getActivity(), LocationService.class);
        getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Log.d("ON START", "trasnport fragment" );
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.start_tracking){

                //TODO: add checks for device api differences here.
                //@@@ This varies between API level. Only use this with 25 or under
                if(clicked) {
                    Log.d("@@@ Button", "Location service has stopped");
                    clicked = false;
                    mService.stopTrackingAndSave();

                    //call the function to change the button style after setting the clicked boolean
                    changeButtonStyle(clicked);
                    drawRoute();

                } else {
                    Log.d("@@@ Button", "Location service has started");

                    mService.startLocationTracking();

                    clicked = true;
                    //call the function to change the button style after setting the clicked boolean
                    changeButtonStyle(clicked);
                }
        }

     }

     //this is used to center the map based on user location
    @Override
    public void onLocationChanged(Location location) {
        //this does not work
        //TODO FIX THIS: It is calling a null pointer error.
        // THE PROBLEM: when you go to another fragment it keeps trying get the map when it doens't exist because we are not in the transport fragment.
        // TODO: We need to find out a way to identify which overlay to delete beacause it is deleting the route overlays as well.
        try {
            map.getOverlays().remove(currentLocationMarker);
            currentLocationMarker = new Marker(map);
            currentLocationMarker.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_navigation_black_24dp, null));
            currentLocationMarker.setPosition(new GeoPoint(location.getLatitude(), location.getLongitude()));

            // add it at index 0 so that later on it can be eleminated and updated
            map.getOverlays().add(currentLocationMarker);

        } catch (Exception e){
            System.out.println(e.toString());
        }
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

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocationService.LocalBinder binder = (LocationService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    private void changeButtonStyle(Boolean isClicked) {
        if(isClicked) {
            btn.setText(R.string.btnStopTracking);
            btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        } else {
            btn.setText(R.string.btnStartTracking);
            btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private void setupMap() {
        try {
            map = getActivity().findViewById(R.id.map1);
            map.setMultiTouchControls(true);
            map.getController().setCenter(currentLocation);
            map.getController().setZoom(10);
        } catch (Exception e){
            Log.d("Exception", e.toString());
        }
    }

    private void drawRoute() {
        Paint paintBorder = new Paint();

        try {
            //TODO: This is also going to delete the marker for the current location
            map.getOverlays().remove(items);
            map.getOverlayManager().remove(line);
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        line = new Polyline();
        //represents a layer of markers
        items = new ItemizedIconOverlay<OverlayItem>(getActivity(), new ArrayList<OverlayItem>(), null);

        paintBorder.setStrokeWidth(5);
        paintBorder.setStyle(Paint.Style.FILL_AND_STROKE);
        paintBorder.setColor(Color.BLUE);
        paintBorder.setStrokeCap(Paint.Cap.ROUND);
        paintBorder.setAntiAlias(true);

        line.getOutlinePaintLists().add(new MonochromaticPaintList(paintBorder));

        List<GeoPoint> geoPoints = new ArrayList<>();
        List<Coordinate> coordinates = routeRepository.getCoordinatesFromRoute(routeRepository.getLastInsertedRoute().getId());


        //this is getting to many coordinates that are duplicated.
        for(Coordinate coordinate : coordinates) {
            Log.d("Loaded coordinate", coordinate.toString());
            // transform coordinates into geo points. Lat/Lon
            geoPoints.add(new GeoPoint(coordinate.getLatitude(), coordinate.getLongitude()));
            if(coordinates.size() != 1){
                items.addItem(new OverlayItem("Point", "Coordinate", new GeoPoint(coordinate.getLatitude(), coordinate.getLongitude())));
            }
        }

        line.setPoints(geoPoints);

        map.getOverlayManager().add(1,line);
        map.getOverlays().add(2,items);
    }
}
