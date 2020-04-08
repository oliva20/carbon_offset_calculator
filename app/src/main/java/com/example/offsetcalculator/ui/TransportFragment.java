package com.example.offsetcalculator.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.offsetcalculator.BuildConfig;
import com.example.offsetcalculator.R;
import com.example.offsetcalculator.rep.AirEmissionRepository;
import com.example.offsetcalculator.rep.BusEmissionRepository;
import com.example.offsetcalculator.rep.CarEmissionRepository;
import com.example.offsetcalculator.services.LocationService;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;

public class TransportFragment extends Fragment implements View.OnClickListener {

    Boolean clicked = false;
    MapView map = null;
    CarEmissionRepository carRep;
    BusEmissionRepository busRep;
    AirEmissionRepository airRep;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        carRep = new CarEmissionRepository(getActivity().getApplication());
        busRep = new BusEmissionRepository(getActivity().getApplication());
        airRep = new AirEmissionRepository(getActivity().getApplication());

        // this is for the map to work
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        View view = inflater.inflate(R.layout.fragment_transport, container, false);

        //click listeners
        btn = (Button) view.findViewById(R.id.start_tracking);
        btn.setOnClickListener(this);

        return view;
    }

    //add a boolean that checks if the button has been clicked.
    @Override
    public void onClick(View v) {

        Log.d("Clicked", clicked.toString());

        if(v.getId() == R.id.start_tracking){
                //TODO: add checks for device api differences here.
                //@@@ This varies between API level. Only use this with 25 or under
                if(clicked) {
                    Log.d("@@@ Button", "Location service has stopped");
                    getActivity().stopService(new Intent(getActivity(),LocationService.class));
                    clicked = false;
                    //call the function to change the button style after setting the clicked boolean
                    changeButtonStyle(clicked);
                } else {
                    Log.d("@@@ Button", "Location service has started");
                    getActivity().startService(new Intent(getActivity(),LocationService.class));
                    clicked = true;
                    //call the function to change the button style after setting the clicked boolean
                    changeButtonStyle(clicked);
                    Log.d("Clicked", clicked.toString());
                }
        }

     }

    private void changeButtonStyle(Boolean isClicked) {
        if(isClicked) {
            btn.setText(R.string.btnStopTracking);
            btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        } else {
            btn.setText(R.string.btnStartTracking);
            btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}
