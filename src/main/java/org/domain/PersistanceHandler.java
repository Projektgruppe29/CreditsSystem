package org.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.data.Credits;
import org.data.IPersistanceHandler;
import org.data.Production;
import java.util.function.Consumer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersistanceHandler implements IPersistanceHandler {
    private static PersistanceHandler instance;
    private static String url = "";
    private static int port = 5432;
    private static String databaseName = "DBData";
    private static String username = "postgres";
    private static String password = "androsen";

    private static Connection connection = null;

    public PersistanceHandler() {
        DBConnect();
    }

    public static PersistanceHandler getInstance() {
        if (instance == null) {
            instance = new PersistanceHandler();
        }
        return instance;
    }

    public static Connection DBConnect() {
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
    //Closing Connection
    public static void DBDisconnect() throws SQLException {
        try{
            if(connection !=null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void execute(String sqlString, Object... args) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = this.connection;
        try{
            statement = connection.prepareStatement(sqlString);
            setArgs(statement, args);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBDisconnect();
        }
    }

    public void executeQuery(String sqlString, Consumer<ResultSet> callback, Object... args) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = this.connection;
        try{
            statement = connection.prepareStatement(sqlString);
            setArgs(statement, args);
            rs = statement.executeQuery();
            callback.accept(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBDisconnect();
        }
    }

    public static void executeUpdate(String sqlString) throws SQLException {
        Statement stmt = null;
        try{
            DBConnect();

            stmt = connection.createStatement();

            stmt.executeUpdate(sqlString);
        } catch (SQLException e) {
            throw e;
        } finally {
            if(stmt != null) {
                stmt.close();
            }
            DBDisconnect();
        }
    }

    private void setArgs(PreparedStatement statement, Object... args) {
        int i = 1;
        for(Object arg : args) {
            try{
                statement.setObject(i, arg);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            i++;
        }
    }


    @Override
    public ObservableList<Credits> getCredits() {
        try{
            DBConnect();
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM credit");
            ResultSet rs = queryStatement.executeQuery();
            ObservableList<Credits> returnValue = FXCollections.observableArrayList();
            while(rs.next()) {
                returnValue.add(new Credits(rs.getInt(1), rs.getString(2),rs.getString(3)));
            }
            return returnValue;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Credits getCredits(int id) {
        try{
            DBConnect();
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM credit WHERE id = ?");
            queryStatement.setInt(1, id);
            ResultSet rs = queryStatement.executeQuery();
            if(rs.next()) {
                return null;
            }
            return new Credits(rs.getInt(1),rs.getString(2), rs.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean createCredits(Credits credits) {
        try{
            DBConnect();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO credit (name, role)"
                    + "VALUES (?, ?)");
            statement.setString(1, credits.getName());
            statement.setString(2, credits.getRole());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ObservableList<Production> getProduction() {
        try{
            DBConnect();
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM production");
            ResultSet rs = queryStatement.executeQuery();
            ObservableList<Production> returnValue = FXCollections.observableArrayList();
            while(rs.next()) {
                returnValue.add(new Production(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getInt(4)));
            }
            return returnValue;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Production getProduction(int id) {
        try{
            DBConnect();
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM production WHERE id = ?");
            queryStatement.setInt(1, id);
            ResultSet rs = queryStatement.executeQuery();
            if(rs.next()) {
                return null;
            }
            return new Production(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getInt(4));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createProduction(Production production) {
        try{
            DBConnect();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO production (name, genre, releaseyear)"
                    + "VALUES (?, ?, ?)");
            statement.setString(1, production.getName());
            statement.setString(2, production.getGenre());
            statement.setInt(3, production.getReleaseYear());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void CreateProductionTable() throws SQLException{
        try{
            DBConnect();
            PreparedStatement createTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS table(id int NOT NULL AUTO_INCREMENT, name VARCHAR(50), role VARCHAR(50), PRIMARY KEY(id))");
            createTable.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


