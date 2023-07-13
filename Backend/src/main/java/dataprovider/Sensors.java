package dataprovider;

import enums.RequiredActionsEnum;
import models.Flower;
import models.FlowerActualSpecs;
import models.FlowerDynamicSpecs;
import models.FlowerRequiredSpecs;
import services.ActionsService;
import services.FlowerSpecService;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Sensors {
    private final Random sensorValue;
    private final FlowerRequiredSpecs flowerRequiredSpecs;
    private final Flower flower;


    public Sensors(Flower flower) {
        this.sensorValue = new Random();
        this.flowerRequiredSpecs = FlowerSpecService.getFlowerSpecByName(flower.getName());
        this.flower = flower;
    }

    public FlowerActualSpecs getFlowerActualSpecs () {

        FlowerDynamicSpecs flowerDynamicSpecs;

        if (SensorsValuesHolder.getInstance().getSensorValuesHolder().containsKey(flower.getId())) {
            flowerDynamicSpecs = SensorsValuesHolder.getInstance().getSensorValuesHolder().get(flower.getId());
        } else {
            int minTemp = 0;
            int maxTemp = 30;

            int minHumid = 20;
            int maxHumid = 80;

            int temperature = sensorValue.nextInt(maxTemp - minTemp +1) + minTemp;
            int humidity = sensorValue.nextInt(maxHumid - minHumid + 1) + minHumid;
            FlowerDynamicSpecs flowerDynamicSpecsToBeAdded = new FlowerDynamicSpecs();
            flowerDynamicSpecsToBeAdded.setHumidityPercent(humidity);
            flowerDynamicSpecsToBeAdded.setTemperatureCelsiusGrades(temperature);
            SensorsValuesHolder.getInstance().getSensorValuesHolder().put(flower.getId(), flowerDynamicSpecsToBeAdded);
            flowerDynamicSpecs = flowerDynamicSpecsToBeAdded;
        }



        int days = calculateDaysNumber(flower.getPlantingDate());

        FlowerActualSpecs flowerActualSpecs = new FlowerActualSpecs();
        flowerActualSpecs.setHumidityPercent(flowerDynamicSpecs.getHumidityPercent());
        flowerActualSpecs.setTemperatureCelsiusGrades(flowerDynamicSpecs.getTemperatureCelsiusGrades());
        flowerActualSpecs.setDaysPassedFromPlantedDate(days);
        flowerActualSpecs.setActionsHistory(ActionsService.getActionsOnFlower(flower.getId()));

        if (days > flowerRequiredSpecs.getRequiredDays()) {
            flowerActualSpecs.setActionRequired(RequiredActionsEnum.requires_harvest.getValue());
            return flowerActualSpecs;
        }


        if (flowerActualSpecs.getTemperatureCelsiusGrades() != flowerRequiredSpecs.getRequiredTemp() || flowerActualSpecs.getHumidityPercent() != flowerRequiredSpecs.getRequiredHumidPercent()) {

            int tempSituation;
            int humidSituation;

            tempSituation = Math.abs(flowerActualSpecs.getTemperatureCelsiusGrades() - flowerRequiredSpecs.getRequiredTemp());
            humidSituation = Math.abs(flowerActualSpecs.getHumidityPercent() - flowerRequiredSpecs.getRequiredHumidPercent());

            if (tempSituation >= humidSituation) {
                if (flowerActualSpecs.getTemperatureCelsiusGrades() > flowerRequiredSpecs.getRequiredTemp()) {
                    flowerActualSpecs.setActionRequired(String.format(RequiredActionsEnum.requires_less_light.getValue() + "(normal value:%s)",
                            flowerRequiredSpecs.getRequiredTemp()));
                    return flowerActualSpecs;
                }

                if (flowerActualSpecs.getTemperatureCelsiusGrades() < flowerRequiredSpecs.getRequiredTemp()) {
                    flowerActualSpecs.setActionRequired(String.format(RequiredActionsEnum.requires_more_light.getValue() + "(normal value:%s)",
                            flowerRequiredSpecs.getRequiredTemp()));
                    return flowerActualSpecs;
                }
            } else {
                if (flowerActualSpecs.getHumidityPercent() > flowerRequiredSpecs.getRequiredHumidPercent()) {
                    flowerActualSpecs.setActionRequired(String.format(RequiredActionsEnum.requires_less_humidity.getValue() + "(normal value:%s)",
                            flowerRequiredSpecs.getRequiredHumidPercent()));
                    return flowerActualSpecs;
                }

                if (flowerActualSpecs.getHumidityPercent() < flowerRequiredSpecs.getRequiredHumidPercent()) {
                    flowerActualSpecs.setActionRequired(String.format(RequiredActionsEnum.requires_more_humidity.getValue() + "(normal value:%s)",
                            flowerRequiredSpecs.getRequiredHumidPercent()));
                    return flowerActualSpecs;
                }
            }
        }

        flowerActualSpecs.setActionRequired(RequiredActionsEnum.no_action_required.getValue());
        return flowerActualSpecs;
    }

    private int calculateDaysNumber (Date plantedDate) {
        Date dateNow = new Date();

        if (plantedDate.after(dateNow)) {
            throw new IllegalArgumentException("Planted date cannot be in the future");
        }

        long diffInMillies = Math.abs(dateNow.getTime() - plantedDate.getTime());
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
