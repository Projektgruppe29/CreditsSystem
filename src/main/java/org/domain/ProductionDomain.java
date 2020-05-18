package org.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.data.Credits;
import org.data.IPersistanceHandler;
import org.data.Production;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductionDomain implements IPersistanceHandler {
    private static ProductionDomain instance;
    private Connection connection = null;

    public ProductionDomain() {
        PersistanceHandler.DBConnect();
    }

    public static ProductionDomain getInstance() {
        if (instance == null) {
            instance = new ProductionDomain();
        }
        return instance;
    }

    @Override
    public ObservableList<Production> getProduction() {
        try{
            PersistanceHandler.DBConnect();
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
            PersistanceHandler.DBConnect();
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
            PersistanceHandler.DBConnect();
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

    @Override
    public ObservableList<Credits> getCredits() {
        return null;
    }

    @Override
    public Credits getCredits(int id) {
        return null;
    }

    @Override
    public boolean createCredits(Credits credits) {
        return false;
    }
}
