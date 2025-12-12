package MySQL;

// -- download MySQL from: http://dev.mysql.com/downloads/
// Community Server version
// -- Installation instructions are here: http://dev.mysql.com/doc/refman/5.7/en/installing.html
// -- open MySQL Workbench to see the contents of the database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
// -- MAKE SURE THE JDBC CONNECTOR JAR IS IN THE BUILD PATH
// workspace -> properties -> Java Build Path -> Libraries -> Add External JARs...
// "C:\Program Files\MySQL\mysql-connector-j-8.2.0\mysql-connector-java 8.0.27.jar"
// -- Preload MySQL with a Schema and Table
// In MySQL Workbench
// Create schema -- csc335:
// CREATE SCHEMA 'csc335';
//
// Create table with records:
// CREATE TABLE 'csc335'.'user-table' (
// 'username' VARCHAR(32) NOT NULL,
// 'password' VARCHAR(32) NULL,
// 'emailaddress' VARCHAR(64) NULL,
// PRIMARY KEY ('username'));
public class DataBaseConnection {
    // -- objects to be used for database access
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rset = null;
    // -- connect to the world database schema
// -- this is the connector to the database, default port is 3306
// private String url = "jdbc:mysql://localhost:3306/csc335project";
// private String url = "jdbc:mysql://127.0.0.1:3306/csc335project";
// jdbc:mysql://<ip address>:<port>/<schema>

// -- this is the username/password, created during installation and in MySQL Workbench
    // When you add a user make sure you give them the appropriate Administrative Roles
    // (DBA sets all which works fine)
// private static String username = "<<Your MySQL username>>";
// private static String password = "<<Your MySQL password>>";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/csc355";
    // Add your database credentials here
    private static final String USERNAME = "root";
    private static final String PASSWORD = "kbbqwh3n?";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    public DataBaseConnection() {
        try {
// -- make the connection to the database
            conn = DataBaseConnection.getConnection();
// -- These will be used to send queries to the database
            stmt = conn.createStatement();
            rset = stmt.executeQuery("SELECT VERSION()");
            if (rset.next()) {
                System.out.println("MySQL version: " + rset.getString(1) + "\n=====================\n");
            }
        }
        catch (SQLException ex) {
// handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    public void accessDatabase() {
        try {
// -- delete this record if it exists
            stmt.executeUpdate("DELETE FROM usertable WHERE username='smith';");
// -- a query will return a ResultSet
// get and print all records in the table
            System.out.println("Original Contents");
            rset = stmt.executeQuery("SELECT * FROM usertable;");
            printResultSet(rset);
// -- insert a record into the table
            System.out.println("Inserted Contents");
            stmt.executeUpdate("INSERT INTO usertable VALUE('smith', 'smith1234', 'smith@yahoo.com');");
// -- get and print all records in the table
            rset = stmt.executeQuery("SELECT * FROM usertable;");
            printResultSet(rset);
// -- modify a record in the table
// get the result set of records
            rset = stmt.executeQuery("SELECT * FROM usertable WHERE username='reinhart';");
// move the iterator to the record, if there is no record this will throw an exception
            rset.next();
// get the lockcount column and convert it to int
            String emailaddress = rset.getString(3);
            System.out.println("Updated Contents");
            stmt.executeUpdate("UPDATE usertable SET emailaddress='" +
                    "reinhart@gmail.com" + "' WHERE username='reinhart';");
// -- get and print all records in the table
            rset = stmt.executeQuery("SELECT * FROM usertable;");
            printResultSet(rset);
        }
        catch (SQLException ex) {
// handle any errors
            printSQLException(ex);
        }
    }
    public void printResultSet(ResultSet rset)
    {
        try {
// -- the metadata tells us how many columns in the data
            ResultSetMetaData rsmd = rset.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            System.out.println("columns: " + numberOfColumns);
// -- loop through the ResultSet one row at a time
// Note that the ResultSet starts at index 1
            while (rset.next()) {
// -- loop through the columns of the ResultSet
                for (int i = 1; i < numberOfColumns; ++i) {
                    System.out.print(rset.getString(i) + "\t");
                }
                System.out.println(rset.getString(numberOfColumns));
            }
        }
        catch (SQLException ex) {
// handle any errors
            printSQLException(ex);
        }
    }
    private void printSQLException(SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }

    public void close() {
        try {
            if (rset != null) rset.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            printSQLException(ex);
        }
    }

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
//        System.out.print("MySQL username: ");
//        String username = kb.next();
//        System.out.print("MySQL password: ");
//        String password = kb.next();
        DataBaseConnection dbc = new DataBaseConnection();
        dbc.accessDatabase();
    }
}
