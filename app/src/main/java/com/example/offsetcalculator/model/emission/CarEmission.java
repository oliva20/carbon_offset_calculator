package com.example.offsetcalculator.model.emission;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DecimalFormat;

/** Represents and calculates a car emission.
 * @author Andre
 * @version 1.0
 * @since 1.0
 * CO2 emissions in pounds = ((number of miles driven per week × weeks in a year) ÷ fuel efficiency per vehicle) × pounds of CO2 emitted per gallon × emissions of greenhouse gases other than CO2
 */
@Entity(tableName = "car_emissions")
public class CarEmission implements Emission{
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(name = "emission_total")
    private Double emissionTotal; // co2 emissions in pounds
    private Double milesDrivenWeekly;
    private Double vehicleFuelEfficiency;
    private Double weeksInYear = 54.0;
    private Double carbonEmittedPerGallon = 19.4; // according to carbonglobe.com
    private Double otherEmissions = 1.05; // according to carbonglobe.com

    /** Calculates the Co2 emissions in pounds per week.
     */
    public CarEmission(Double milesDrivenWeekly, Double vehicleFuelEfficiency) {
        this.milesDrivenWeekly = milesDrivenWeekly;
        this.vehicleFuelEfficiency = vehicleFuelEfficiency;
        DecimalFormat df = new DecimalFormat("##.##");
        Double total = ((milesDrivenWeekly * weeksInYear) / vehicleFuelEfficiency) * carbonEmittedPerGallon * otherEmissions;
        String result = df.format(total);
        emissionTotal = Double.valueOf(result);
    }

    @Override
    public Double totalEmissionToTons() {
        //changing to kilos and then to tons.
        //kilos = pounds / 2.2046
        //tons = kilos * 0.0001102

        //number needs to be rounded to two decimal places.
        DecimalFormat df = new DecimalFormat("##.##");
        String x = df.format((emissionTotal/2.2046) * 0.001102);
        return Double.valueOf(x);
    }

    @Override
    public Double getTotal() {
        return emissionTotal;
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
