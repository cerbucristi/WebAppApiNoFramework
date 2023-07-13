package services;

import dataprovider.Data;
import enums.FlowerStatusEnum;
import exceptions.EmailSenderException;
import exceptions.NotFoundException;
import models.Flower;
import models.FlowerListed;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlowerService {

    public static List<Flower> findFlowersByUserMail (String userMail) {
        String query = "SELECT flower_id, name, kind, planting_date, owner_email, status FROM flowers WHERE owner_email = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {

            statement.setString(1, userMail);

            ResultSet resultSet = statement.executeQuery();

            List<Flower> allFlowers = new ArrayList<>();

            while (resultSet.next()) {
                Flower flower = new Flower();
                flower.setId(resultSet.getInt("flower_id"));
                flower.setName(resultSet.getString("name"));
                flower.setKind(resultSet.getString("kind"));
                flower.setPlantingDate(resultSet.getDate("planting_date"));
                flower.setOwnerEmail(resultSet.getString("owner_email"));
                flower.setStatus(resultSet.getString("status"));
                allFlowers.add(flower);
            }

            return allFlowers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Flower findFlowerById(int id) {

        String query = "SELECT flower_id, name, kind, planting_date, owner_email, status FROM flowers WHERE flower_id = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Flower flower = new Flower();
                flower.setId(resultSet.getInt("flower_id"));
                flower.setName(resultSet.getString("name"));
                flower.setKind(resultSet.getString("kind"));
                flower.setPlantingDate(resultSet.getDate("planting_date"));
                flower.setOwnerEmail(resultSet.getString("owner_email"));
                flower.setStatus(resultSet.getString("status"));
                return flower;
            } else {
                return null; // Flower with the specified ID not found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Flower> getAllFlowers () throws NotFoundException {
        String query = "SELECT flower_id, name, kind, planting_date, owner_email, status FROM flowers";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();

            List<Flower> allFlowers = new ArrayList<>();

            while (resultSet.next()) {
                Flower flower = new Flower();
                flower.setId(resultSet.getInt("flower_id"));
                flower.setName(resultSet.getString("name"));
                flower.setKind(resultSet.getString("kind"));
                flower.setPlantingDate(resultSet.getDate("planting_date"));
                flower.setOwnerEmail(resultSet.getString("owner_email"));
                flower.setStatus(resultSet.getString("status"));
                allFlowers.add(flower);
            }

            if (allFlowers.isEmpty()) {
                throw new NotFoundException("Cannot find flowers!");
            }

            return allFlowers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<FlowerListed> getFlowersListed () throws NotFoundException {
        String query = "SELECT flower.flower_id as id, flower.name as name, flower.kind as kind, flower.planting_date as date, " +
                "flower.owner_email as email, flower.status as status, sales.price as price" +
                " FROM flowers flower JOIN flower_sales sales ON flower.flower_id = sales.flower_id where LOWER(flower.status) = 'listed'";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();

            List<FlowerListed> allFlowersListed = new ArrayList<>();

            while (resultSet.next()) {
                FlowerListed flower = new FlowerListed();
                flower.setId(resultSet.getInt("id"));
                flower.setName(resultSet.getString("name"));
                flower.setKind(resultSet.getString("kind"));
                flower.setPlantingDate(resultSet.getDate("date"));
                flower.setOwnerEmail(resultSet.getString("email"));
                flower.setStatus(resultSet.getString("status"));
                flower.setPrice(resultSet.getBigDecimal("price"));
                allFlowersListed.add(flower);
            }

            if (allFlowersListed.isEmpty()) {
                throw new NotFoundException("Cannot find flowers!");
            }

            return allFlowersListed;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void saveFlower(Flower flower) {

        String query = "INSERT INTO flowers (name, kind, planting_date, owner_email, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            
            statement.setString(1, flower.getName());
            statement.setString(2, flower.getKind());
            statement.setDate(3, new Date(flower.getPlantingDate().getTime()));
            statement.setString(4, flower.getOwnerEmail());
            statement.setString(5, FlowerStatusEnum.CULTIVATION_PROCESS.name().toLowerCase());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void updateFlower(Flower flower) {

        String query = "UPDATE flowers SET name = ?, kind = ?, planting_date = ?, owner_email = ?, status = ? WHERE flower_id = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, flower.getName());
            statement.setString(2, flower.getKind());
            statement.setDate(3, (Date) flower.getPlantingDate());
            statement.setString(4, flower.getOwnerEmail());
            statement.setString(5, flower.getStatus());
            statement.setInt(6, flower.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteFlower(Flower flower) {
        //These should be done as flower_id is foreign kei in flower_sales and actions
        deleteFlowerFromSales(flower.getId());
        ActionsService.deleteActionsTookOnFlower(flower.getId());


        String query = "DELETE FROM flowers WHERE flower_id = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, flower.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listFlower (int id, BigDecimal price) throws NotFoundException {
        Flower flower = findFlowerById(id);

        if (flower == null) {
            throw new NotFoundException(String.format("Flower with id: %d not found", id));
        }

        flower.setStatus(FlowerStatusEnum.LISTED.name().toLowerCase());

        updateFlower(flower);

        insertListedFlower(id, price);

        UserService.notifyUsers(flower.getName());
    }

    public static void sellFlower (int id, String sellerMail) throws NotFoundException {
        Flower flower = findFlowerById(id);

        if (flower == null) {
            throw new NotFoundException(String.format("Flower with id: %d not found", id));
        }

        flower.setStatus(FlowerStatusEnum.SELLED.name().toLowerCase());

        updateFlower(flower);

        updateSellInformation(id, sellerMail);

        UserService.removeFlowerFromWishList(flower.getName(), sellerMail);
    }

    private static void insertListedFlower (int id, BigDecimal price) {
        String query = "INSERT INTO flower_sales (flower_id, price, listed_date, buyer_email, buy_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {

            statement.setInt(1, id);
            statement.setBigDecimal(2, price);
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.setNull(4, Types.VARCHAR);
            statement.setNull(5, Types.DATE);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateSellInformation (int id, String clientMail) {

        String query = "UPDATE flower_sales SET buyer_email = ?, buy_date =? WHERE flower_id = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, clientMail);
            statement.setDate(2, new Date(System.currentTimeMillis()));
            statement.setInt(3, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void deleteFlowerFromSales (int id) {
        String query = "DELETE FROM flower_sales WHERE flower_id =?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
