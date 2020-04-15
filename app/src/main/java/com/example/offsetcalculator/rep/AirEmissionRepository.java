package com.example.offsetcalculator.rep;

import android.app.Application;

import androidx.room.Room;

import com.example.offsetcalculator.dao.AirEmissionDAO;
import com.example.offsetcalculator.db.AppDatabase;
import com.example.offsetcalculator.model.emission.AirEmission;
import com.example.offsetcalculator.model.emission.CreateDate;
import com.example.offsetcalculator.model.emission.EmissionTotal;

import java.util.List;

public class AirEmissionRepository {
    private AirEmissionDAO mAirEmissionDao;
    private List<AirEmission> mAllAirEmissions;
    private EmissionTotalRepository mEmissionTotalRep;

    public AirEmissionRepository(Application application) {
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "thinkarbon-db")
                .allowMainThreadQueries().fallbackToDestructiveMigration()   //Allows room to do operation on main thread
                .build();
        mEmissionTotalRep = new EmissionTotalRepository(application);
        mAirEmissionDao = db.getAirEmissionDAO();
        mAllAirEmissions = mAirEmissionDao.getAirEmissions();
    }
    //must be a public method
    public List<AirEmission> getAllAirEmissions() {
        return mAllAirEmissions;
    }

    public void insert(AirEmission airEmission){
        CreateDate date = new CreateDate();
        EmissionTotal e = new EmissionTotal(date.now(), airEmission.getTotal());
        mEmissionTotalRep.insert(e);
        mAirEmissionDao.insert(airEmission);
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
