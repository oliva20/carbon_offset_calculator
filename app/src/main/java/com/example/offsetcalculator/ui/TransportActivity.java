package com.example.offsetcalculator.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.offsetcalculator.R;
import com.example.offsetcalculator.model.BusEmission;
import com.example.offsetcalculator.model.CarEmission;
import com.example.offsetcalculator.model.Emission;
import com.example.offsetcalculator.rep.CarEmissionRepository;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class TransportActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {
    MapView map = null;
    LocationManager locationManager;
    CarEmissionRepository carRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carRep = new CarEmissionRepository(this.getApplication());

        setTitle(getString(R.string.publicTransportTitle));
        //Load application context
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_transport);


        map = (MapView) findViewById(R.id.map1);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.getController().setZoom(16.0);
        map.getController().setCenter(new GeoPoint(51.05,-0.72));

        //TODO: delete this. Just for testing.
        Button button = (Button) findViewById(R.id.transport_btn1);
        Button button2 = (Button) findViewById(R.id.delete_emissions_btn1);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        map.onResume();
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
                intent = new Intent(this, TransportActivity.class);
                startActivity(intent);
                return true;

            case R.id.menuVehicleItem:
                intent = new Intent(this, CarActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    //location stuff
    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void onClick(View v){
        if(v == findViewById(R.id.transport_btn1)){
            EditText edtEff = (EditText) findViewById(R.id.edtEff);
            EditText edtMiles = (EditText) findViewById(R.id.edtMiles);

            Double miles = Double.valueOf(edtMiles.getText().toString());
            Double eff = Double.valueOf(edtEff.getText().toString());

            CarEmission carEmission = new CarEmission(miles,eff);
            carRep.insert(carEmission);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (v == findViewById(R.id.delete_emissions_btn1)){
            carRep.deleteAllEmissions();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

}
