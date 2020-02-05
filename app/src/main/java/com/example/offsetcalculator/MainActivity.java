package com.example.offsetcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LOGS
        Log.d("@@@", "LOG @@@");
    }

    public void onClick(View view){
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.menuHomeEnergyItem){
//            Intent intent = new Intent(this, HomeEnergyActivity.class);
//            startActivity(intent);
//            return true;
//        }
        Intent intent;
        switch (item.getItemId()) {
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
