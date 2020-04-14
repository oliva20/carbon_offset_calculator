package com.example.offsetcalculator.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.offsetcalculator.BuildConfig;
import com.example.offsetcalculator.R;
import com.example.offsetcalculator.impl.EmissionServiceImpl;
import com.example.offsetcalculator.model.route.Coordinate;
import com.example.offsetcalculator.model.service.EmissionService;
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
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import java.util.List;

public class TransportFragment extends Fragment implements View.OnClickListener, LocationListener {

    private MapView map;
    private Button btn;
    private Boolean clicked = false;

    private RouteRepository routeRepository;
    private EmissionService emissionService;


    private Marker currentLocationMarker; //marker responsible for the current location in the map
    private Marker coordinateMarker; //marker responsible for the current location in the map
    private Marker beginMarker;
    private Marker endMarker;

    private ItemizedIconOverlay<OverlayItem> mapPoints; //markers in the map that correspond to different coordinate points taken every so often
    private GeoPoint currentLocation;
    private Polyline line = new Polyline();
    private LocationManager locationManager;
    private LocationService mService;
    private boolean mBound = false;
    private List<GeoPoint> routePoints;

    private boolean isRouteCreated = false; //checks whether user has created a route and can leave the fragment without an alertdialog popping up.


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get the current user's location before starting anything so that the map knows where to center
        locationManager=(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

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

    //Here we store the variables that we want to keep when the user leaves the fragment
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO this could be useful to store the zoom of the map
        outState.putDouble("ZOOM", map.getZoomLevelDouble()); //remember the zoom level
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // this is for the map to work
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        routeRepository = new RouteRepository(getActivity().getApplication());
        emissionService = new EmissionServiceImpl(getActivity().getApplication());
        View view = inflater.inflate(R.layout.fragment_transport, container, false);

        //click listeners
        btn = view.findViewById(R.id.start_tracking);
        btn.setOnClickListener(this);

        return view;
    }

    // map config must be done after the view is created
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

                    routePoints = mService.stopTrackingAndSave();

                    //call the function to change the button style after setting the clicked boolean
                    changeButtonStyle(clicked);
                    drawRoute(); //geopoints must not be null in order to drawroute to work

                } else {
                    Log.d("@@@ Button", "Location service has started");

                    mService.startLocationTracking();

                    clicked = true;
                    //call the function to change the button style after setting the clicked boolean
                    changeButtonStyle(clicked);
                }
        }

     }

    @Override
    public void onPause() {
        super.onPause();
        //stop the location manager from getting updates when user leaves the fragment
        locationManager.removeUpdates(this);
        if(isRouteCreated) { // don't display the dialog unless a route has been created
            alertAndCalcEmission();
        }
    }

    //this is used to set the current location the map based on user location
    @Override
    public void onLocationChanged(Location location) {
        // when you go to another fragment it keeps trying get the map when it doens't exist because we are not in the transport fragment.
        try {

            map.getOverlays().remove(currentLocationMarker);
            currentLocationMarker = new Marker(map);
            currentLocationMarker.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_localization, null));
            currentLocationMarker.setPosition(new GeoPoint(location.getLatitude(), location.getLongitude()));
            map.getOverlays().add(currentLocationMarker);
            map.invalidate(); //This refreshes the map so we can see the location marker moving.

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
            //TODO Here we need to alert the user that they must allow location permission
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
            map.setMinZoomLevel(10.0);
            map.getController().setCenter(currentLocation);
        } catch (Exception e){
            Log.d("Exception", e.toString());
        }
    }

    private void drawRoute() {
        isRouteCreated = true;
        Paint paintBorder = new Paint();

        try {
            //TODO when starting a new route, it leaves the start marker from the previous route but it blue.
            map.getOverlays().remove(coordinateMarker);
            map.getOverlays().remove(beginMarker);
            map.getOverlays().remove(endMarker);
            map.getOverlayManager().remove(line);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        //function this maybe
        paintBorder.setStrokeWidth(5);
        paintBorder.setStyle(Paint.Style.FILL_AND_STROKE);
        paintBorder.setColor(getResources().getColor(R.color.route_line_color));
        paintBorder.setStrokeCap(Paint.Cap.ROUND);
        paintBorder.setAntiAlias(true);

        line.getOutlinePaintLists().add(new MonochromaticPaintList(paintBorder));

        //get the coordinates here
        List<Coordinate> coordinates = routeRepository.getCoordinatesFromRoute(routeRepository.getLastInsertedRoute().getId());

        //this is getting to many coordinates that are duplicated.
        for(final Coordinate coordinate : coordinates) {
            Log.d("Loaded coordinate", coordinate.toString());

            //maybe refactor this into another function
            if(coordinates.size() != 1){

                //this will not working on api's lower than lolipop, instead we can use getResources.getDrawable
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        coordinateMarker = new Marker(map);
                        //set on event listeners and update the coordinate field of type of transport here.
                        coordinateMarker.setIcon(getActivity().getDrawable(R.drawable.ic_point));
                        coordinateMarker.setPosition(new GeoPoint(coordinate.getLatitude(), coordinate.getLongitude()));
                        InfoWindow infoWindow = new PointInfoWindow(R.layout.bonuspack_bubble, map, coordinate, getActivity(), routeRepository);
                        coordinateMarker.setInfoWindow(infoWindow);

                } else {
                         coordinateMarker = new Marker(map);
                         coordinateMarker.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_point));
                         coordinateMarker.setPosition(new GeoPoint(coordinate.getLatitude(), coordinate.getLongitude()));
                         InfoWindow infoWindow = new PointInfoWindow(R.layout.bonuspack_bubble, map, coordinate, getActivity(), routeRepository);
                         coordinateMarker.setInfoWindow(infoWindow);
                }

                map.getOverlays().add(coordinateMarker);
           }

        }

        //set beginning and end markers
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /* Begin marker setup */
            beginMarker = new Marker(map);
            beginMarker.setTitle(getString(R.string.start_marker));

            /* End marker setup */
            endMarker = new Marker(map);
            endMarker.setTitle(getString(R.string.end_marker));

            Log.d("Start Marker ", routePoints.get(0).toString());
            beginMarker.setPosition(routePoints.get(0));
            beginMarker.setIcon(getActivity().getDrawable(R.drawable.ic_start_point));

            Log.d("End Marker ", routePoints.get(routePoints.size() - 1).toString());
            endMarker.setPosition(routePoints.get(routePoints.size() - 1));
            endMarker.setIcon(getActivity().getDrawable(R.drawable.ic_end_point));
        } else {
            /* Begin marker setup */
            beginMarker = new Marker(map);
            beginMarker.setTitle(getString(R.string.start_marker));

            /* End marker setup */
            endMarker = new Marker(map);
            endMarker.setTitle(getString(R.string.end_marker));

            Log.d("Start Marker ", routePoints.get(0).toString());
            beginMarker.setPosition(routePoints.get(0));
            beginMarker.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_start_point));

            Log.d("End Marker ", routePoints.get(routePoints.size() - 1).toString());
            endMarker.setPosition(routePoints.get(routePoints.size() - 1)); //last point in the list might return null.
            endMarker.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_end_point));
        }

        line.setPoints(routePoints);
        map.getOverlays().add(beginMarker);
        map.getOverlays().add(endMarker);
        map.getOverlayManager().add(1,line);
    }

    private void alertAndCalcEmission() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Calculate emissions for this route?"); //TODO Hardcoded strings here.
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //no need to pass the coordinates as a param because they are going to be updated.
                        emissionService.createEmissionsFromCoordinates();
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }

}
