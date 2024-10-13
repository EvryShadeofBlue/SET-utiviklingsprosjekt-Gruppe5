package models;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DBHelper {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/yourDatabaseName/softwareengineering";
    private static final String User = "root";
    private static final String Password = "1227kto";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection((DB_URL), User, Password);
            System.out.println("Connected to the database successfully");
        }catch (SQLException e) {
            System.out.println("Can not connect to database" + e.getMessage());

        }
        return conn;
    }

}
