package com.example.offsetcalculator.rep;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import com.example.offsetcalculator.dao.CoordinateDAO;
import com.example.offsetcalculator.dao.RouteDAO;
import com.example.offsetcalculator.db.AppDatabase;
import com.example.offsetcalculator.model.route.Coordinate;
import com.example.offsetcalculator.model.route.Route;

import java.util.ArrayList;
import java.util.List;

public class RouteRepository {
    private RouteDAO mRouteDao;
    private CoordinateDAO mCoodinateDao;

    public RouteRepository(Application application){
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "thinkarbon-db")
                .allowMainThreadQueries().fallbackToDestructiveMigration()   //Allows room to do operation on main thread
                .build();
        mRouteDao = db.getRouteDAO();
        mCoodinateDao = db.getCoordinateDAO();
    }

    public void insert(Route route, List<Coordinate> coordinates) {
        if(route != null) {
            mRouteDao.insert(route); // route must be inserted first.
            //loop through the coordinates and insert them with the route's id
            for (int i = 0; i < coordinates.size(); i++) {
                mCoodinateDao.insert(coordinates.get(i));
            }
        } else {
            Log.d("ERROR", "Route must not be null!");
            throw new RuntimeException();
        }
    }

    public void update(Route route) {
        mRouteDao.update(route);
    }

    public void delete(Route route) {
        mRouteDao.delete(route);
    }

    public Route getRouteById(Integer id) {
       return mRouteDao.getRouteById(id);
    }

    public List<Coordinate> getCoordinatesFromRoute(Route route) {
        if(route.getId() != null ){
            Log.d("SUCCESS", "Coordinates have been loaded");
            return  mCoodinateDao.findCoordinatesForRoute(route.getId());
        } else {
            Log.d("FAILURE", "Route has no id");
            throw new RuntimeException();
        }
    }

}
