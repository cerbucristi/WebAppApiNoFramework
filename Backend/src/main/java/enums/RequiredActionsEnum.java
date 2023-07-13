package enums;

public enum RequiredActionsEnum {

    requires_more_humidity("Adjust Humidity, level is LOWER than expected"),
    requires_less_humidity("Adjust Humidity, level is HIGHER than expected"),
    requires_less_light("Adjust Luminosity, value is HIGHER than expected"),
    requires_more_light("Adjust Luminosity, value is LOWER than expected"),
    requires_harvest("Plant is ready to be harvested"),
    no_action_required("No action required");

    private final String value;

    RequiredActionsEnum (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RequiredActionsEnum getByValue(String value) {
        for (RequiredActionsEnum action : RequiredActionsEnum.values()) {
            if (value.startsWith(action.getValue())) {
                return action;
            }
        }
        throw new IllegalArgumentException("Invalid value for RequiredActionsEnum: " + value);
    }
}
