package org.present;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.data.Production;
import org.domain.PersistanceHandler;

import java.net.URL;
import java.util.ArrayList;


import java.io.IOException;
import java.util.ResourceBundle;

public class ProductionController implements Initializable {
    public TableView tableView;
    public TextField IDTextField;
    public TextField nameTextField;
    public TextField genreTextField;
    public TextField releaseYearTextField;

    @FXML
    private TableColumn<Production, Integer> idColumn;
    @FXML
    private TableColumn<Production, String> nameColumn;
    @FXML
    private TableColumn<Production, String> genreColumn;
    @FXML
    private TableColumn<Production, Integer> releaseYearColumn;


    @FXML
    private void switchToProductionValues() throws IOException {
        App.setRoot("productionValues");
    }


    public void backToMainScene(ActionEvent actionEvent) throws IOException {
        App.setRoot("primary");
    }

    //productionValues.fxml
    public void switchToProduction(ActionEvent actionEvent) throws IOException {
        App.setRoot("Production");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
    }

    private void setCellTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
    }


    public void confirmProduction(ActionEvent actionEvent) {
        PersistanceHandler.getInstance().getProduction().add(new Production(Integer.parseInt(IDTextField.getText()), nameTextField.getText(), genreTextField.getText(), Integer.parseInt(releaseYearTextField.getText())));
        clearFields();
    }

    private void clearFields() {
        nameTextField.clear();
        genreTextField.clear();
        releaseYearTextField.clear();
    }


    public void showProduction(ActionEvent actionEvent) {
        tableView.setItems(PersistanceHandler.getInstance().getProduction());
    }


}
