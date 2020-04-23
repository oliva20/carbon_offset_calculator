package com.example.offsetcalculator.model.decorator;

import android.util.Log;
import android.util.Pair;

import java.util.Map;

/*
* This class calculates the emission for different types of food
* This information was taken from this website: http://www.greeneatz.com/foods-carbon-footprint.html
*
 */
public class FoodEmissionDecorator extends EmissionDecorator{

    private static final String HUMAN_READABLE_NAME = "food";
    private String foodType;

    //food factors co2kg per 1 kg of food
    private static final double lamb = 39.2; //kg
    private static final double beef = 27.0;
    private static final double cheese = 13.5;
    private static final double pork = 12.1;
    private static final double turkey = 10.9;
    private static final double chicken = 6.9;
    private static final double tuna = 6.1;
    private static final double fish = tuna;
    private static final double eggs = 4.8;
    private static final double potatoes = 2.9;
    private static final double rice = 2.7;
    private static final double nuts = 2.3;
    private static final double beans = 2.0;
    private static final double tofu = 2.0;
    private static final double milk = 1.9;
    private static final double lentils = 0.9;
    private static final double FRUIT = 1.1;
    private static final double VEG = 2.0;
    private static final double RED_MEAT = (lamb + beef) / 2; //
    private static final double WHITE_MEAT = (pork + turkey + chicken) / 3;

    FoodEmissionDecorator(Emission e) {
        super(e);
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    @Override
    public Double calculate(Double grams) {
        // we must convert the grams to kilograms
        grams /= 1000;
        double result = 0.0;
        try {
            switch (foodType) {
                case "redMeat":
                    result = grams * RED_MEAT; //for 200 grams this should return 6.62 kg co2
                    break;
                case "whiteMeat":
                    result = grams * WHITE_MEAT; //returns co2 in kilograms
                    break;
                case "vegetables":
                    result = grams * VEG; //returns co2 in kilograms. Also this option can be used for people that are vegetarian.
                    break;
                case "fruit":
                    result = grams * FRUIT; //returns co2 in kilograms
                    break;
            }

        } catch (Exception e){
            throw new RuntimeException("Food type cannot be null, set the foodtype before calculating. Options {fruit, vegetables, redMeat, whiteMeat");
        }

        Log.d("@@@ " + foodType, String.valueOf(result));

        return super.calculate(grams) + result; // returns the result in co2 in kg.
    }

    @Override
    public String getType() {
        return super.getType();
    }

    @Override
    public String getHumanReadableName() {
        return HUMAN_READABLE_NAME;
    }
}
