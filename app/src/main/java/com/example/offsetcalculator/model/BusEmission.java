package com.example.offsetcalculator.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DecimalFormat;

/** Represents and calculates a bus emission.
 * @author Andre
 * @version 1.0
 * @since 1.0
 * CO2 emissions in pounds = (miles travelled per year × public transportation direct emissions) + (public transportation direct emissions × indirect emissions multiplication factor) × gram to pound conversion
 */
@Entity(tableName = "bus_emissions")
public class BusEmission implements Emission {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Double emissionTotal; // co2 emissions in pounds
    private Double milesPerYear;
    //TODO get real value for publicTranspDirectEmiss
    private Double publicTranspDirectEmiss = 14.0; //example value
    //TODO get real value for publicTranspIndirEmiss
    private Double publicTranspIndirEmiss = 12.0; //example value
    private Double carbonEmittedPerGallon = 19.4; //according to carbonglobe.com
    private Double gramToPound = 0.022; // according to carbonglobe.com
    private Double otherEmissions = 1.05; // according to carbonglobe.com

    public BusEmission(Double milesPerYear) {
        this.milesPerYear = milesPerYear;
        //calculate method should be called everytime a emission object is initialized
        calculateEmission();
    }

    @Override
    public Integer getId() {
        return null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void calculateEmission() {
        DecimalFormat df = new DecimalFormat("##.##");
        Double total = ((milesPerYear * publicTranspDirectEmiss) + (publicTranspDirectEmiss * publicTranspIndirEmiss)) * gramToPound;
        String result = df.format(total);
        emissionTotal = Double.valueOf(result);
    }

    @Override
    public Double getTotalEmission() {
        return emissionTotal;
    }

    @Override
    public Double emissionToKilograms() {
        //number needs to be rounded to two decimal places.
        DecimalFormat df = new DecimalFormat("##.##");
        String x = df.format(emissionTotal/2.2046);
        return Double.valueOf(x);
}

    public Double getEmissionTotal() {
        return emissionTotal;
    }

    public void setEmissionTotal(Double emissionTotal) {
        this.emissionTotal = emissionTotal;
    }

    public Double getMilesPerYear() {
        return milesPerYear;
    }

    public void setMilesPerYear(Double milesPerYear) {
        this.milesPerYear = milesPerYear;
    }

    public Double getPublicTranspDirectEmiss() {
        return publicTranspDirectEmiss;
    }

    public void setPublicTranspDirectEmiss(Double publicTranspDirectEmiss) { this.publicTranspDirectEmiss = publicTranspDirectEmiss; }

    public Double getPublicTranspIndirEmiss() { return publicTranspIndirEmiss; }

    public void setPublicTranspIndirEmiss(Double publicTranspIndirEmiss) { this.publicTranspIndirEmiss = publicTranspIndirEmiss; }

    public Double getGramToPound() {
        return gramToPound;
    }

    public void setGramToPound(Double gramToPound) {
        this.gramToPound = gramToPound;
    }

    public Double getCarbonEmittedPerGallon() {
        return carbonEmittedPerGallon;
    }

    public void setCarbonEmittedPerGallon(Double carbonEmittedPerGallon) { this.carbonEmittedPerGallon = carbonEmittedPerGallon; }

    public Double getOtherEmissions() {
        return otherEmissions;
    }

    public void setOtherEmissions(Double otherEmissions) {
        this.otherEmissions = otherEmissions;
    }

    @Override
    public String toString() {
        return "BusEmission{" +
                "id=" + id +
                ", emissionTotal=" + emissionTotal +
                ", milesPerYear=" + milesPerYear +
                ", publicTranspDirectEmiss=" + publicTranspDirectEmiss +
                ", publicTranspIndirEmiss=" + publicTranspIndirEmiss +
                ", gramToPound=" + gramToPound +
                '}';
    }
}
