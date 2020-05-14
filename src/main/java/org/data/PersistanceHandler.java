package org.data;

import org.domain.IPersistanceHandler;
import org.domain.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersistanceHandler implements IPersistanceHandler {
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

    public PersistanceHandler() {
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

    public List<Movie> getMovies()  {
        try{
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM movies");
            ResultSet rs = queryStatement.executeQuery();
            List<Movie> returnValue = new ArrayList<>();
            while(rs.next()) {
                returnValue.add(new Movie(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getInt(4)));
            }
            return returnValue;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Movie getMovies(int id) {
        try{
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM credits WHERE id = ?");
            queryStatement.setInt(1, id);
            ResultSet rs = queryStatement.executeQuery();
            if(rs.next()) {
                return null;
            }
            return new Movie(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getInt(4));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Credits> getCredits() {
        try{
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM credits");
            ResultSet rs = queryStatement.executeQuery();
            List<Credits> returnValue = new ArrayList<>();
            while(rs.next()) {
                returnValue.add(new Credits(rs.getInt(1), rs.getString(2),rs.getString(3)));
            }
            return returnValue;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Credits getCredits(int id) {
        try{
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM credits WHERE id = ?");
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO credits (name, role)"
                    + "VALUES (?, ?)");
            statement.setString(1, credits.getName());
            statement.setString(2, credits.getRole());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createMovies(Movie movie) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO movies (name, genre, releaseyear)"
                    + "VALUES (?, ?, ?)");
            statement.setString(1, movie.getName());
            statement.setString(2, movie.getGenre());
            statement.setInt(3, movie.getReleaseYear());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


