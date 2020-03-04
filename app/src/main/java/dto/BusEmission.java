package dto;

public class BusEmission implements Emission {

    private Double emissionTotal;

    @Override
    public Double emissionToKilograms() {
        return emissionTotal / 2.2046;
    }

    @Override
    public Double getTotalEmission() {
        return null;
    }

    @Override
    public void calculateEmission() {

    }

    public Double getEmissionTotal() {
        return emissionTotal;
    }

    public void setEmissionTotal(Double emissionTotal) {
        this.emissionTotal = emissionTotal;
    }
}
