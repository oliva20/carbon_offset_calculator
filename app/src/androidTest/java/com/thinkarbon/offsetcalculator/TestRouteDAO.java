package com.thinkarbon.offsetcalculator;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.thinkarbon.offsetcalculator.dao.CoordinateDAO;
import com.thinkarbon.offsetcalculator.dao.RouteDAO;
import com.thinkarbon.offsetcalculator.db.AppDatabase;
import com.thinkarbon.offsetcalculator.model.route.Coordinate;
import com.thinkarbon.offsetcalculator.model.route.Route;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestRouteDAO {
    private RouteDAO routeDao;
    private CoordinateDAO coordinateDao;
    private AppDatabase db;

//    @Test
//    public void useAppContext() {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//
//        assertEquals("com.example.offsetcalculator", appContext.getPackageName());
//    }

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        routeDao = db.getRouteDAO();
        coordinateDao = db.getCoordinateDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeRouteAndRetrieve() throws Exception {
        //create a route
        Route r1 = new Route(1, new Date().toString());
        r1.setTimestamp(new Date());
        Route r2 = new Route(2, new Date().toString());

        //create coordinates for r1
        coordinateDao.insert(new Coordinate(0.1,0.2, r1.getId()));
        coordinateDao.insert(new Coordinate(0.5,0.9, r1.getId()));
        coordinateDao.insert(new Coordinate(0.7,0.3, r1.getId()));

        //create coordinates for r2
        coordinateDao.insert(new Coordinate(1.1,1.2, r2.getId()));
        coordinateDao.insert(new Coordinate(1.5,1.9, r2.getId()));
        coordinateDao.insert(new Coordinate(1.7,1.3, r2.getId()));

        //save routes
        routeDao.insert(r1);
        routeDao.insert(r2);

        /* ------------------------------------------------ */

        List<Coordinate> coordsForR1 = coordinateDao.findCoordinatesForRoute(r1.getId());
        List<Coordinate> coordsForR2 = coordinateDao.findCoordinatesForRoute(r2.getId());

        for(int i = 0; i < coordsForR1.size(); i++){
            System.out.println("Coordinates for the first route");
            System.out.println(coordsForR1.get(i).toString());
        }

        for(int i = 0; i < coordsForR2.size(); i++){
            System.out.println("Coordinates for the second route");
            System.out.println(coordsForR2.get(i).toString());
        }

        /* ------------------------------------------------ */

        assertNotNull(coordsForR1);
        assertNotNull(coordsForR2);

        routeDao.deleteAllRoutes();
    }


}
