package com.thinkarbon.offsetcalculator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thinkarbon.offsetcalculator.model.entity.CarbonEmission;

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

    @Query("SELECT * FROM CarbonEmission ce WHERE ce.date = :dateToday")
    CarbonEmission getEmissionFromToday(String dateToday);
}

