package com.example.offsetcalculator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.offsetcalculator.model.entity.CarbonEmission;

import java.util.List;

@Dao
public interface EmissionDAO {
    @Insert
    void insert(CarbonEmission... emission);

    @Update
    void update(CarbonEmission... emissions);

    @Delete
    void delete(CarbonEmission emission);

    @Query("SELECT * FROM CarbonEmission")
    List<CarbonEmission> getAllEmissions();

    @Query("DELETE FROM  CarbonEmission")
    void deleteAll();
}

