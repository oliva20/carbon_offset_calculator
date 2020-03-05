package com.example.offsetcalculator.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DecimalFormat;

/** Represents and calculates a car emission.
 * @author Andre
 * @version 1.0
 * @since 1.0
 */
@Entity(tableName = "car_emissions")
public class CarEmission implements Emission{
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Double emissionTotal; // co2 emissions in pounds
    private Double milesDrivenWeekly;
    private Double weeksInYear = 54.0;
    private Double vehicleFuelEfficiency;
    private Double carbonEmittedPerGallon = 19.4; // according to carbonglobe.com
    private Double otherEmissions = 1.05; // according to carbonglobe.com


    public CarEmission(Double milesDrivenWeekly, Double vehicleFuelEfficiency) {
        this.milesDrivenWeekly = milesDrivenWeekly;
        this.vehicleFuelEfficiency = vehicleFuelEfficiency;
        calculateEmission();
    }

    @Override
    public Double emissionToKilograms() {
        //number needs to be rounded to two decimal places.
        DecimalFormat df = new DecimalFormat("##.##");
        String x = df.format(emissionTotal/2.2046);
        return Double.valueOf(x);
    }

    @Override
    public Double getTotalEmission() {
        return emissionTotal;
    }

    /** Calculates the Co2 emissions in pounds per week.
     */
    @Override
    public void calculateEmission() {
        DecimalFormat df = new DecimalFormat("##.##");
        Double total = ((milesDrivenWeekly * weeksInYear) / vehicleFuelEfficiency) * carbonEmittedPerGallon * otherEmissions;
        String result = df.format(total);
        emissionTotal = Double.valueOf(result);
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getEmissionTotal() {
        return emissionTotal;
    }

    public void setEmissionTotal(Double emissionTotal) {
        this.emissionTotal = emissionTotal;
    }

    public Double getMilesDrivenWeekly() {
        return milesDrivenWeekly;
    }

    public Double getWeeksInYear() {
        return weeksInYear;
    }

    public Double getVehicleFuelEfficiency() {
        return vehicleFuelEfficiency;
    }

    public Double getCarbonEmittedPerGallon() {
        return carbonEmittedPerGallon;
    }

    public Double getOtherEmissions() {
        return otherEmissions;
    }

    public void setMilesDrivenWeekly(Double milesDrivenWeekly) {
        this.milesDrivenWeekly = milesDrivenWeekly;
    }

    public void setWeeksInYear(Double weeksInYear) {
        this.weeksInYear = weeksInYear;
    }

    public void setVehicleFuelEfficiency(Double vehicleFuelEfficiency) {
        this.vehicleFuelEfficiency = vehicleFuelEfficiency;
    }

    public void setCarbonEmittedPerGallon(Double carbonEmittedPerGallon) {
        this.carbonEmittedPerGallon = carbonEmittedPerGallon;
    }

    public void setOtherEmissions(Double otherEmissions) {
        this.otherEmissions = otherEmissions;
    }

    @Override
    public String toString() {
        return "CarEmission{" +
                "emissionTotal=" + emissionTotal +
                ", milesDrivenWeekly=" + milesDrivenWeekly +
                ", weeksInYear=" + weeksInYear +
                ", vehicleFuelEfficiency=" + vehicleFuelEfficiency +
                ", carbonEmittedPerGallon=" + carbonEmittedPerGallon +
                ", otherEmissions=" + otherEmissions +
                '}';
    }
}
