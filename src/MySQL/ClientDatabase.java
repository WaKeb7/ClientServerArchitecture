package MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientDatabase {

    private Connection conn;

    public ClientDatabase() {
        // Establish a connection to the database
        try {
            this.conn = DataBaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle failed connection here, possibly retrying or logging
        }
    }

    public boolean addUser(String username, String password, String email) {
        String sql = "INSERT INTO usertable (username, password, emailaddress) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password); // The password should be hashed in a real scenario
            statement.setString(3, email);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT username FROM usertable WHERE username = ? AND password = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password); // The password should be hashed

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean lockUser(String username) {
        String sql = "UPDATE usertable SET account_locked = ? WHERE username = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setBoolean(1, true);
            statement.setString(2, username);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getEmail(String username) {
        String sql = "SELECT email FROM usertable WHERE username = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateLoginStatus(String username, boolean isLoggedIn) {
        String sql = "UPDATE usertable SET is_logged_in = ? WHERE username = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setBoolean(1, isLoggedIn);
            statement.setString(2, username);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Additional methods as required...

    // It's often a good idea to provide a way to close the connection
    public void closeConnection() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle closing error
            }
        }
    }
}
