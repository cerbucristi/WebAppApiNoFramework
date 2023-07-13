package dataprovider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Data {

    private static final String URL = "jdbc:postgresql://localhost:5432/flowerManager";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres2";
    private Connection connection = null;

    private static Data instance;

    private Data() {
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            instance.createConnection();
        }
        return instance.connection;
    }


    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollback () {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /// private's

    private void createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Data getInstance() {
        if (instance == null) {
            synchronized (Data.class) {
                if (instance == null) {
                    instance = new Data();
                }
            }
        }
        return instance;
    }

}
