package billingapp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//
//   THIS CLASS IS ONLY FOR CREATING CONNECTION BETWEEN SQLITE3 AND JAVAFX OR JAVA
//   THIS FUNCTION IS VERY IMPORTANT
//
public class Loginmodel {
    private final static String DATABASE_NAME = "sm.db";
    private final static String url = "jdbc:sqlite:" + DATABASE_NAME;
    public static Connection Connector(){
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
