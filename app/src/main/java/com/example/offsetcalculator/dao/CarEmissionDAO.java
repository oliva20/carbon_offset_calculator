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
    public void insert(CarEmission... emissions);

    @Update
    public void update(CarEmission... emissions);

    @Delete
    public void delete(CarEmission emission);

    @Query("SELECT * FROM car_emissions")
    public List<CarEmission> getCarEmissions();

    @Query("SELECT * FROM car_emissions WHERE id = :id")
    public CarEmission getCarEmissionById(Integer id);

    @Query("DELETE FROM car_emissions")
    public void deleteAllCarEmissions();
}

