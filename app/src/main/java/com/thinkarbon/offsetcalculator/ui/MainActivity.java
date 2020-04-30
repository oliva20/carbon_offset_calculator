package com.thinkarbon.offsetcalculator.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;

import com.thinkarbon.offsetcalculator.R;
import com.thinkarbon.offsetcalculator.rep.RouteRepository;
import com.thinkarbon.offsetcalculator.ui.fragments.DietFragment;
import com.thinkarbon.offsetcalculator.ui.fragments.SettingsFragment;
import com.thinkarbon.offsetcalculator.ui.fragments.MainScreenFragment;
import com.thinkarbon.offsetcalculator.ui.fragments.TransportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    //fragments
    private MainScreenFragment mainScreenFragment;
    private TransportFragment transportFragment;
    private SettingsFragment settingsFragment;
    private DietFragment dietFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_screen_title);
        bottomNavigationView = findViewById(R.id.navigation_bar);

        RouteRepository routeRepository = new RouteRepository(getApplication());
        routeRepository.deleteAll();

        if(savedInstanceState == null) {
            transportFragment = new TransportFragment();
            mainScreenFragment = new MainScreenFragment();
            dietFragment = new DietFragment();
            settingsFragment = new SettingsFragment();

            //start the app in main screen
            displayMainScreenFragment();

        }

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_diet:
                displayDietFragment();
                break;
            case R.id.action_home:
                displaySettingsFragment();
                break;
            case R.id.action_transport:
                displayTransportFragment();
                break;
            case R.id.action_main:
                displayMainScreenFragment();
                break;
        }

        return false;
    }

    @Override
    public void onClick(View v){}

    // Replace the switch method
    protected void displayMainScreenFragment() {
        setTitle(R.string.main_screen_title);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mainScreenFragment.isAdded()) { // if the fragment is already in container
            ft.show(mainScreenFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fragment_container, mainScreenFragment, "MAIN_F");
        }
        // Hide fragment transportFragment
        if (transportFragment.isAdded()) { ft.hide(transportFragment); }
        // Hide fragment settingsFragment
        if (settingsFragment.isAdded()) { ft.hide(settingsFragment); }
        // Hide fragment dietFragment
        if (dietFragment.isAdded()) { ft.hide(dietFragment); }
        // Commit changes
        ft.commit();
    }

    protected void displayTransportFragment() {
        setTitle(R.string.transport_screen_title);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (transportFragment.isAdded()) { // if the fragment is already in container
            ft.show(transportFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fragment_container, transportFragment, "TRANSPORT_F");
        }
        // Hide fragment B
        if (mainScreenFragment.isAdded()) { ft.hide(mainScreenFragment); }
        // Hide fragment C
        if (settingsFragment.isAdded()) { ft.hide(settingsFragment); }

        if (dietFragment.isAdded()) { ft.hide(dietFragment); }
        // Commit changes
        ft.commit();
    }

    protected void displaySettingsFragment() {
        setTitle(R.string.settings_title);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (settingsFragment.isAdded()) { // if the fragment is already in container
            ft.show(settingsFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fragment_container, settingsFragment, "SETTINGS_F");
        }
        // Hide fragment B
        if (mainScreenFragment.isAdded()) { ft.hide(mainScreenFragment); }
        // Hide fragment C
        if (dietFragment.isAdded()) { ft.hide(dietFragment); }

        if (transportFragment.isAdded()) { ft.hide(transportFragment); }
        // Commit changes
        ft.commit();
    }

    protected void displayDietFragment() {
        setTitle(R.string.food_title);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (dietFragment.isAdded()) { // if the fragment is already in container
            ft.show(dietFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fragment_container, dietFragment, "DIET_F");
        }
        // Hide fragment B
        if (mainScreenFragment.isAdded()) { ft.hide(mainScreenFragment); }
        // Hide fragment C
        if (transportFragment.isAdded()) { ft.hide(transportFragment); }

        if (settingsFragment.isAdded()) { ft.hide(settingsFragment); }
        // Commit changes
        ft.commit();
    }

}
