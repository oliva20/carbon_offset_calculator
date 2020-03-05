package com.example.offsetcalculator.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.offsetcalculator.dao.BusEmissionDAO;
import com.example.offsetcalculator.dao.CarEmissionDAO;
import com.example.offsetcalculator.model.BusEmission;
import com.example.offsetcalculator.model.CarEmission;


@Database(entities = {CarEmission.class, BusEmission.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CarEmissionDAO getCarEmissionDAO();
    public abstract BusEmissionDAO getBusEmissionDAO();
}
