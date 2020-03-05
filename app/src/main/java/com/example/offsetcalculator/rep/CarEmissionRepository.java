package com.example.offsetcalculator.rep;

import android.app.Application;

import androidx.room.Room;

import com.example.offsetcalculator.dao.CarEmissionDAO;
import com.example.offsetcalculator.db.AppDatabase;
import com.example.offsetcalculator.model.CarEmission;

import java.util.List;

public class CarEmissionRepository {
    private CarEmissionDAO mCarEmissionDao;
    private List<CarEmission> mAllCarEmissions;

    public CarEmissionRepository(Application application) {
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "thinkarbon-db")
                .allowMainThreadQueries().fallbackToDestructiveMigration()   //Allows room to do operation on main thread
                .build();
        mCarEmissionDao = db.getCarEmissionDAO();
        mAllCarEmissions = mCarEmissionDao.getCarEmissions();
    }
    //must be a public method
    public List<CarEmission> getAllBusEmissions() {
        return mAllCarEmissions;
    }

    public void insert(CarEmission busEmission){
        mCarEmissionDao.insert(busEmission);
    }

    public CarEmission getLastInsertedBusEmission(){
        return mAllCarEmissions.get(mAllCarEmissions.size()-1);
    }

    public void deleteAllEmissions(){
        mCarEmissionDao.deleteAllCarEmissions();
    }

}
