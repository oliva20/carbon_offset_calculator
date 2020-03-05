package com.example.offsetcalculator;

import org.junit.Test;

import com.example.offsetcalculator.model.BusEmission;
import com.example.offsetcalculator.model.CarEmission;

import static junit.framework.TestCase.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testDto(){
        System.out.println("############### Car ###################");
        CarEmission carEmission = new CarEmission(20.0,52.0);
        carEmission.calculateEmission();
        Double result = carEmission.getTotalEmission();
        Double resultInKilos = carEmission.emissionToKilograms();
        System.out.println("@@@ total emissions from a car -> " + result);
        System.out.println("@@@ total emissions in kilograms from a car -> " + resultInKilos);
        System.out.println(carEmission.toString());
        assertNotNull(result);

        System.out.println("############### Bus ###################");
        // bus emissions test
        BusEmission busEmission = new BusEmission(131.0);
        busEmission.calculateEmission();
        result = busEmission.getEmissionTotal();
        resultInKilos = busEmission.emissionToKilograms();
        System.out.println("@@@ total emissions from a bus -> " + result);
        System.out.println("@@@ total emissions in kilograms from a bus -> " + resultInKilos);
        System.out.println(busEmission.toString());
        assertNotNull(result);

    }
}