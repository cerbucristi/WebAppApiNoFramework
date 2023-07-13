package controllers;

import api.UserApi;
import exceptions.NotFoundException;
import handlers.Response;
import models.FlowerFavoriteType;
import models.User;
import services.UserService;

import java.util.List;

public class UserController implements UserApi {
    @Override
    public User getUserByEmail(String email) throws NotFoundException {
        User user = UserService.findUserByEmail(email);
        if (user == null) {
            throw new NotFoundException(String.format("User with email : %s not found", email));
        }
        return user;
    }

    @Override
    public Response updateUser(User user) throws NotFoundException {

        User existingUser = UserService.findUserByEmail(user.getEmail());
        if (existingUser == null) {
            throw new NotFoundException("User not found");
        }
        // Update the flower properties
        existingUser.setName(user.getName());
        existingUser.setCity(user.getCity());
        existingUser.setRole(user.getRole());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setPasswordHash(user.getPasswordHash());

        UserService.updateUser(user);

        return Response.ok();
    }

    @Override
    public Response deleteUser(String email) throws NotFoundException {
        if (UserService.findUserByEmail(email) == null) {
            throw new NotFoundException(String.format("User with email: %s can't be found", email));
        }

        UserService.deleteUser(email);
        return Response.ok();
    }

    @Override
    public Response addFlowerOnWishlist(String flowerName, String email) {
        if (!UserService.isFlowerAddedToWishList(flowerName, email)) {
            UserService.addFlowerOnWishList(flowerName, email);
        }
        return Response.ok();
    }

    @Override
    public Response removeFlowerFromWishlist(String flowerName, String email) {
        if (UserService.isFlowerAddedToWishList(flowerName, email)) {
            UserService.removeFlowerFromWishList(flowerName, email);
        }
        return Response.ok();
    }

    @Override
    public List<FlowerFavoriteType> getUserFavoriteFlowers(String email) {
        return UserService.getFavoriteFlowersForUser(email);
    }
}
