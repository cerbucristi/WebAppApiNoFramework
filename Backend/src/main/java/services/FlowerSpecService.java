package services;

import dataprovider.Data;
import models.FlowerRequiredSpecs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FlowerSpecService {

    public static FlowerRequiredSpecs getFlowerSpecByName(String name) {

        String query = "SELECT name, required_time, required_temp, required_humid FROM flower_details WHERE name = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                FlowerRequiredSpecs flower = new FlowerRequiredSpecs();
                flower.setName(resultSet.getString("name"));
                flower.setRequiredDays(resultSet.getInt("required_time"));
                flower.setRequiredHumidPercent(resultSet.getInt("required_humid"));
                flower.setRequiredTemp(resultSet.getInt("required_temp"));
                return flower;
            } else {
                return null; // Flower with the specified ID not found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
