package com.example.offsetcalculator;

import android.app.Application;
import android.content.Context;
import com.example.offsetcalculator.model.CarEmission;
import com.example.offsetcalculator.rep.CarEmissionRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

//TODO Test not passing: Need to  figure out a way to get application context in order to get access to room.
@RunWith(JUnit4.class)
public class CarEmissionRepTest extends Application  {
    Context ctx;
    Application application = (Application) ctx.getApplicationContext(); //null pointer error
    private CarEmissionRepository rep = new CarEmissionRepository(application);

    @Test
    public void writeEmissionAndReadList() throws Exception {
        CarEmission carE1 = new CarEmission(123.2, 2.5);
        CarEmission carE2 = new CarEmission(267.0, 2.5);
        CarEmission carE3 = new CarEmission(245.6, 2.5);

        List<CarEmission> emissions1 = new ArrayList<>();
        emissions1.add(carE3);
        emissions1.add(carE2);
        emissions1.add(carE1);

        rep.insert(carE1);
        rep.insert(carE2);
        rep.insert(carE3);

        List<CarEmission> emissions2 = rep.getAllCarEmissions();

        assertEquals(emissions1, emissions2);

    }
}

