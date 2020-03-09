package com.example.offsetcalculator.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.offsetcalculator.model.CarEmission;

@Dao
public interface CarEmissionDAO {
    @Insert
    void insert(CarEmission... emissions);

    @Update
    void update(CarEmission... emissions);

    @Delete
    void delete(CarEmission emission);

    @Query("SELECT * FROM car_emissions")
    List<CarEmission> getCarEmissions();

    @Query("SELECT * FROM car_emissions WHERE id = :id")
    CarEmission getCarEmissionById(Integer id);

    @Query("DELETE FROM car_emissions")
    void deleteAllCarEmissions();
}

