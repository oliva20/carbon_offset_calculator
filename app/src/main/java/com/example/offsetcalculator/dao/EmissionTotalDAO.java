package com.example.offsetcalculator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.offsetcalculator.model.emission.EmissionTotal;

import java.util.List;

@Dao
public interface EmissionTotalDAO {
    @Insert
    void insert(EmissionTotal... emissionTotals);

    @Update
    void update(EmissionTotal... emissionTotals);

    @Delete
    void delete(EmissionTotal  emissionTotal);

    @Query("SELECT * FROM EmissionTotal")
    List<EmissionTotal> getAll();

    @Query("SELECT * FROM EmissionTotal a WHERE  a.dateCreated = :dateCreated") //comparing strings with '=' is faster than LIKE
    EmissionTotal checkExists(String dateCreated);

    @Query("SELECT * FROM EmissionTotal a WHERE  a.dateCreated = :dateToday")
    EmissionTotal getTodayEmission(String dateToday);

    @Query("DELETE FROM EmissionTotal")
    void deleteAll();

}
