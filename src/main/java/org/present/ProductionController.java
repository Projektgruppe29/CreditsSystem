package org.present;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.domain.Production;
import java.util.ArrayList;
import org.data.db;



import java.awt.*;
import java.io.IOException;

public class ProductionController extends Production {
    public TextField ProductionNameTextField;
    public TextField ProductionIDTextField;
    public TextField ProductionReleaseTextField;
    public TextField ProductionCountryTextField;
    public TextField EpisodeCountTextField;
    public TextField ProductionGenreTextField;
    public ListView viewProduction;
    private ObservableList<Production> productionObservableList;
    private ArrayList<Production> tempUsers;

    @FXML
    private void switchToProductionValues() throws IOException {
        App.setRoot("productionValues");
    }


    public void confirmProduction(ActionEvent actionEvent) {
        db.getList().add(new Production(ProductionNameTextField.getText(), Integer.parseInt(ProductionIDTextField.getText()), Integer.parseInt(ProductionReleaseTextField.getText()),
                ProductionCountryTextField.getText(), Integer.parseInt(EpisodeCountTextField.getText()), ProductionGenreTextField.getText()));
        clearFields();
    }
    private void clearFields() {
        ProductionNameTextField.clear();
        ProductionIDTextField.clear();
        ProductionReleaseTextField.clear();
        ProductionCountryTextField.clear();
        EpisodeCountTextField.clear();
        ProductionGenreTextField.clear();
    }

    public void getProductionList(ActionEvent actionEvent) {
        viewProduction.setItems(FXCollections.observableArrayList(db.getList()));
    }

    public void showProduction(MouseEvent mouseEvent) {


    }

    public void switchToProduction(ActionEvent actionEvent) throws IOException {
        App.setRoot("Production");
    }

    public void backToMainScene(ActionEvent actionEvent) throws IOException {
        App.setRoot("primary");
    }

    public void SwitchToCredits(ActionEvent actionEvent) throws IOException {
        App.setRoot("Credits");
    }
}
