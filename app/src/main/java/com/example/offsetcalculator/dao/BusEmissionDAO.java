package com.example.offsetcalculator.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.offsetcalculator.model.BusEmission;

@Dao
public interface BusEmissionDAO {
    @Insert
    public void insert(BusEmission... emissions);

    @Update
    public void update(BusEmission... emissions);

    @Delete
    public void delete(BusEmission emission);

    @Query("SELECT * FROM bus_emissions")
    public List<BusEmission> getBusEmissions();

    @Query("SELECT * FROM bus_emissions WHERE id = :id")
    public BusEmission getBusEmissionById(Integer id);

    @Query("DELETE FROM bus_emissions")
    public void deleteAllBusEmissions();
}
