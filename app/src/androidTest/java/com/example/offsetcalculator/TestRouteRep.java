package com.example.offsetcalculator;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.offsetcalculator.db.AppDatabase;
import com.example.offsetcalculator.model.route.Route;
import com.example.offsetcalculator.rep.RouteRepository;

import org.junit.Before;

public class TestRouteRep {
    AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
    }
}
