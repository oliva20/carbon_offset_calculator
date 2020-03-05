package com.example.offsetcalculator.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.offsetcalculator.R;

public class HomeEnergyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_energy);
        //change the title on the actual app activity instead of displaying a title
        setTitle(getString(R.string.homeEnergyTitle));


        /* --------------- spinner --------------------- */

        Spinner spinner = (Spinner) findViewById(R.id.spinnerHomeEnergy);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.energy_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        /* --------------- spinner --------------------- */

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuMainItem:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.menuHomeEnergyItem:
                intent = new Intent(this, HomeEnergyActivity.class);
                startActivity(intent);
                return true;

            case R.id.menuTransportItem:
                intent = new Intent(this, PublicTransportActivity.class);
                startActivity(intent);
                return true;

            case R.id.menuVehicleItem:
                intent = new Intent(this, CarActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
}
