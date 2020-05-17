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

public class CreditsDomain implements IPersistanceHandler {

    private static CreditsDomain instance;
    private Connection connection = null;

    public CreditsDomain() {
        PersistanceHandler.DBConnect();
    }

    public static CreditsDomain getInstance() {
        if (instance == null) {
            instance = new CreditsDomain();
        }
        return instance;
    }

    @Override
    public ObservableList<Credits> getCredits() {
        try{
            PersistanceHandler.DBConnect();
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
            PersistanceHandler.DBConnect();
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
            PersistanceHandler.DBConnect();
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
        return null;
    }

    @Override
    public Production getProduction(int id) {
        return null;
    }

    @Override
    public boolean createProduction(Production production) {
        return false;
    }

}
