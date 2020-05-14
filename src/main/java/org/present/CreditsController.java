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
import org.data.Credits;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CreditsController implements Initializable {
    @FXML
    private TableView<Credits> tableView;
    @FXML
    private TableColumn<Credits, String> fullNameColumn;
    @FXML
    private TableColumn<Credits, Integer> personIDColumn;
    @FXML
    private TableColumn<Credits, String> RoleColumn;

    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField PersonIDTextField;
    @FXML
    private TextField RoleTextField;

    public void changeFullnameCellEvent(CellEditEvent fullNameCell) {
        Credits personSelected = tableView.getSelectionModel().getSelectedItem();
        personSelected.setName(fullNameCell.getNewValue().toString());
    }

    public void changePersonIDCellEvent(CellEditEvent editEvent) {
        Credits personSelected = tableView.getSelectionModel().getSelectedItem();
        personSelected.setName(editEvent.getNewValue().toString());
    }

    public void changeRoleCellEvent(CellEditEvent editEvent) {
        Credits personSelected = tableView.getSelectionModel().getSelectedItem();
        personSelected.setName(editEvent.getNewValue().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set Columns
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<Credits, String>("fullName"));
        personIDColumn.setCellValueFactory(new PropertyValueFactory<Credits, Integer>("PersonID"));
        RoleColumn.setCellValueFactory(new PropertyValueFactory<Credits, String>("role"));

        //Load own data
        //tableView.setItems(getPeople());

        //Update the table for allow for the fullname and role
        tableView.setEditable(true);
        fullNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        RoleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }



    public void AddToList(ActionEvent actionEvent) {
        Credits newPerson = new Credits(new Integer(personIDColumn.getText()), fullNameTextField.getText(),RoleTextField.getText());

        tableView.getItems().add(newPerson);
    }

    private void clearList() {
        fullNameTextField.clear();
        PersonIDTextField.clear();
        RoleTextField.clear();
    }

    public void ConfirmList(ActionEvent actionEvent) {
        Credits.getCreditsList().add(new Credits(new Integer(personIDColumn.getText()), fullNameTextField.getText(),RoleTextField.getText()));
        clearList();
    }

    public void deleteFromList(ActionEvent actionEvent) {
        ObservableList<Credits> selectedRows, allPeople;
        allPeople = tableView.getItems();

        selectedRows = tableView.getSelectionModel().getSelectedItems();

        for(Credits person : selectedRows) {
            allPeople.remove(person);
        }

    }

    public void switchToProduction(ActionEvent actionEvent) throws IOException {
        App.setRoot("Production");
    }



    //Check it works
    /*
    public ObservableList<Credits> getPeople() {
        ObservableList<Credits> people = FXCollections.observableArrayList();
        people.add(new Credits("Kasper Svane", 1, "actor"));

        return people;
    }

     */


}
