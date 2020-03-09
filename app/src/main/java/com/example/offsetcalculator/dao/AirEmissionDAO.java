package com.example.offsetcalculator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.offsetcalculator.model.AirEmission;

import java.util.List;

@Dao
public interface AirEmissionDAO {
    @Insert
    void insert(AirEmission... emissions);

    @Update
    void update(AirEmission... emissions);

    @Delete
    void delete(AirEmission emission);

    @Query("SELECT * FROM air_emissions")
    List<AirEmission> getAirEmissions();

    @Query("SELECT * FROM air_emissions WHERE id = :id")
    AirEmission getAirEmissionById(Integer id);

    @Query("DELETE FROM air_emissions")
    void deleteAllAirEmissions();
}
