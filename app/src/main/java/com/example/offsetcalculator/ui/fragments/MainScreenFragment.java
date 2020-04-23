package com.example.offsetcalculator.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.offsetcalculator.R;
import com.example.offsetcalculator.impl.EmissionServiceImpl;
import com.example.offsetcalculator.model.service.EmissionService;

import java.text.DecimalFormat;

public class MainScreenFragment extends Fragment {
    private EmissionService emissionService;
    private TextView emissionsNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.main_screen_title);
        emissionService = new EmissionServiceImpl(getActivity().getApplication());

        return inflater.inflate(R.layout.fragment_main_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayCarbonEmissions(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        displayCarbonEmissions(getView());
    }

    private void displayCarbonEmissions(View view){
        emissionsNumber = (TextView) getView().findViewById(R.id.tvEmissionsNumber);
        String msgCo2 = "<b>0.0</b> Kg CO2e";
        emissionsNumber.setText(Html.fromHtml(msgCo2));
        try {

            DecimalFormat decimalFormat = new DecimalFormat("##.##");
            //get the emissions
            Double num = Double.valueOf(decimalFormat.format(emissionService.getEmissionsTotalDay()));
            Log.d("@@@ NUMBER ", String.valueOf(num));
            msgCo2 = "<b>" + num + "</b>" + " Kg CO2e";
            emissionsNumber.setText(Html.fromHtml(msgCo2));

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
