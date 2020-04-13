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

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public class PointInfoWindow extends InfoWindow implements AdapterView.OnItemSelectedListener {

    Coordinate coordinate;
    Activity activity;

    PointInfoWindow(int layoutResId, MapView mapView, Coordinate coordinate, Activity activity) {
        super(layoutResId, mapView);
        this.coordinate = coordinate;
        this.activity = activity;
    }

    public void onClose() {
    }

    public void onOpen(Object arg0) {

        LinearLayout layout = (LinearLayout) mView.findViewById(R.id.bubble_layout);
        TextView txtTitle = (TextView) mView.findViewById(R.id.bubble_title);

        /* --------------- spinner --------------------- */
        Spinner spinner = (Spinner) mView.findViewById(R.id.bubble_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.transport_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        /* --------------- spinner --------------------- */

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
        //TODO Find out to know which option was selected here.
        String itemSelected = parent.getItemAtPosition(position).toString();

        if(itemSelected.equals(activity.getResources().getString(R.string.foot))){
            System.out.println("You selected foot"); //TODO Maybe update the coordinate here to know which type of emission.

        } else if (itemSelected.equals(activity.getResources().getString(R.string.car))) {
            System.out.println("You selected car"); //TODO Maybe update the coordinate here to know which type of emission.

        } else if (itemSelected.equals(activity.getResources().getString(R.string.bicycle))) {
            System.out.println("You selected bicycle"); //TODO Maybe update the coordinate here to know which type of emission.

        } else if (itemSelected.equals(activity.getResources().getString(R.string.bus))) {
            System.out.println("You selected bus"); //TODO Maybe update the coordinate here to know which type of emission.

        } else if (itemSelected.equals(activity.getResources().getString(R.string.other))) {
            System.out.println("You selected other"); //TODO Maybe update the coordinate here to know which type of emission.

        } else if (itemSelected.equals(activity.getResources().getString(R.string.airplane))) {
            System.out.println("You selected airplane"); //TODO Maybe update the coordinate here to know which type of emission.
        } else {
          Log.d("Item", "No item selected");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
