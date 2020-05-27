package org.present;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.data.Credits;
import org.domain.PersistanceHandler;
import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;

public class ViewCreditController implements Initializable {

    public TableView tableView;
    @FXML
    private TableColumn<Credits, String> idColumn;
    @FXML
    private TableColumn<Credits, String> castIdColumn;
    @FXML
    private TableColumn<Credits, String> titelColumn;
    @FXML
    private TableColumn<Credits, String> roleColumn;
    @FXML
    Label titleLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellTable();

        tableView.setItems(PersistanceHandler.getInstance().getCredits(PrimaryController.getCurrentid()));
        titleLabel.setText(PrimaryController.getCurrentName());

    }

    private void setCellTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        castIdColumn.setCellValueFactory(new PropertyValueFactory<>("castID"));
        titelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

}



