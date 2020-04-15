package com.example.offsetcalculator.rep;

import android.app.Application;

import androidx.room.Room;

import com.example.offsetcalculator.dao.BusEmissionDAO;
import com.example.offsetcalculator.db.AppDatabase;
import com.example.offsetcalculator.model.emission.BusEmission;
import com.example.offsetcalculator.model.emission.CreateDate;
import com.example.offsetcalculator.model.emission.EmissionTotal;

import java.util.List;

public class BusEmissionRepository {
    private BusEmissionDAO mBusEmissionDao;
    private EmissionTotalRepository mEmissionTotalRep;
    private List<BusEmission> mAllBusEmissions;

    public BusEmissionRepository(Application application) {
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "thinkarbon-db")
                .allowMainThreadQueries().fallbackToDestructiveMigration()   //Allows room to operate on main thread
                .build();
        mEmissionTotalRep = new EmissionTotalRepository(application);
        mBusEmissionDao = db.getBusEmissionDAO();
        mAllBusEmissions = mBusEmissionDao.getBusEmissions();
    }

    //must be a public method
    public List<BusEmission> getAllBusEmissions() {
        return mAllBusEmissions;
    }

    public void insert(BusEmission busEmission){
        CreateDate date = new CreateDate();
        EmissionTotal e = new EmissionTotal(date.now(), busEmission.getTotal());
        mEmissionTotalRep.insert(e);
        mBusEmissionDao.insert(busEmission);
    }

    public BusEmission getLastInsertedBusEmission() {
        if (mAllBusEmissions.isEmpty()) {
            return null;
        }
        return mAllBusEmissions.get(mAllBusEmissions.size() - 1);
    }

    public void deleteAllEmissions(){
        mBusEmissionDao.deleteAllBusEmissions();
    }

    public Integer getNumOfEmissions(){
        return mAllBusEmissions.size();
    }
}
