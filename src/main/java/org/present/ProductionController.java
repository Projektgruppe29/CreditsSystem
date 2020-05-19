package org.present;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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


    public void CreateCreditForProduction(MouseEvent mouseEvent) throws IOException, InterruptedException {
        Thread.sleep(500);
        App.setRoot("Credits");
    }

    /*
    public boolean createProduction(ProductionType type,
                                    @Nullable UUID uuid,
                                    String title,
                                    String internalId,
                                    String description,
                                    Image image,
                                    Set<Genre> genres,
                                    int length,
                                    float rating,
                                    int year,
                                    List<ProductionPerson> persons) {
        if (title == null || title.matches("\\s*")) return false;
        switch (type) {
            case MOVIE:
                return createMovie(uuid == null ? getRandomUUID() : uuid, title, internalId, description, image, genres, length, rating, year, persons);
            case SERIES:
                return createSeries(uuid == null ? getRandomUUID() : uuid, title, internalId, image, description, genres);
            case SEASON:
                return createSeason(uuid == null ? getRandomUUID() : uuid, title, internalId, description);
            case EPISODE:
                return createEpisode(uuid == null ? getRandomUUID() : uuid, title, internalId, description, length, rating, year, persons);
        }

        return false;
    }

     */
}
