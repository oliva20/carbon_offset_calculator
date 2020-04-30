package com.thinkarbon.offsetcalculator.ui.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.thinkarbon.offsetcalculator.R;
import com.thinkarbon.offsetcalculator.ui.AboutActivity;
import com.thinkarbon.offsetcalculator.ui.HelpActivity;

public class SettingsFragment extends Fragment  {
    TextView tvAbout;
    TextView tvHelp;
    SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle(R.string.settings_title);

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        tvAbout = (TextView) view.findViewById(R.id.settings_about);
        tvHelp = (TextView) view.findViewById(R.id.settings_help);

        tvAbout.setOnClickListener(v -> startActivity(new Intent(getActivity(), AboutActivity.class)));

        tvHelp.setOnClickListener(v -> startActivity(new Intent(getActivity(), HelpActivity.class)));


        //init prefs;
        prefs = getActivity().getSharedPreferences("com.example.offsetcalculator", Context.MODE_PRIVATE);


        //setup spinners
        /* --------------- SPINNER COUNTRY --------------------- */
        Spinner spinnerCountry = (Spinner) view.findViewById(R.id.settings_spinner_country);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(getActivity(),
                R.array.countries, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCountry.setAdapter(adapterCountry);

        // Listener
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putString(getResources().getString(R.string.countryKey), parent.getItemAtPosition(position).toString().toLowerCase()).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /* --------------- END  --------------------- */

        /* --------------- SPINNER FUEL TYPE --------------------- */
        Spinner spinnerFuel = (Spinner) view.findViewById(R.id.settings_spinner_fuel_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterFuel = ArrayAdapter.createFromResource(getActivity(),
                R.array.fuelType, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterFuel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFuel.setAdapter(adapterFuel);

        //Listener
        spinnerFuel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putString(getResources().getString(R.string.fuelKey), parent.getItemAtPosition(position).toString().toLowerCase()).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /* --------------- END  --------------------- */


        return view;
    }

}
