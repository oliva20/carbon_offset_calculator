package com.example.offsetcalculator.rep;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import androidx.room.Room;

import com.example.offsetcalculator.dao.CoordinateDAO;
import com.example.offsetcalculator.dao.RouteDAO;
import com.example.offsetcalculator.db.AppDatabase;
import com.example.offsetcalculator.model.route.Coordinate;
import com.example.offsetcalculator.model.route.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteRepository {
    private RouteDAO mRouteDao;
    private CoordinateDAO mCoodinateDao;
    private List<Route> mAllRoutes;

    public RouteRepository(Application application){
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "thinkarbon-db")
                .allowMainThreadQueries().fallbackToDestructiveMigration()   //Allows room to do operation on main thread
                .build();
        mRouteDao = db.getRouteDAO();
        mCoodinateDao = db.getCoordinateDAO();
        mAllRoutes = mRouteDao.getAllRoutes();
    }

    public List<Route> getmAllRoutes(){
        return mAllRoutes;
    }

    public void insert(Route route, List<Coordinate> coordinates) {
        if(route != null) {
            mRouteDao.insert(route); // route must be inserted first.
            Log.d("@@@ RouteRep", "Inserted route " + route.toString());

            //loop through the coordinates and insert them with the route's id
            for (int i = 0; i < coordinates.size(); i++) {
                mCoodinateDao.insert(coordinates.get(i));
                Log.d("@@@ RouteRep", "Inserted coordinate" + coordinates.get(i).toString());
            }
        } else {
            Log.d("ERROR", "Route must not be null!");
            throw new NullPointerException();
        }
    }

    public void update(Route route) {
        mRouteDao.update(route);
    }

    public void updateCoordinate(Coordinate coordinate) {
        mCoodinateDao.update(coordinate);
    }

    public void delete(Route route) {
        mRouteDao.delete(route);
    }

    public void deleteAll() {
        mRouteDao.deleteAllRoutes();
        mCoodinateDao.deleteAll();
    }


    public Route getLastInsertedRoute(){
         List<Route> l =  mRouteDao.getAllRoutes();
         try {
             return l.get(l.size() - 1);
         } catch (Exception e){
             System.out.println(e);
             return null;
         }
    }

    public Route getRouteById(Integer id) {
       return mRouteDao.getRouteById(id);
    }

    public List<Coordinate> getCoordinatesFromRoute(int routeId) {
            Log.d("RouteRep", "Getting coorinates from route with id = " + routeId);
            return  mCoodinateDao.findCoordinatesForRoute(routeId);
    }


    public Integer generateId(){
        if(getLastInsertedRoute() != null){
            return getLastInsertedRoute().getId() + 1;
        } else {
            return  1;
        }
    }


}
