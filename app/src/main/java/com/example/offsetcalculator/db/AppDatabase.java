package com.example.offsetcalculator.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.offsetcalculator.dao.CoordinateDAO;
import com.example.offsetcalculator.dao.EmissionDAO;
import com.example.offsetcalculator.dao.RouteDAO;
import com.example.offsetcalculator.model.entity.CarbonEmission;
import com.example.offsetcalculator.model.route.Coordinate;
import com.example.offsetcalculator.model.route.Route;

@Database(entities = {CarbonEmission.class, Route.class, Coordinate.class}, version = 14,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RouteDAO getRouteDAO();
    public abstract CoordinateDAO getCoordinateDAO();
    public abstract EmissionDAO getEmissionDAO();
}
