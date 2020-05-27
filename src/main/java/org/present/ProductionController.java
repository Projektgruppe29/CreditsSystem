package org.present;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.data.Production;
import org.domain.PersistanceHandler;
import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;

public class ProductionController implements Initializable {

    public TableView tableView;
    @FXML
    private TableColumn<Production, Integer> idColumn;
    @FXML
    private TableColumn<Production, String> nameColumn;
    @FXML
    private TableColumn<Production, String> genreColumn;
    @FXML
    private TableColumn<Production, Integer> releaseYearColumn;
    private static int ids;
    private static String names;
    Production production;


    @FXML
    private void switchToProductionValues() throws IOException {
        App.setRoot("productionValues");
    }

    public void backToMainScene(ActionEvent actionEvent) throws IOException {
        App.setRoot("primary");
    }

    public void switchToProduction(ActionEvent actionEvent) throws IOException {
        App.setRoot("Production");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellTable();

        tableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue ov, Object old_val, Object new_val) -> {
            production = (Production) tableView.getSelectionModel().getSelectedItem();
            int index = tableView.getSelectionModel().getSelectedIndex();
            PersistanceHandler.getInstance().getCredits(production.getId());
            ids = production.getId();
            names = production.getName();
        });

        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Credits.fxml"));
                CreditsController dac = (CreditsController) loader.getController();
                try {
                    loader.load();
                    Thread.sleep(400);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                Parent p = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.show();
            }
        });
    }

    private void setCellTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
    }

    public void showProduction(ActionEvent actionEvent) {
        tableView.setItems(PersistanceHandler.getInstance().getProduction());
    }

    public static int getIds() {
        return ids;
    }

    public static String getNames() {
        return names;
    }
}
