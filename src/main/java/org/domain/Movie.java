package org.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.data.ConnectionToDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int id;
    private String name;
    private String genre;
    private int releaseYear;
    public static ObservableList<Movie> datalist = FXCollections.observableArrayList();
    public static Connection connection = ConnectionToDataBase.initializePostgresqlDatabase();

    public Movie(int id, String name, String genre, int releaseYear) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }

    public Movie(String name, String genre, int releaseYear) {
        this.name = name;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public static ObservableList<Movie> getDatalist() {return datalist;}

    public static List<Movie> getMovies()  {
        try{

            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM movies");
            ResultSet rs = queryStatement.executeQuery();
            ObservableList<Movie> datalist = FXCollections.observableArrayList();
            while(rs.next()) {
                datalist.add(new Movie(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getInt(4)));
            }
            return datalist;
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
