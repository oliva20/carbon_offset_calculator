package com.example.offsetcalculator.model.decorator;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Default emission
public class BaseEmission implements Emission {
    @Override
    public Double calculate(Double meters) {
        return 0.0;
    }

    @Override
    public String getType() {
        return "Emission";
    }

}
