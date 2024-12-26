package Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionHelper
{
    private static DbConnectionHelper conn = null;
    private Connection connect = null;
    private final String DB_URL = "jdbc:mysql://localhost:3307/flavorite";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "";

    private DbConnectionHelper() {
        try {
            this.connect = DriverManager.getConnection("jdbc:mysql://localhost:3307/flavorite", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Database ile bağlantı kurmamıı sağlıyor.
    private Connection getConnect() {
        return this.connect;
    }

    public static Connection getInstance() {
        try {
            if (conn == null || conn.getConnect().isClosed()) {
                conn = new DbConnectionHelper();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conn.getConnect();
    }
}
