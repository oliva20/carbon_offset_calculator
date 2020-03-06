package com.example.offsetcalculator.ui;

import androidx.annotation.NonNull;
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
import com.example.offsetcalculator.model.BusEmission;
import com.example.offsetcalculator.rep.BusEmissionRepository;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
    }

    public void onResume(){
        super.onResume();
        insertData();
    }

    public void insertData(){
        TextView tv = (TextView)findViewById(R.id.tvEmissionsNumber);
        BusEmissionRepository rep = new BusEmissionRepository(this.getApplication());

        BusEmission busEmission = new BusEmission(20.0);
        rep.insert(busEmission);

        Double num = rep.getLastInsertedBusEmission().getEmissionTotal();

        //building this string so that the number is in bold
        String msg = "<b>" + num + "</b>" + " kg CO2e";
        tv.setText(Html.fromHtml(msg));
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
}
