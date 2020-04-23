package com.example.offsetcalculator.rep;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import com.example.offsetcalculator.dao.EmissionDAO;
import com.example.offsetcalculator.db.AppDatabase;
import com.example.offsetcalculator.model.entity.CarbonEmission;

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
            Log.d("@@@ emissionTodayExists", "Emission for today exists");
            return true;
        } else {
            Log.d("@@@ emissionTodayExists", "Emission for today does not exist, create a new one");
            return false;
        }
    }

    public CarbonEmission getEmissionFromToday() {
            Log.d("Getting Emission", "from today");
            return mEmissioDao.getEmissionFromToday(df.format(new Date()));
    }


    public List<CarbonEmission> getAllEmissions(){
        return mEmissioDao.getAllEmissions();
    }

    public void deleteAll(){
        mEmissioDao.deleteAll();
    }


}
