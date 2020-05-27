package org.present;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.data.Credits;
import org.data.Production;
import org.domain.PersistanceHandler;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;

public class ViewCreditController implements Initializable {

    public TableView tableView;
    @FXML
    private TableColumn<Credits, String> castIdColumn;
    @FXML
    private TableColumn<Credits, String> titelColumn;
    @FXML
    private TableColumn<Credits, String> roleColumn;
    @FXML
    Label titleLabel;

    public void switchToPrimaryScene(ActionEvent actionEvent) throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellTable();

        tableView.setItems(PersistanceHandler.getInstance().getCredits(PrimaryController.getCurrentid()));
        titleLabel.setText(PrimaryController.getCurrentName());

    }


    private void setCellTable() {
        castIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

}



