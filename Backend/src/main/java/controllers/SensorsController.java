package controllers;

import api.SensorsApi;
import dataprovider.Sensors;
import exceptions.NotFoundException;
import models.Flower;
import models.FlowerActualSpecs;
import services.FlowerService;

public class SensorsController implements SensorsApi {

    @Override
    public FlowerActualSpecs getFlowerActualSpecs(int id) throws NotFoundException {
        Flower flower = FlowerService.findFlowerById(id);
        if (flower == null) {
            throw new NotFoundException("Flower not found");
        }

        Sensors sensors = new Sensors(flower);
        return sensors.getFlowerActualSpecs();
    }
}
