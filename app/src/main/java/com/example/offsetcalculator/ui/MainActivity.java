package com.example.offsetcalculator.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;

import com.example.offsetcalculator.R;
import com.example.offsetcalculator.impl.EmissionServiceImpl;
import com.example.offsetcalculator.model.service.EmissionService;
import com.example.offsetcalculator.rep.RouteRepository;
import com.example.offsetcalculator.ui.fragments.DietFragment;
import com.example.offsetcalculator.ui.fragments.HomeEnergyFragment;
import com.example.offsetcalculator.ui.fragments.MainScreenFragment;
import com.example.offsetcalculator.ui.fragments.TransportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    BottomNavigationView bottomNavigationView;
    Fragment fragment = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_screen_title);
        bottomNavigationView = findViewById(R.id.navigation_bar);

        // TODO This clears the database remember to take it off when sending to testing
        EmissionService emissionService = new EmissionServiceImpl(getApplication());
        emissionService.deleteAllEmissionsAndRoutes();

        RouteRepository routeRepository = new RouteRepository(getApplication());
        routeRepository.deleteAll();

        //start the app in main screen
        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MainScreenFragment())
                    .commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_diet:
                        fragment = new DietFragment();
                        break;
                    case R.id.action_home:
                        fragment = new HomeEnergyFragment();
                        break;
                    case R.id.action_transport:
                        fragment = new TransportFragment();
                        break;
                    case R.id.action_main:
                        fragment = new MainScreenFragment();
                        break;
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return false;
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onResume(){
        super.onResume();
    }

    @Override
    public void onClick(View v){}

}
