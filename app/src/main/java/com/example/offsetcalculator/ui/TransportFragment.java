package com.example.offsetcalculator.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.offsetcalculator.R;
import com.example.offsetcalculator.model.CarEmission;
import com.example.offsetcalculator.rep.AirEmissionRepository;
import com.example.offsetcalculator.rep.BusEmissionRepository;
import com.example.offsetcalculator.rep.CarEmissionRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class TransportFragment extends Fragment implements View.OnClickListener {
    MapView map = null;
    CarEmissionRepository carRep;
    BusEmissionRepository busRep;
    AirEmissionRepository airRep;
    Button btnInsert;
    Button btnDelete;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Since it's a fragment we are dealing with we need to get the activity in order to get the application
        carRep = new CarEmissionRepository(getActivity().getApplication());
        busRep = new BusEmissionRepository(getActivity().getApplication());
        airRep = new AirEmissionRepository(getActivity().getApplication());

        View view = inflater.inflate(R.layout.fragment_transport, container, false);

        //click listeners
        btnInsert = (Button) view.findViewById(R.id.transport_btn1);
        btnDelete = (Button) view.findViewById(R.id.delete_emissions_btn1);
        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.transport_btn1:
                EditText edtEff = (EditText) getActivity().findViewById(R.id.edtEff);
                EditText edtMiles = (EditText) getActivity().findViewById(R.id.edtMiles);

                System.out.println("@@@ edit text -> " + edtMiles.getText().toString());

                Double miles = Double.valueOf(edtMiles.getText().toString());
                Double eff = Double.valueOf(edtEff.getText().toString());

                CarEmission carEmission = new CarEmission(miles,eff);
                carRep.insert(carEmission);

                String msg = carRep.getLastInsertedCarEmission().toString();
                System.out.println("@@@ -> " + msg);

                dismissKeyboard(getActivity());
                break;
            case R.id.delete_emissions_btn1:
                String msg1 = "Delete button called";
                System.out.println("@@@ -> " + msg1);
                carRep.deleteAllEmissions();
                dismissKeyboard(getActivity());
                break;
        }
    }

    //should dismiss keyboard when hitting a button
    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
}
