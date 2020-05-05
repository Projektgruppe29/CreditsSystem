package org.data;

import java.sql.*;

public class ConnectionToDatabase {

    private static Connection connection = null;

    public static Connection main() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Movies",
                    "postgres",
                    "postgres"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
