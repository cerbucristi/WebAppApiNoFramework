package models;


import java.util.List;

public class FlowerActualSpecs extends  FlowerDynamicSpecs{
    private int daysPassedFromPlantedDate;
    private String actionRequired;

    private List<String> actionsHistory;

    public FlowerActualSpecs(int humidityPercent, int temperatureCelsiusGrades, int daysPassedFromPlantedDate, String actionRequired, List<String> actionsHistory) {
        super(humidityPercent, temperatureCelsiusGrades);
        this.daysPassedFromPlantedDate = daysPassedFromPlantedDate;
        this.actionRequired = actionRequired;
        this.actionsHistory = actionsHistory;
    }

    public FlowerActualSpecs() {
    }


    public int getDaysPassedFromPlantedDate() {
        return daysPassedFromPlantedDate;
    }

    public void setDaysPassedFromPlantedDate(int daysPassedFromPlantedDate) {
        this.daysPassedFromPlantedDate = daysPassedFromPlantedDate;
    }

    public String getActionRequired() {
        return actionRequired;
    }

    public void setActionRequired(String actionRequired) {
        this.actionRequired = actionRequired;
    }

    public List<String> getActionsHistory() {
        return actionsHistory;
    }

    public void setActionsHistory(List<String> actionsHistory) {
        this.actionsHistory = actionsHistory;
    }
}
