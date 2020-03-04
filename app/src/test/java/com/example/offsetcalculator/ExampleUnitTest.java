package com.example.offsetcalculator;

import org.junit.Test;

import dto.CarEmission;
import dto.Emission;

import static junit.framework.TestCase.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testDto(){
        //TODO run tests on dto
        /* ################## test for carEmission ###################*/
        
        Emission carEmission = new CarEmission(20.0,52.0, 21.0);
        carEmission.calculateEmission();
        Double result = carEmission.getTotalEmission();
        Double resultInKilos = carEmission.emissionToKilograms();
        System.out.println("@@@ total emissions from a car -> " + result);
        System.out.println("@@@ total emissions in kilograms from a car -> " + resultInKilos);
        assertNotNull(result);
    }

    @Test
    public void testDB(){
        //TODO test database
    }
}