package api;

import annotations.*;
import exceptions.NotFoundException;
import handlers.Response;
import models.FlowerFavoriteType;
import models.User;

import java.util.List;

public interface UserApi {
    @GET
    @Path("/users")
    User getUserByEmail(@PathParam("email") String email) throws NotFoundException;

    @PUT
    @Path("/users")
    Response updateUser(@RequestBody User user) throws NotFoundException;

    @DELETE
    @Path("/users")
    Response deleteUser(String email) throws NotFoundException;

    @POST
    @Path("/users/addToWishList/{flowerName}")
    Response addFlowerOnWishlist (@PathParam("flowerName") String flowerName, String email);

    @DELETE
    @Path("/users/removeFlowerFromWishList/{flowerName}")
    Response removeFlowerFromWishlist(@PathParam("flowerName") String flowerName, String email);

    @GET
    @Path("/users/getUserFavoriteFlowers")
    List<FlowerFavoriteType> getUserFavoriteFlowers (String email);
}
