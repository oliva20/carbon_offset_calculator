package com.thinkarbon.offsetcalculator.ui.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thinkarbon.offsetcalculator.R;
import com.thinkarbon.offsetcalculator.impl.EmissionServiceImpl;
import com.thinkarbon.offsetcalculator.model.service.EmissionService;
import com.thinkarbon.offsetcalculator.ui.HelpActivity;

// source to compare with average emissions UK https://lginform.local.gov.uk/reports/lgastandard?mod-metric=51&mod-area=E92000001&mod-group=AllRegions_England&mod-type=namedComparisonGroup
public class MainScreenFragment extends Fragment {
    private EmissionService emissionService;
    private TextView emissionsNumber;
    private Button btn;

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
        btn = (Button) view.findViewById(R.id.main_btn_help);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HelpActivity.class));
            }
        });
        displayCarbonEmissions(view);
    }

    @Override
    public void onHiddenChanged(boolean hidden) { //this method gets called when the fragent is hidden and gets show again
        super.onHiddenChanged(hidden);
        if(!hidden) {
            displayCarbonEmissions(getView());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        displayCarbonEmissions(getView());
    }

    @Override
    public void onResume() {
        super.onResume();
        displayCarbonEmissions(getView());
    }

    private void displayCarbonEmissions(View view){
        emissionsNumber = (TextView) getView().findViewById(R.id.tvEmissionsNumber);
        StateListDrawable drawable = (StateListDrawable) view.findViewById(R.id.tvEmissionsNumber).getBackground();
        DrawableContainer.DrawableContainerState dcs = (DrawableContainer.DrawableContainerState)drawable.getConstantState();
        Drawable[] drawableItems = dcs.getChildren();
        GradientDrawable gradientDrawableChecked = (GradientDrawable)drawableItems[0];

        // chage the color of the circle for visual feedback
        switch (emissionService.compareEmissions()){
            case BAD:
                gradientDrawableChecked.setStroke(10, getResources().getColor(R.color.colorMedium));
                break;

            case GOOD:
                gradientDrawableChecked.setStroke(10, getResources().getColor(R.color.colorPrimary));
                break;

            case HIGH:
                gradientDrawableChecked.setStroke(10, getResources().getColor(R.color.colorHigh));
                break;
        }


        String msgCo2 = "<b>0.0</b> Kg CO2e";
        emissionsNumber.setText(Html.fromHtml(msgCo2));
        try {
            //get the emissions
            Double num = emissionService.getEmissionsTotalDay();
            msgCo2 = "<b>" + num + "</b>" + " Kg CO2e";
            emissionsNumber.setText(Html.fromHtml(msgCo2));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
