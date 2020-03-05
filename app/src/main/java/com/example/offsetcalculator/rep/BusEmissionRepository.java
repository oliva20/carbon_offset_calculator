package com.example.offsetcalculator.rep;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.offsetcalculator.dao.BusEmissionDAO;
import com.example.offsetcalculator.db.AppDatabase;
import com.example.offsetcalculator.model.BusEmission;

import java.util.List;

public class BusEmissionRepository {
    private BusEmissionDAO mBusEmissionDao;
    private List<BusEmission> mAllBusEmissions;

    public BusEmissionRepository(Application application) {
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "thinkarbon-db")
                .allowMainThreadQueries().fallbackToDestructiveMigration()   //Allows room to do operation on main thread
                .build();
        mBusEmissionDao = db.getBusEmissionDAO();
        mAllBusEmissions = mBusEmissionDao.getBusEmissions();
    }

    //must be a public method
    public List<BusEmission> getAllBusEmissions() {
        return mAllBusEmissions;
    }

    public void insert(BusEmission busEmission){
        mBusEmissionDao.insert(busEmission);
    }

    public BusEmission getLastInsertedBusEmission(){
        return mAllBusEmissions.get(mAllBusEmissions.size()-1);
    }

    public void deleteAllEmissions(){
        mBusEmissionDao.deleteAllBusEmissions();
    }
}
