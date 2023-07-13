package services;

import dataprovider.Data;
import exceptions.EmailSenderException;
import exceptions.NotFoundException;
import models.Flower;
import models.FlowerFavoriteType;
import models.User;
import utils.EmailSender;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class UserService {

    public static User findUserByEmail(String email) {

        String query = "SELECT email, name, password, phone_number, city, role FROM users WHERE email = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setPasswordHash(resultSet.getString("password"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setCity(resultSet.getString("city"));
                user.setRole(resultSet.getString("role"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveUser (User user) {

        String query = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getCity());
            statement.setString(6, user.getRole());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateUser(User user) {
        String query = "UPDATE users SET name = ?, password = ?, phone_number = ?, city = ?, role = ? WHERE email = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getCity());
            statement.setString(5, user.getRole());
            statement.setString(6, user.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteUser(String email) {
        //These should be done as user email is foreign key in flowers and favorites
        deleteAllItemsFromFavorites(email);

        List<Flower> userFlowers = FlowerService.findFlowersByUserMail(email);
        for (Flower flower : userFlowers) {
            FlowerService.deleteFlower(flower);
        }

        String query = "DELETE FROM users WHERE email = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, email);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeFlowerFromWishList (String flowerName, String email) {
        String query = "DELETE FROM favorites where email = ? AND flower_name = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, flowerName);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addFlowerOnWishList (String flowerName, String email) {
        String query = "INSERT INTO favorites VALUES (?, ?, ?)";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, flowerName.toLowerCase());
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isFlowerAddedToWishList (String flowerName, String email) {
        String query = "SELECT email, flower_name FROM favorites WHERE email = ? and flower_name = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, flowerName.toLowerCase());

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<FlowerFavoriteType> getFavoriteFlowersForUser (String email) {
        String query = "SELECT flower_name FROM favorites WHERE email =?";
        List<FlowerFavoriteType> favoriteFlowers;

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            favoriteFlowers = new LinkedList<>();

            while (resultSet.next()) {
                FlowerFavoriteType flowerFavorite = new FlowerFavoriteType();
                flowerFavorite.setFlowerName(resultSet.getString("flower_name"));
                favoriteFlowers.add(flowerFavorite);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return favoriteFlowers;
    }

    public static void notifyUsers(String flowerName)  {
        List<String> usersMails = getUsersToBeNotified(flowerName);

        for (String mail : usersMails) {
            try {
                EmailSender.sendEmail(mail, flowerName);
            } catch (EmailSenderException e) {
                System.out.printf("There was a problem with sending email to: %s%n", mail);
            }
        }
    }

    private static List<String> getUsersToBeNotified (String flowerName) {
        String query = "SELECT email FROM favorites where flower_name = ?";
        List<String> users;

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, flowerName);

            ResultSet resultSet = statement.executeQuery();
            users = new LinkedList<>();

            while (resultSet.next()) {
                users.add(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    private static void deleteAllItemsFromFavorites (String email) {
        String query = "DELETE FROM favorites where email =?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, email);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
