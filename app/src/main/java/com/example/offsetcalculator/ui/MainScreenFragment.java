package com.example.offsetcalculator.ui;

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

public class MainScreenFragment extends Fragment {
    private EmissionService emissionService;
    TextView emissionsNumber;
    TextView numOfEmiss;

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

    public void displayCarbonEmissions(View view){

        emissionsNumber = (TextView) getView().findViewById(R.id.tvEmissionsNumber);
        numOfEmiss = (TextView) getView().findViewById(R.id.tvNumOfEmissionsCreated);

        String msgCo2 = "<b>0.0</b> Tons CO2e";
        String msgEmiss = "<b>Emissisons registered:</b> ";
        emissionsNumber.setText(Html.fromHtml(msgCo2));
        numOfEmiss.setText(Html.fromHtml(msgEmiss));

        try {
            //get the emissions
            Double num = emissionService.getTotalEmissions();

            Log.d("@@@ NUMBER ", String.valueOf(num));
            //            Double num = carRep.getLastInsertedCarEmission().totalEmissionToTons();
            Integer numOfEmissionsRegistered = emissionService.getNumRegisteredEmissions();
            if(num!=null){
                msgCo2 = "<b>" + num + "</b>" + " Tons CO2e";
                emissionsNumber.setText(Html.fromHtml(msgCo2));
                msgEmiss = "<b>Emissisons registered:</b> " + numOfEmissionsRegistered;
                numOfEmiss.setText(Html.fromHtml(msgEmiss));
            } else {
                msgCo2 = "<b>" + num + "</b>" + " Tons CO2e";
                emissionsNumber.setText(Html.fromHtml(msgCo2));
                msgEmiss = "<b>Emissisons registered:</b> " + numOfEmissionsRegistered;
                numOfEmiss.setText(Html.fromHtml(msgEmiss));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
