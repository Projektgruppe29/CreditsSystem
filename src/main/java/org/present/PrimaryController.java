package org.present;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.data.Production;
import org.domain.PersistanceHandler;

public class PrimaryController implements Initializable {

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

    public void LoadMovie(MouseEvent mouseEvent) throws IOException, InterruptedException {
        Thread.sleep(500);
        App.setRoot("viewFeatured");
    }

    public void viewProduction(ActionEvent actionEvent) {
        tableView.setItems(PersistanceHandler.getInstance().getProduction());
        searchSortFilter();
    }
}
