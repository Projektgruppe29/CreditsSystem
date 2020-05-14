package org.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Credits {
    private int id;
    private String name;
    private String role;
    public static ObservableList<Credits> creditsList = FXCollections.observableArrayList();
    public static Connection connection = ConnectionToDataBase.initializePostgresqlDatabase();

    public Credits(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Credits(String name, String role) {
        this.name = name;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static ObservableList<Credits> getCreditsList() {
        return creditsList;
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

}


