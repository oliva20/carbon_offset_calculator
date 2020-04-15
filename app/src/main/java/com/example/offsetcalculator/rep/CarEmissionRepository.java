package com.example.offsetcalculator.rep;

import android.app.Application;

import androidx.room.Room;

import com.example.offsetcalculator.dao.CarEmissionDAO;
import com.example.offsetcalculator.dao.EmissionTotalDAO;
import com.example.offsetcalculator.db.AppDatabase;
import com.example.offsetcalculator.model.emission.CarEmission;
import com.example.offsetcalculator.model.emission.CreateDate;
import com.example.offsetcalculator.model.emission.Emission;
import com.example.offsetcalculator.model.emission.EmissionTotal;

import java.util.List;

public class CarEmissionRepository {

    private CarEmissionDAO mCarEmissionDao;
    private List<CarEmission> mAllCarEmissions;
    private EmissionTotalRepository mEmissionTotalRep;
    private Integer numOfEmissions;

    public CarEmissionRepository(Application application) {
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "thinkarbon-db")
                .allowMainThreadQueries().fallbackToDestructiveMigration()   //Allows room to do operation on main thread
                .build();
        mCarEmissionDao = db.getCarEmissionDAO();

        mEmissionTotalRep = new EmissionTotalRepository(application);

        mAllCarEmissions = mCarEmissionDao.getCarEmissions();
    }

    public List<CarEmission> getAllCarEmissions() {
        return mAllCarEmissions;
    }

    public void insert(CarEmission carEmission){
        CreateDate date = new CreateDate();
        EmissionTotal e = new EmissionTotal(date.now(), carEmission.getTotal());
        mEmissionTotalRep.insert(e);
        mCarEmissionDao.insert(carEmission);
    }

    public CarEmission getLastInsertedCarEmission(){
        if (mAllCarEmissions.isEmpty()) {
            return null;
        }
        return mAllCarEmissions.get(mAllCarEmissions.size() - 1);
    }

    public void deleteAllEmissions(){
        mCarEmissionDao.deleteAllCarEmissions();
    }

    public Integer getNumOfEmissions(){
        return mAllCarEmissions.size();
    }

}
