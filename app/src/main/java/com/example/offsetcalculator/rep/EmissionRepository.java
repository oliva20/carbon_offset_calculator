package com.example.offsetcalculator.rep;

import android.app.Application;

import androidx.room.Room;

import com.example.offsetcalculator.dao.EmissionDAO;
import com.example.offsetcalculator.db.AppDatabase;
import com.example.offsetcalculator.model.decorator.BaseEmission;
import com.example.offsetcalculator.model.decorator.Emission;
import com.example.offsetcalculator.model.entity.CarbonEmission;

import java.util.List;

public class EmissionRepository {

    EmissionDAO mEmissioDao;

    public EmissionRepository(Application application){
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "thinkarbon-db")
                .allowMainThreadQueries().fallbackToDestructiveMigration()   //Allows room to do operation on main thread
                .build();
        mEmissioDao = db.getEmissionDAO();
    }

    public void insert(CarbonEmission e) {
        mEmissioDao.insert(e);
    }

    public void update(CarbonEmission e) {
        mEmissioDao.update(e);
    }

    public void delete(CarbonEmission e){
        mEmissioDao.delete(e);
    }

    public List<CarbonEmission> getAllEmissions(){
        return mEmissioDao.getAllEmissions();
    }

    public void deleteAll(){
        mEmissioDao.deleteAll();
    }


}
