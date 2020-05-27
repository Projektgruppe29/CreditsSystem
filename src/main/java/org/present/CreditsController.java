package org.present;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import org.data.Credits;
import org.data.Production;
import org.domain.PersistanceHandler;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CreditsController implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Credits, Integer> idColumn;
    @FXML
    private TableColumn<Credits, Integer> castIdColumn;
    @FXML
    private TableColumn<Credits, String> titelColumn;
    @FXML
    private TableColumn<Credits, String> roleColumn;

    @FXML
    private TextField productionIDTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField roleTextField;
    @FXML
    private TextField idTextField;

    @FXML
    Label titleLabel;

    public void switchToProduction(ActionEvent actionEvent) throws IOException {
        App.setRoot("Production");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set Columns
        setCellTable();

        tableView.setItems(PersistanceHandler.getInstance().getCredits(ProductionController.getIds()));
        titleLabel.setText(ProductionController.getNames());

    }


    private void setCellTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        castIdColumn.setCellValueFactory(new PropertyValueFactory<>("castID"));
        titelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    }


    public void CreateCredit(ActionEvent actionEvent) {
        PersistanceHandler.getInstance().createCredits(new Credits(nameTextField.getText(), roleTextField.getText()));
        clearFields();

    }

    private void clearFields() {
        productionIDTextField.clear();
        idTextField.clear();
        nameTextField.clear();
        roleTextField.clear();
    }

    public void AlterCredit(ActionEvent actionEvent) {
        tableView.setEditable(true);

    }

}
