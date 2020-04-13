package com.example.offsetcalculator.ui;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.offsetcalculator.R;
import com.example.offsetcalculator.model.route.Coordinate;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public class PointInfoWindow extends InfoWindow {
    Coordinate coordinate;

    PointInfoWindow(int layoutResId, MapView mapView, Coordinate coordinate) {
        super(layoutResId, mapView);
        this.coordinate = coordinate;
    }

    public void onClose() {
    }

    public void onOpen(Object arg0) {

        LinearLayout layout = (LinearLayout) mView.findViewById(R.id.bubble_layout);
        Button btnMoreInfo = (Button) mView.findViewById(R.id.bubble_moreinfo);
        TextView txtTitle = (TextView) mView.findViewById(R.id.bubble_title);
        TextView txtDescription = (TextView) mView.findViewById(R.id.bubble_description);
        TextView txtSubdescription = (TextView) mView.findViewById(R.id.bubble_subdescription);

        txtTitle.setText(getMapView().getResources().getText(R.string.mark_info_window_title));
        txtDescription.setText(coordinate.toString());

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
}
