package com.example.offsetcalculator.ui.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.offsetcalculator.R;
import com.example.offsetcalculator.impl.EmissionServiceImpl;
import com.example.offsetcalculator.model.service.EmissionService;


public class DietFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText edt;
    Button btn;
    EmissionService emissionService;
    String itemSelected; //default

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.food_title);
        View view = inflater.inflate(R.layout.fragment_diet, container,false);
        emissionService = new EmissionServiceImpl(getActivity().getApplication());
        /* --------------- spinner --------------------- */
        Spinner spinner = (Spinner) view.findViewById(R.id.spinnerDiet);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.food_types, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        /* --------------- spinner --------------------- */

        edt = (EditText) view.findViewById(R.id.edtGrams);
        btn = (Button) view.findViewById(R.id.diet_btn);

        btn.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.diet_btn) {
            System.out.println("@@@ " + edt.getText().toString());
            if(!edt.getText().toString().equals("")){ // if it isn't empty
                System.out.println("Item selected: " + itemSelected);
                emissionService.createEmissionForFoodType(itemSelected, Double.valueOf(edt.getText().toString()));
                edt.getText().clear();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // we need to format the string so we a constant value the whole time
        String item = parent.getItemAtPosition(position).toString();
        System.out.println("Item selected: " + item);

        switch (item) {
            case "Red meat":
                itemSelected = "redMeat";
                break;
            case "White meat":
                itemSelected = "whiteMeat";
                break;
            case "Vegetables":
                itemSelected = "vegetables";
                break;
            case "Fruit":
                itemSelected = "fruit";
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
