package com.thinkarbon.offsetcalculator;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.thinkarbon.offsetcalculator.db.AppDatabase;
import com.thinkarbon.offsetcalculator.model.decorator.BaseEmission;
import com.thinkarbon.offsetcalculator.model.decorator.CarEmissionDecorator;
import com.thinkarbon.offsetcalculator.model.decorator.Emission;

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
