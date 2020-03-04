package dto;

import java.text.DecimalFormat;

/** Represents an employee.
 * @author Andre Brasil
 * @version 1.5
 * @since 1.0
 */
public class CarEmission implements Emission{

    private Double emissionTotal; // co2 emissions in pounds
    private Double milesDrivenWeekly;
    private Double weeksInYear;
    private Double vehicleFuelEfficiency;
    private Double carbonEmittedPerGallon = 19.4; // according to carbonglobe.com
    private Double otherEmissions = 1.05; // according to carbonglobe.com

    public CarEmission(Double milesDrivenWeekly, Double weeksInYear, Double vehicleFuelEfficiency) {
        this.milesDrivenWeekly = milesDrivenWeekly;
        this.weeksInYear = weeksInYear;
        this.vehicleFuelEfficiency = vehicleFuelEfficiency;
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
        emissionTotal = ((milesDrivenWeekly * weeksInYear) / vehicleFuelEfficiency) * carbonEmittedPerGallon * otherEmissions;
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

    public void setMilesDrivenWeekly(Double milesDrivenWeekly) {
        this.milesDrivenWeekly = milesDrivenWeekly;
    }

    public Double getWeeksInYear() {
        return weeksInYear;
    }

    public void setWeeksInYear(Double weeksInYear) {
        this.weeksInYear = weeksInYear;
    }

    public Double getVehicleFuelEfficiency() {
        return vehicleFuelEfficiency;
    }

    public void setVehicleFuelEfficiency(Double vehicleFuelEfficiency) {
        this.vehicleFuelEfficiency = vehicleFuelEfficiency;
    }

    public Double getCarbonEmittedPerGallon() {
        return carbonEmittedPerGallon;
    }

    public void setCarbonEmittedPerGallon(Double carbonEmittedPerGallon) {
        this.carbonEmittedPerGallon = carbonEmittedPerGallon;
    }

    public Double getOtherEmissions() {
        return otherEmissions;
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
