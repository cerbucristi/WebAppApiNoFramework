package controllers;

import api.FlowerApi;
import enums.FlowerStatusEnum;
import exceptions.EmailSenderException;
import exceptions.NotFoundException;
import handlers.Response;
import models.Flower;
import models.FlowerListed;
import models.FlowerRequiredSpecs;
import services.FlowerService;
import services.FlowerSpecService;

import java.math.BigDecimal;
import java.util.List;

public class FlowerController implements FlowerApi {

    @Override
    public FlowerRequiredSpecs getFlowerSpec(String name) throws NotFoundException {
        FlowerRequiredSpecs flowerRequiredSpecs = FlowerSpecService.getFlowerSpecByName(name);

        if (flowerRequiredSpecs == null) {
            throw new NotFoundException(String.format("There are no specs data for flower with name: %s", name));
        }
        return flowerRequiredSpecs;
    }

    @Override
    public List<FlowerListed> getFlowersListed() throws NotFoundException {
        return FlowerService.getFlowersListed();
    }

    @Override
    public List<Flower> getFlowersByUserEmail(String email) {
        return FlowerService.findFlowersByUserMail(email);
    }

    @Override
    public List<Flower> getFlowers() throws NotFoundException {
        return FlowerService.getAllFlowers();
    }

    @Override
    public Flower getFlower(int id) throws NotFoundException {
        // Retrieve flower from the database based on the provided ID
        Flower flower = FlowerService.findFlowerById(id);
        if (flower == null) {
            throw new NotFoundException("Flower not found");
        }
        return flower;
    }

    @Override
    public Response createFlower(Flower flower) {
        // Save the new flower in the database
        FlowerService.saveFlower(flower);
        return Response.created();
    }

    @Override
    public Response listFlower(int id, BigDecimal price) throws NotFoundException {
        FlowerService.listFlower(id, price);
        return Response.ok();
    }

    @Override
    public Response sellFlower(int id, String sellerMail) throws NotFoundException {
        FlowerService.sellFlower(id, sellerMail);
        return Response.ok();
    }

    @Override
    public Response updateFlower(Flower flower) throws NotFoundException {
        // Retrieve the existing flower from the database based on the provided ID
        Flower existingFlower = FlowerService.findFlowerById(flower.getId());
        if (existingFlower == null) {
            throw new NotFoundException("Flower not found");
        }
        // Update the flower properties
        existingFlower.setName(flower.getName());
        existingFlower.setOwnerEmail(flower.getOwnerEmail());
        existingFlower.setPlantingDate(flower.getPlantingDate());
        existingFlower.setKind(flower.getKind());

        // Save the updated flower in the database
        FlowerService.updateFlower(existingFlower);
        return Response.ok();
    }

    @Override
    public Response deleteFlower(int id) throws NotFoundException {
        // Retrieve the flower from the database based on the provided ID
        Flower flower = FlowerService.findFlowerById(id);
        if (flower == null) {
            throw new NotFoundException("Flower not found");
        }
        // Delete the flower from the database
        FlowerService.deleteFlower(flower);
        return Response.ok();
    }
}
