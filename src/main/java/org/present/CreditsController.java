package org.present;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.data.Credits;
import org.domain.PersistanceHandler;
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
    private TextField idTextField;
    @FXML
    private TextField productionIDTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField roleTextField;
    @FXML
    Label titleLabel;

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
        int id = Integer.parseInt(idTextField.getText());
        int castId = Integer.parseInt(productionIDTextField.getText());
        PersistanceHandler.getInstance().createCredits(new Credits(castId,
                id, nameTextField.getText(), roleTextField.getText()));
        tableView.setItems(PersistanceHandler.getInstance().getCredits(ProductionController.getIds()));
        clearFields();

    }

    private void clearFields() {
        productionIDTextField.clear();
        idTextField.clear();
        nameTextField.clear();
        roleTextField.clear();
    }

}
