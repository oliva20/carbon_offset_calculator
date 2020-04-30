package com.thinkarbon.offsetcalculator.rep;

import android.app.Application;

import androidx.room.Room;

import com.thinkarbon.offsetcalculator.dao.EmissionDAO;
import com.thinkarbon.offsetcalculator.db.AppDatabase;
import com.thinkarbon.offsetcalculator.model.entity.CarbonEmission;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmissionRepository {
    String datePattern = "dd/MM/yyyy";
    SimpleDateFormat df = new SimpleDateFormat(datePattern);

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

    public Boolean emissionTodayExists() {
        CarbonEmission ce = mEmissioDao.getEmissionFromToday(df.format(new Date()));
        if(ce != null) {
            return true;
        } else {
            return false;
        }
    }

    public CarbonEmission getEmissionFromToday() {
            return mEmissioDao.getEmissionFromToday(df.format(new Date()));
    }


    public List<CarbonEmission> getAllEmissions(){
        return mEmissioDao.getAllEmissions();
    }

    public void deleteAll(){
        mEmissioDao.deleteAll();
    }


}
