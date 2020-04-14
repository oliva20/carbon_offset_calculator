package com.example.offsetcalculator.ui;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.offsetcalculator.R;
import com.example.offsetcalculator.model.route.Coordinate;
import com.example.offsetcalculator.rep.RouteRepository;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public class PointInfoWindow extends InfoWindow implements AdapterView.OnItemSelectedListener {

    private Coordinate coordinate;
    private Activity activity;
    private RouteRepository mRouteRep;
    private int currentItemPosition; //current item selected in the spinner.

    PointInfoWindow(int layoutResId, MapView mapView, Coordinate coordinate, Activity activity, RouteRepository routeRep) {
        super(layoutResId, mapView);
        this.coordinate = coordinate;
        this.activity = activity;
        this.mRouteRep = routeRep;
    }

    public void onClose() {
    }

    public void onOpen(Object arg0) {

        LinearLayout layout = (LinearLayout) mView.findViewById(R.id.bubble_layout);
        TextView txtTitle = (TextView) mView.findViewById(R.id.bubble_title);
        Spinner spinner = (Spinner) mView.findViewById(R.id.bubble_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.transport_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(currentItemPosition);

        Button btnMoreInfo = (Button) mView.findViewById(R.id.bubble_moreinfo);

        txtTitle.setText(getMapView().getResources().getText(R.string.mark_info_window_title));

        layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Override Marker's onClick behaviour here
            }
        });

        //this code closes the already opened info window when we click on another marker
        for(int i=0; i<mMapView.getOverlays().size(); ++i){
            Overlay o = mMapView.getOverlays().get(i);
            if(o instanceof Marker){
                Marker m = (Marker) o;
                Marker current = (Marker) arg0;
                if(m != current)
                    m.closeInfoWindow();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String itemSelected = parent.getItemAtPosition(position).toString();

        if(itemSelected.equals(activity.getResources().getString(R.string.foot))){
            coordinate.setTransportType("foot"); // DO NOT USE STRING RESOURCES TO SET THE PROP
            coordinate.setTransportType(activity.getResources().getString(R.string.foot));
            mRouteRep.updateCoordinate(coordinate);
            currentItemPosition = position;
            mMapView.invalidate();

        } else if (itemSelected.equals(activity.getResources().getString(R.string.car))) {
            coordinate.setTransportType("car");
            mRouteRep.updateCoordinate(coordinate);
            currentItemPosition = position;
            mMapView.invalidate();

        } else if (itemSelected.equals(activity.getResources().getString(R.string.bicycle))) {
            coordinate.setTransportType("bicycle");
            mRouteRep.updateCoordinate(coordinate);
            currentItemPosition = position;
            mMapView.invalidate();

        } else if (itemSelected.equals(activity.getResources().getString(R.string.bus))) {
            coordinate.setTransportType("bus");
            mRouteRep.updateCoordinate(coordinate);
            currentItemPosition = position;
            mMapView.invalidate();

        } else if (itemSelected.equals(activity.getResources().getString(R.string.other))) {
            coordinate.setTransportType("other");
            mRouteRep.updateCoordinate(coordinate);
            currentItemPosition = position;
            mMapView.invalidate();

        } else if (itemSelected.equals(activity.getResources().getString(R.string.airplane))) {
            coordinate.setTransportType("airplane");
            mRouteRep.updateCoordinate(coordinate);
            currentItemPosition = position;
            mMapView.invalidate();

        } else {
          Log.d("Item", "No item selected");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
