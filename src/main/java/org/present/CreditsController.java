package org.present;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import org.data.Credits;
import org.domain.CreditsDomain;
import org.domain.PersistanceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CreditsController implements Initializable {
    @FXML
    private TableView<Credits> tableView;
    @FXML
    private TableColumn<Credits, Integer> idColumn;
    @FXML
    private TableColumn<Credits, String> nameColumn;
    @FXML
    private TableColumn<Credits, String> RoleColumn;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField roleTextField;

    public void switchToProduction(ActionEvent actionEvent) throws IOException {
        App.setRoot("Production");
    }

    public void changeFullnameCellEvent(CellEditEvent fullNameCell) {
        Credits personSelected = tableView.getSelectionModel().getSelectedItem();
        personSelected.setName(fullNameCell.getNewValue().toString());
    }

    public void changeRoleCellEvent(CellEditEvent editEvent) {
        Credits personSelected = tableView.getSelectionModel().getSelectedItem();
        personSelected.setName(editEvent.getNewValue().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set Columns
        idColumn.setCellValueFactory(new PropertyValueFactory<Credits, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Credits, String>("name"));
        RoleColumn.setCellValueFactory(new PropertyValueFactory<Credits, String>("role"));
    }


    private void clearList() {
        nameTextField.clear();
        roleTextField.clear();
    }

    public void deleteFromList(ActionEvent actionEvent) {
        ObservableList<Credits> selectedRows, allPeople;
        allPeople = tableView.getItems();

        selectedRows = tableView.getSelectionModel().getSelectedItems();

        for(Credits person : selectedRows) {
            allPeople.remove(person);
        }

    }

    public void AddImage(MouseEvent mouseEvent) {

    }

    public void CreateCredit(ActionEvent actionEvent) {
        Credits newPerson = new Credits(new Integer(idColumn.getText()), nameTextField.getText(), roleTextField.getText());

        tableView.getItems().add(newPerson);
    }

    public void AlterCredit(ActionEvent actionEvent) {
        tableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        RoleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

}
