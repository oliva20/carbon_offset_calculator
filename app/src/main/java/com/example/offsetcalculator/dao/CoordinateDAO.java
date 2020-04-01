package com.example.offsetcalculator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.offsetcalculator.model.route.Coordinate;

import java.util.List;

@Dao
public interface CoordinateDAO {

    @Insert
    void insert(Coordinate... coordinates);

    @Update
    void update(Coordinate... coordinates);

    @Delete
    void delete(Coordinate coordinate);

    @Query("SELECT * FROM coordinate")
    List<Coordinate> getAllCoordinates();

    @Query("SELECT * FROM coordinate WHERE parentRouteId=:parentRouteId")
    List<Coordinate> findCoordinatesForRoute(final int parentRouteId);
}
