package models;

public class FlowerDynamicSpecs {
    private int humidityPercent;
    private int temperatureCelsiusGrades;

    public FlowerDynamicSpecs(int humidityPercent, int temperatureCelsiusGrades) {
        this.humidityPercent = humidityPercent;
        this.temperatureCelsiusGrades = temperatureCelsiusGrades;
    }

    public FlowerDynamicSpecs() {
    }

    public int getHumidityPercent() {
        return humidityPercent;
    }

    public void setHumidityPercent(int humidityPercent) {
        this.humidityPercent = humidityPercent;
    }

    public int getTemperatureCelsiusGrades() {
        return temperatureCelsiusGrades;
    }

    public void setTemperatureCelsiusGrades(int temperatureCelsiusGrades) {
        this.temperatureCelsiusGrades = temperatureCelsiusGrades;
    }
}
