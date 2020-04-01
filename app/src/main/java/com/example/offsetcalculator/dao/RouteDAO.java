package com.example.offsetcalculator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.offsetcalculator.model.route.Coordinate;
import com.example.offsetcalculator.model.route.Route;

@Dao
public interface RouteDAO {
    @Insert
    void insert(Route... routes);

    @Insert
    void insert(Coordinate... coordinates);

    @Update
    void update(Route... routes);

    @Delete
    void delete(Route route);

    @Query("DELETE FROM route")
    void deleteAllRoutes();

    @Query("SELECT * FROM route WHERE id=:id")
    Route getRouteById(Integer id);

}
