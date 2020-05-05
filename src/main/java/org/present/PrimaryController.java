package org.present;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.data.ConnectionToDatabase;
import org.data.db;
import org.domain.Movie;

import static org.data.db.datalist;

public class PrimaryController implements Initializable {

    @FXML
    private TableView<Movie> tableView;
    @FXML
    private TextField filterField;
    @FXML
    private TableColumn<Movie, String> nameColumn;
    @FXML
    private TableColumn<Movie, String> genreColumn;
    @FXML
    private TableColumn<Movie, Integer> releaseYearColumn;

    private Connection con = null;
    private PreparedStatement queryStatement = null;
    private ResultSet rs = null;

    @FXML
    public void switchToLogin() throws IOException {
        App.setRoot("Login");
    }

    public void switchToViewFeatured(MouseEvent mouseEvent) throws IOException {
        App.setRoot("viewFeatured");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        con = ConnectionToDatabase.main();
        db.getDatalist();
        setCellTable();
        LoadMovieFromDatabase();
        searchSortFilter();
    }

    private void setCellTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
    }

    public void LoadMovieFromDatabase()  {
        try{
            queryStatement = con.prepareStatement("SELECT * FROM movies");
            rs = queryStatement.executeQuery();
            while(rs.next()) {
                datalist.add(new Movie(rs.getString(2),rs.getString(3),rs.getInt(4)));
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableView.setItems(datalist);
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
}
