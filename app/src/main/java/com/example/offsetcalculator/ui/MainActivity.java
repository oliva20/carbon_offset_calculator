package com.example.offsetcalculator.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;

import com.example.offsetcalculator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_screen_title);

        bottomNavigationView = findViewById(R.id.navigation_bar);
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
                Fragment fragment = null;
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

    public void onResume(){
        super.onResume();
    }

    @Override
    public void onClick(View v){}

}
