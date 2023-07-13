package controllers;

import api.FlowerActionsApi;
import dataprovider.SensorsValuesHolder;
import enums.RequiredActionsEnum;
import handlers.Response;
import models.Flower;
import services.ActionsService;
import services.FlowerService;
import services.FlowerSpecService;

import java.util.Objects;

public class FlowerActionsController implements FlowerActionsApi {
    @Override
    public Response takeAction(int id, String action) {
        ActionsService.insertAction(id, action);

        Flower flower = FlowerService.findFlowerById(id);

        RequiredActionsEnum actionTook = RequiredActionsEnum.getByValue(action);
        if (actionTook.equals(RequiredActionsEnum.requires_more_humidity) || actionTook.equals(RequiredActionsEnum.requires_less_humidity)) {
            assert flower != null;
            SensorsValuesHolder.getInstance().getSensorValuesHolder().get(id).setHumidityPercent(Objects.requireNonNull(FlowerSpecService.getFlowerSpecByName(flower.getName())).getRequiredHumidPercent());
        }

        if (actionTook.equals(RequiredActionsEnum.requires_less_light) || actionTook.equals(RequiredActionsEnum.requires_more_light)) {
            assert flower != null;
            SensorsValuesHolder.getInstance().getSensorValuesHolder().get(id).setTemperatureCelsiusGrades(Objects.requireNonNull(FlowerSpecService.getFlowerSpecByName(flower.getName())).getRequiredTemp());
        }

        return Response.ok();
    }
}
