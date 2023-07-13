package models;

public class FlowerRequiredSpecs {
    private String name;
    private int requiredDays;
    private int requiredTemp;
    private int requiredHumidPercent;

    public FlowerRequiredSpecs() {
    }

    public FlowerRequiredSpecs(String name, int requiredDays, int requiredTemp, int requiredHumidPercent) {
        this.name = name;
        this.requiredDays = requiredDays;
        this.requiredTemp = requiredTemp;
        this.requiredHumidPercent = requiredHumidPercent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRequiredDays() {
        return requiredDays;
    }

    public void setRequiredDays(int requiredDays) {
        this.requiredDays = requiredDays;
    }

    public int getRequiredTemp() {
        return requiredTemp;
    }

    public void setRequiredTemp(int requiredTemp) {
        this.requiredTemp = requiredTemp;
    }

    public int getRequiredHumidPercent() {
        return requiredHumidPercent;
    }

    public void setRequiredHumidPercent(int requiredHumidPercent) {
        this.requiredHumidPercent = requiredHumidPercent;
    }
}
