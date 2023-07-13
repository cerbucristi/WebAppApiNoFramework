package api;

import annotations.*;
import exceptions.NotFoundException;
import handlers.Response;
import models.Flower;
import models.FlowerListed;
import models.FlowerRequiredSpecs;

import java.math.BigDecimal;
import java.util.List;

public interface FlowerApi {


    @GET
    @Path("flowersSpec/{name}")
    FlowerRequiredSpecs getFlowerSpec (@PathParam("name") String name) throws NotFoundException;
    @GET
    @Path("flowers/listed")
    List<FlowerListed> getFlowersListed() throws NotFoundException;

    @GET
    @Path("/flowersByEmail/{email}")
    List<Flower> getFlowersByUserEmail (@PathParam("email") String email);

    @GET
    @Path("/flowers/all")
    List<Flower> getFlowers() throws NotFoundException;

    @GET
    @Path("/flowers/{id}")
    Flower getFlower(@PathParam("id") int id) throws NotFoundException;

    @POST
    @Path("/flowers")
    Response createFlower(@RequestBody Flower flower);

    @POST
    @Path("/flowers/listFlower/{id}")
    Response listFlower(@PathParam("id") int id, @RequestBody BigDecimal price) throws NotFoundException;

    @POST
    @Path("/flowers/sellFlower/{id}")
    Response sellFlower(@PathParam("id") int id, String sellerMail) throws NotFoundException;

    @PUT
    @Path("/flowers")
    Response updateFlower(@RequestBody Flower flower) throws NotFoundException;

    @DELETE
    @Path("/flowers/{id}")
    Response deleteFlower(@PathParam("id") int id) throws NotFoundException;
    
}
