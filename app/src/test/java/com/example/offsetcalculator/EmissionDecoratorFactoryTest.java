package com.example.offsetcalculator;

import com.example.offsetcalculator.model.decorator.Emission;
import com.example.offsetcalculator.model.factory.EmissionDecoratorFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Set;

@RunWith(JUnit4.class)
public class EmissionDecoratorFactoryTest {

    @Test
    public void test(){
        System.out.println("start of test");
        Set<String> humanReadableNames = EmissionDecoratorFactory.getHumanReadableNames();
        System.out.println("Here are all the Human Readable Names of known EmissionDecorators in the factory\n"+humanReadableNames);
        System.out.println("\nhere are the classes instantiated for each name:");
        for(String humanReadableName:humanReadableNames) {
            Emission ed = EmissionDecoratorFactory.getDecoForHumanReadableName(humanReadableName);
            System.out.println("humanReadableName="+humanReadableName+" ed="+ed.getClass().getName());
        }
        System.out.println("end of test");
    }

}
