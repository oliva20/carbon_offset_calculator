package com.example.offsetcalculator.rep;

import android.app.Application;

import androidx.room.Room;

import com.example.offsetcalculator.dao.AirEmissionDAO;
import com.example.offsetcalculator.db.AppDatabase;
import com.example.offsetcalculator.model.emission.AirEmission;

import java.util.List;

public class AirEmissionRepository {
    private AirEmissionDAO mAirEmissionDao;
    private List<AirEmission> mAllAirEmissions;

    public AirEmissionRepository(Application application) {
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "thinkarbon-db")
                .allowMainThreadQueries().fallbackToDestructiveMigration()   //Allows room to do operation on main thread
                .build();
        mAirEmissionDao = db.getAirEmissionDAO();
        mAllAirEmissions = mAirEmissionDao.getAirEmissions();
    }
    //must be a public method
    public List<AirEmission> getAllAirEmissions() {
        return mAllAirEmissions;
    }

    public void insert(AirEmission AirEmission){
        mAirEmissionDao.insert(AirEmission);
    }

    public AirEmission getLastInsertedAirEmission(){
        if (mAllAirEmissions.isEmpty()) {
            return null;
        }
        return mAllAirEmissions.get(mAllAirEmissions.size() - 1);
    }

    public void deleteAllEmissions(){
        mAirEmissionDao.deleteAllAirEmissions();
    }

    public Integer getNumOfEmissions(){
        return mAllAirEmissions.size();
    }
}
