package org.present;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.data.Credits;
import org.domain.Movie;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.data.Credits.creditsList;

public class TestController implements Initializable {

    @FXML
    private TableView<Credits> tableView;
    @FXML
    private TableColumn<Credits, String> nameColumn;
    @FXML
    private TableColumn<Credits, String> roleColumn;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField roleTextField;

    private Connection con = null;
    private PreparedStatement queryStatement = null;
    private ResultSet rs = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Movie.getDatalist();
        setCellTable();
        LoadFromDatabase();

    }

    public void ConfirmList(ActionEvent actionEvent) {
        Credits.getCreditsList().add(new Credits(nameTextField.getText(), roleTextField.getText()));
        clearList();
    }

    private void clearList() {
        nameTextField.clear();
        roleTextField.clear();
    }

    public void LoadFromDatabase() {
        try{
            queryStatement = con.prepareStatement("SELECT * FROM credits");
            rs = queryStatement.executeQuery();
            while(rs.next()) {
                creditsList.add(new Credits(rs.getString(2),rs.getString(3)));
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableView.setItems(creditsList);
    }

    private void setCellTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Credits, String>("name"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<Credits, String>("role"));
    }

    public void BackToScene(ActionEvent actionEvent) throws IOException {
        App.setRoot("Production");
    }
}
