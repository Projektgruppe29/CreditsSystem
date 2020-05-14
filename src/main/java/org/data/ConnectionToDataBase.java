package org.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDataBase {
    private static PersistanceHandler instance;
    private static String url = "";
    private static int port = 5432;
    private static String databaseName = "Movies";
    private static String username = "postgres";
    private static String password = "postgres";

    private static Connection connection = null;

    public static PersistanceHandler getInstance() {
        if (instance == null) {
            instance = new PersistanceHandler();
        }
        return instance;
    }

    public ConnectionToDataBase() {
        initializePostgresqlDatabase();
    }

    public static Connection initializePostgresqlDatabase() {
        try{
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if(connection == null) {
                System.exit(-1);
            }
        }
        return connection;
    }
}
