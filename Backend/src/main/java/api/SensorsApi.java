package api;

import annotations.GET;
import annotations.Path;
import annotations.PathParam;
import exceptions.NotFoundException;
import models.FlowerActualSpecs;


public interface SensorsApi {

    @GET
    @Path("/sensorValuesForFlower/{id}")
    FlowerActualSpecs getFlowerActualSpecs (@PathParam("id") int id) throws NotFoundException;

}
