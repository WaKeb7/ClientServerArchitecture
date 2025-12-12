package MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerDatabase {
    public int getNumberOfRegisteredUsers() {
        String sql = "SELECT COUNT(*) FROM usertable";
        return executeCountQuery(sql);
    }

    public int getNumberOfLoggedInUsers() {
        String sql = "SELECT COUNT(*) FROM usertable WHERE is_logged_in = true";
        return executeCountQuery(sql);
    }

    public String[] getLoggedInUsers() {
        String sql = "SELECT username FROM usertable WHERE is_logged_in = true";
        return executeStringArrayQuery(sql);
    }

    public String[] getLockedOutUsers() {
        String sql = "SELECT username FROM usertable WHERE is_locked_out = true";
        return executeStringArrayQuery(sql);
    }



    private int executeCountQuery(String sql) {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String[] executeStringArrayQuery(String sql) {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            ArrayList<String> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(resultSet.getString("username"));
            }
            return users.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[0];
    }
}
