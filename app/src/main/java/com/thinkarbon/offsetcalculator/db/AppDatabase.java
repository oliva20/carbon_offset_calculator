package com.thinkarbon.offsetcalculator.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.thinkarbon.offsetcalculator.dao.CoordinateDAO;
import com.thinkarbon.offsetcalculator.dao.EmissionDAO;
import com.thinkarbon.offsetcalculator.dao.RouteDAO;
import com.thinkarbon.offsetcalculator.model.entity.CarbonEmission;
import com.thinkarbon.offsetcalculator.model.route.Coordinate;
import com.thinkarbon.offsetcalculator.model.route.Route;

@Database(entities = {CarbonEmission.class, Route.class, Coordinate.class}, version = 14,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RouteDAO getRouteDAO();
    public abstract CoordinateDAO getCoordinateDAO();
    public abstract EmissionDAO getEmissionDAO();
}
