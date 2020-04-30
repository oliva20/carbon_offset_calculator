package com.thinkarbon.offsetcalculator.model.factory;

import com.thinkarbon.offsetcalculator.model.decorator.BaseEmission;
import com.thinkarbon.offsetcalculator.model.decorator.BusEmissionDecorator;
import com.thinkarbon.offsetcalculator.model.decorator.CarEmissionDecorator;
import com.thinkarbon.offsetcalculator.model.decorator.Emission;
import com.thinkarbon.offsetcalculator.model.decorator.EmissionDecorator;
import com.thinkarbon.offsetcalculator.model.decorator.FoodEmissionDecorator;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmissionDecoratorFactory {

    private static Map<String, String> factoryMap = null;

    private static List<String> decoratorTypeNames = Arrays.asList(
            CarEmissionDecorator.class.getName(),
            BusEmissionDecorator.class.getName(),
            FoodEmissionDecorator.class.getName()
            // add other decorators here
    );

    private static void init() {
        // creates new factory map once if not already created
        if (factoryMap == null)
            synchronized (EmissionDecoratorFactory.class) {
                if (factoryMap == null) {
                    factoryMap = new HashMap<String, String>();
                    for (String className : decoratorTypeNames) {
                        EmissionDecorator ed;
                        try {
                            Constructor ct = Class.forName(className).getDeclaredConstructor(Emission.class);
                            ct.setAccessible(true);
                            ed = (EmissionDecorator) ct.newInstance(new BaseEmission());
                            factoryMap.put(ed.getHumanReadableName(), className);
                        } catch (Exception e) {
                            System.out.println(e.toString());
                            throw new RuntimeException("problem instantiating className=" + className);
                        }

                    }
                }
            }

    }

    public static Set<String> getHumanReadableNames() {
        init();
        return factoryMap.keySet();
    }

    public static EmissionDecorator getDecoForHumanReadableName(String humanReadableName) {
        init();
        String className = null;
        EmissionDecorator ed;
        try {
            className = factoryMap.get(humanReadableName);
            Constructor ct = Class.forName(className).getDeclaredConstructor(Emission.class);
            ct.setAccessible(true);
            ed = (EmissionDecorator) ct.newInstance(new BaseEmission());
            return ed;

        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException(
                    "cannot instantiate class className=" + className + " for humanReadableName=" + humanReadableName);
        }

    }

}
