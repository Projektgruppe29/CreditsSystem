package org.present;

import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.domain.Movie;

import static org.domain.Movie.datalist;

public class PrimaryController implements Initializable {

    @FXML
    private TableView<Movie> tableView;
    @FXML
    private TextField filterField;
    @FXML
    private TableColumn<Movie, Integer> idColumn;
    @FXML
    private TableColumn<Movie, String> nameColumn;
    @FXML
    private TableColumn<Movie, String> genreColumn;
    @FXML
    private TableColumn<Movie, Integer> releaseYearColumn;

    @FXML
    public void switchToLogin() throws IOException {
        App.setRoot("Login");
    }

    public void switchToViewFeatured(MouseEvent mouseEvent) throws IOException {
        App.setRoot("viewFeatured");
    }

    private void LoadMovies(Movie movie) throws SQLException {
        ObservableList<Movie> DBData = FXCollections.observableArrayList();
        DBData.add(movie);
        tableView.setItems(DBData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellTable();
        searchSortFilter();
    }

    private void setCellTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
    }

    public void searchSortFilter() {
        FilteredList<Movie> filteredList = new FilteredList<>(datalist, e -> true);

        filterField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(movie -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(movie.getName().toLowerCase().indexOf(lowerCaseFilter) !=-1) {
                    return true;
                } else if (movie.getGenre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });

        });

        SortedList<Movie> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedList);
    }

    public void LoadMovie(MouseEvent mouseEvent) throws IOException, InterruptedException {
        Thread.sleep(500);
        App.setRoot("viewFeatured");
    }

    public void LoadMovieFromDatabase() {
        Movie.getMovies();
        tableView.setItems(datalist);
    }
}
