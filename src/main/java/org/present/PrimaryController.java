package org.present;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.data.Credits;
import org.data.Production;
import org.domain.PersistanceHandler;

public class PrimaryController implements Initializable {

    private static int currentid;
    @FXML
    private TableView tableView;
    @FXML
    private TextField filterField;
    @FXML
    private TableColumn<Production, Integer> idColumn;
    @FXML
    private TableColumn<Production, String> nameColumn;
    @FXML
    private TableColumn<Production, String> genreColumn;
    @FXML
    private TableColumn<Production, Integer> releaseYearColumn;
    Production credits;
    private static String currentName;


    @FXML
    public void switchToLogin() throws IOException {
        App.setRoot("Login");
    }

    public void switchToViewFeatured(MouseEvent mouseEvent) throws IOException {
        App.setRoot("viewFeatured");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellTable();

        tableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue ov, Object old_val, Object new_val) -> {
            credits = (Production) tableView.getSelectionModel().getSelectedItem();
            int index = tableView.getSelectionModel().getSelectedIndex();
            PersistanceHandler.getInstance().getCredits(credits.getId());
            currentid = credits.getId();
            currentName = credits.getName();
        });


        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("viewCredit.fxml"));
                ViewCreditController dac = (ViewCreditController) loader.getController();
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

    public void searchSortFilter() {
        FilteredList<Production> filteredList = new FilteredList<>(PersistanceHandler.getInstance().getProduction(), e -> true);

        filterField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(production -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(production.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (production.getGenre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                 else {
                    return false;
                }
            });

        });

        SortedList<Production> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedList);
    }

    public void LoadProduction(MouseEvent mouseEvent) throws IOException, InterruptedException {
        Thread.sleep(500);
        App.setRoot("viewCredit");
    }

    public void viewProduction(ActionEvent actionEvent) {
        tableView.setItems(PersistanceHandler.getInstance().getProduction());


        searchSortFilter();
    }

    public static int getCurrentid() {
        return currentid;
    }

    public static String getCurrentName() {
        return currentName;
    }
}
