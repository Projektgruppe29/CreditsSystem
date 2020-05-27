package org.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.data.Credits;
import org.data.IPersistanceHandler;
import org.data.Production;

import java.sql.*;

public class PersistanceHandler implements IPersistanceHandler {
    private static PersistanceHandler instance;
    private static String url = "";
    private static int port = 5432;
    private static String databaseName = "DBData";
    private static String username = "postgres";
    private static String password = "postgres";

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
/*
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

 */

    @Override
    public ObservableList<Credits> getCredits() {
        try{
            DBConnect();
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM credit");
            ResultSet rs = queryStatement.executeQuery();
            ObservableList<Credits> returnValue = FXCollections.observableArrayList();
            while(rs.next()) {
                returnValue.add(new Credits(rs.getInt(1), rs.getInt(2),rs.getString(3), rs.getString(4)));
            }
            return returnValue;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ObservableList<Credits> getCredits(int id) {
        try{
            DBConnect();
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM credit WHERE movie_id = ?");
            queryStatement.setInt(1, id);
            ResultSet rs = queryStatement.executeQuery();
            ObservableList<Credits> returnValue = FXCollections.observableArrayList();
            if(rs.next()) {
                System.out.println(rs.getInt(2));
                returnValue.add(new Credits(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
                return returnValue;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public void createCredits(Credits credits) {
        try{
            DBConnect();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO credit (movie_id, cast_id, name, role)"
                    + "VALUES (?, ?, ?, ?)");
            statement.setInt(1, credits.getId());
            statement.setInt(2, credits.getCastID());
            statement.setString(3, credits.getName());
            statement.setString(4, credits.getRole());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
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
    public void createProduction(Production production) {
        try{
            DBConnect();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO production (titel, genre, release_year)"
                    + "VALUES (?, ?, ?)");
            statement.setString(1, production.getName());
            statement.setString(2, production.getGenre());
            statement.setInt(3, production.getReleaseYear());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


