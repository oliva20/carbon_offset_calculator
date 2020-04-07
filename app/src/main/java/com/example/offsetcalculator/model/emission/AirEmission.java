package com.example.offsetcalculator.model.emission;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DecimalFormat;

/** Represents and calculates a airplane journey emission.
 * @author Andre
 * @version 1.0
 * @since 1.0
 * CO2 emissions in pounds = air miles travelled per year × (average direct emissions per air mile × indirect well-to-pump factor × indirect atmospheric radiative forcing factor) × gram to pound conversion
 */
@Entity(tableName = "air_emissions")
public class AirEmission implements Emission {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Double emissionTotal;
    private Double milesPerYear;
    private Double avgEmissMile = 223.0; //average emission per mile
    private Double wellToPump = 1.2; //indirect well-to-pump factor
    private Double indirRadiForcingFactor = 1.9; // indirect atmospheric radiative forcing factor
    private Double gramToPound = 0.022;

    public AirEmission(Double milesPerYear) {
        this.milesPerYear = milesPerYear;
        DecimalFormat df = new DecimalFormat("##.##");
        Double total = (milesPerYear * (avgEmissMile * wellToPump * indirRadiForcingFactor)) * gramToPound;
        String result = df.format(total);
        emissionTotal = Double.valueOf(result);
    }

    @Override
    public Double getTotal() {
        return null;
    }

    @Override
    public Double totalEmissionToTons() {
        //number needs to be rounded to two decimal places.
        DecimalFormat df = new DecimalFormat("##.##");
        String x = df.format((emissionTotal/2.2046) * 0.001102);
        return Double.valueOf(x);
    }

    @Override
    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Double getEmissionTotal() { return emissionTotal; }

    public void setEmissionTotal(Double emissionTotal) { this.emissionTotal = emissionTotal; }

    public Double getMilesPerYear() { return milesPerYear; }

    public void setMilesPerYear(Double milesPerYear) { this.milesPerYear = milesPerYear; }

    public Double getAvgEmissMile() { return avgEmissMile; }

    public void setAvgEmissMile(Double avgEmissMile) { this.avgEmissMile = avgEmissMile; }

    public Double getWellToPump() { return wellToPump; }

    public void setWellToPump(Double wellToPump) { this.wellToPump = wellToPump; }

    public Double getIndirRadiForcingFactor() { return indirRadiForcingFactor; }

    public void setIndirRadiForcingFactor(Double indirRadiForcingFactor) { this.indirRadiForcingFactor = indirRadiForcingFactor; }

    public Double getGramToPound() { return gramToPound; }

    public void setGramToPound(Double gramToPound) { this.gramToPound = gramToPound; }

    public static AirEmission[] populateData() {
        return new AirEmission[] {
                new AirEmission(12.2),
                new AirEmission(32.2)
        };
    }

    @Override
    public String toString() {
        return "AirEmission{" +
                "id=" + id +
                ", emissionTotal=" + emissionTotal +
                ", milesPerYear=" + milesPerYear +
                ", avgEmissMile=" + avgEmissMile +
                ", wellToPump=" + wellToPump +
                ", indirRadiForcingFactor=" + indirRadiForcingFactor +
                ", gramToPound=" + gramToPound +
                '}';
    }
}
