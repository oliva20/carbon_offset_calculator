package com.example.offsetcalculator.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.offsetcalculator.BuildConfig;
import com.example.offsetcalculator.R;
import com.example.offsetcalculator.rep.AirEmissionRepository;
import com.example.offsetcalculator.rep.BusEmissionRepository;
import com.example.offsetcalculator.rep.CarEmissionRepository;
import com.example.offsetcalculator.rep.RouteRepository;
import com.example.offsetcalculator.services.LocationService;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;

public class TransportFragment extends Fragment implements View.OnClickListener {
    MapView map = null;
    CarEmissionRepository carRep;
    BusEmissionRepository busRep;
    AirEmissionRepository airRep;
    RouteRepository routeRep;
    Button btnInsert;
    Button btnDelete;
    private Activity activity = getActivity();

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
        btnInsert = (Button) view.findViewById(R.id.start_tracking);
        btnDelete = (Button) view.findViewById(R.id.stop_tracking);
        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        return view;
    }

    //add a boolean that checks if the button has been clicked.
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_tracking:
                Log.d("Button", "Button pressed to start the service");
                //TODO: add checks for device api differences here.
                //@@@ This varies between API level. Only use this with 25 or under
                getActivity().startService(new Intent(getActivity(),LocationService.class));
                break;
            case R.id.stop_tracking:
                Log.d("@@@ Button", "Stop button pressed");
                getActivity().stopService(new Intent(getActivity(),LocationService.class));
                break;
        }
    }


    //should dismiss keyboard when hitting a button
    public void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
}
