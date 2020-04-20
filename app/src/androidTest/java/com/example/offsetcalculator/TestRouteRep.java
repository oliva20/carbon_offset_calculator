package com.example.offsetcalculator;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.offsetcalculator.db.AppDatabase;
import com.example.offsetcalculator.model.decorator.BaseEmission;
import com.example.offsetcalculator.model.decorator.CarEmissionDecorator;
import com.example.offsetcalculator.model.decorator.Emission;

import org.junit.Before;

public class TestRouteRep {
    AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
    }

    public void test(){
        Emission e  = new BaseEmission();
        e = new CarEmissionDecorator(e);
        e.calculate(100.0);
    }
}
