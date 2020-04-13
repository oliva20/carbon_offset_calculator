package com.example.offsetcalculator.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.offsetcalculator.dao.AirEmissionDAO;
import com.example.offsetcalculator.dao.BusEmissionDAO;
import com.example.offsetcalculator.dao.CarEmissionDAO;
import com.example.offsetcalculator.dao.CoordinateDAO;
import com.example.offsetcalculator.dao.RouteDAO;
import com.example.offsetcalculator.model.emission.AirEmission;
import com.example.offsetcalculator.model.emission.BusEmission;
import com.example.offsetcalculator.model.emission.CarEmission;
import com.example.offsetcalculator.model.route.Coordinate;
import com.example.offsetcalculator.model.route.Route;

import java.util.concurrent.Executors;


@Database(entities = {CarEmission.class, BusEmission.class, AirEmission.class, Route.class, Coordinate.class}, version = 12,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CarEmissionDAO getCarEmissionDAO();
    public abstract BusEmissionDAO getBusEmissionDAO();
    public abstract AirEmissionDAO getAirEmissionDAO();
    public abstract RouteDAO getRouteDAO();
    public abstract CoordinateDAO getCoordinateDAO();
}
