package com.example.offsetcalculator.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.offsetcalculator.R;
import com.example.offsetcalculator.rep.AirEmissionRepository;
import com.example.offsetcalculator.rep.BusEmissionRepository;
import com.example.offsetcalculator.rep.CarEmissionRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CarEmissionRepository carRep;
    BusEmissionRepository busRep;
    AirEmissionRepository airRep;
    TextView emissionsNumber;
    TextView numOfEmiss;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        carRep = new CarEmissionRepository(this.getApplication());
        busRep = new BusEmissionRepository(this.getApplication());
        airRep = new AirEmissionRepository(this.getApplication());

        setContentView(R.layout.activity_main);
        displayCarbonEmissions();

        //bottom navigation bar
        actionBar = getSupportActionBar();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
    }

    public void onResume(){
        super.onResume();
        displayCarbonEmissions();
    }

    public void displayCarbonEmissions(){
        //TODO At the moment is only displaying emissions from car.
        emissionsNumber = (TextView)findViewById(R.id.tvEmissionsNumber);
        numOfEmiss = (TextView)findViewById(R.id.tvNumOfEmissionsCreated);
        String msgCo2 = "<b>0.0</b> Tons CO2e";
        String msgEmiss = "<b>Emissisons registered:</b> ";
        emissionsNumber.setText(Html.fromHtml(msgCo2));
        numOfEmiss.setText(Html.fromHtml(msgEmiss));

        try {
        Double num = carRep.getLastInsertedCarEmission().totalEmissionToTons();
        Integer numOfEmissionsRegistered = carRep.getAllCarEmissions().size();
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
        //building this string so that the number is in bold
    }

    public void onClick(View v){

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.navigation_transport:
                intent = new Intent(this, TransportActivity.class);
                startActivity(intent);
                return true;

            case R.id.navigation_home_energy:
                intent = new Intent(this, HomeEnergyActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
}
