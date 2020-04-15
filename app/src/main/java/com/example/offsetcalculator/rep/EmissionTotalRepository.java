package com.example.offsetcalculator.rep;

import android.app.Application;

import androidx.room.Room;

import com.example.offsetcalculator.dao.EmissionTotalDAO;
import com.example.offsetcalculator.db.AppDatabase;
import com.example.offsetcalculator.model.emission.CreateDate;
import com.example.offsetcalculator.model.emission.EmissionTotal;

import java.util.List;

public class EmissionTotalRepository {
    private EmissionTotalDAO mEmissionTotalDao;
    private List<EmissionTotal> emissions;
    private  EmissionTotal e;

    public EmissionTotalRepository(Application application) {
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "thinkarbon-db")
                .allowMainThreadQueries().fallbackToDestructiveMigration()   //Allows room to do operation on main thread
                .build();
        mEmissionTotalDao = db.getEmissionTotalDAO();
        emissions = mEmissionTotalDao.getAll();
    }

    public void insert(EmissionTotal emissionTotal) {
        if(checkExists()) {
            //if does exist then we want to select it and get
            e.setTotal(e.getTotal() + emissionTotal.getTotal());
            mEmissionTotalDao.update(e);
        } else {
            mEmissionTotalDao.insert(emissionTotal);
        }
    }

    //this might return null
    public EmissionTotal getTodayEmission() {
        CreateDate date = new CreateDate();
        return mEmissionTotalDao.getTodayEmission(date.now());
    }

    //this method checks if an emissiontotal object has been created today.
    private Boolean checkExists() {
        CreateDate date = new CreateDate();
        try {
            e = mEmissionTotalDao.checkExists(date.now());
            return e != null;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public void deleteAll(){
        mEmissionTotalDao.deleteAll();
    }
}
