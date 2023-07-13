package services;

import dataprovider.Data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class ActionsService {

    public static void deleteActionsTookOnFlower (int flowerId) {
        String query = "DELETE FROM actions WHERE flower_id =?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, flowerId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertAction (int id, String action) {

        String query = "INSERT INTO actions VALUES (?, ?, ?)";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, action);
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<String> getActionsOnFlower (int flowerId) {
        String query = "SELECT action_name FROM actions where flower_id = ? ORDER BY action_date";
        List<String> actions;
        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, flowerId);

            ResultSet resultSet = statement.executeQuery();
            actions = new LinkedList<>();
            while (resultSet.next()) {
                actions.add(resultSet.getString("action_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actions;
    }
}
