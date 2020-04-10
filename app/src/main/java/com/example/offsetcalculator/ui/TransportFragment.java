package com.example.offsetcalculator.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Paint;
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
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.advancedpolyline.MonochromaticPaintList;

import java.util.ArrayList;
import java.util.List;

public class TransportFragment extends Fragment implements View.OnClickListener {

    private Boolean clicked = false;
    private MapView map;
    private RouteRepository routeRepository;
    private Button btn;

    LocationService mService;
    boolean mBound = false;

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
        setupMap(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this.getActivity(), LocationService.class);
        getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
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
//                    getActivity().startService(new Intent(getActivity(),LocationService.class));

                    mService.startLocationTracking();

                    clicked = true;
                    //call the function to change the button style after setting the clicked boolean
                    changeButtonStyle(clicked);
                }
        }

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

    private void setupMap(Activity activity) {
        try {
            map = activity.findViewById(R.id.map1);
            map.setMultiTouchControls(true);
            map.getController().setZoom(15.0);

            if(mService.getCurrentLocation() != null){
                map.getController().setCenter(mService.getCurrentLocation());
            } else {
                map.getController().setCenter(new GeoPoint(50.90, -1.4015));
            }

        } catch (Exception e){
            Log.d("Exception", e.toString());
        }
    }

    private void drawRoute() {

        map.getOverlays().clear();
        map.getOverlayManager().clear();

        Polyline line = new Polyline();
        Paint paintBorder = new Paint();
        //represents a layer of markers
        ItemizedIconOverlay<OverlayItem> items = new ItemizedIconOverlay<OverlayItem>(getActivity(), new ArrayList<OverlayItem>(), null);

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

        map.getOverlayManager().add(line);
        map.getOverlays().add(items);

    }

}
