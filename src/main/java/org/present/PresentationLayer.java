package org.present;

public class PresentationLayer {
import io.github.arkobat.semesterprojektf20.business.controller.ProductionController;
import io.github.arkobat.semesterprojektf20.business.controller.UserController;
import io.github.arkobat.semesterprojektf20.business.model.production.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

    public class CreateProduction {

        private final ProductionController productionController;
        private final UserController userController;

        private final Frontpage frontpage;

        private @NotNull Production production;

        private @NotNull ProductionType productionType = ProductionType.MOVIE;

        private final VBox finalBox = new VBox();

        private @Nullable TextField nameField;
        private @Nullable TextField internalIdField;
        private @Nullable TextArea descriptionField;
        private @Nullable Image image;
        private @Nullable TextField lengthField;
        private @Nullable TextField ratingField;
        private @Nullable TextField yearField;
        private @Nullable FlowPane genresField;
        private final List<ProductionPerson> persons = new LinkedList<>();

        public CreateProduction(ProductionController productionController, UserController userController, Frontpage frontpage, @Nullable Production production) {
            this.productionController = productionController;
            this.userController = userController;
            this.frontpage = frontpage;
            this.production = production == null ? new Movie(productionController.getRandomUUID()) : production;
        }

        public void startProductionCreation(Stage stage) {
            this.finalBox.setAlignment(Pos.CENTER);

            this.finalBox.getChildren().add(getHeader(stage));
            if (userController.canEdit(this.production.getUuid())) {
                this.finalBox.getChildren().add(getRadioButtons());
            }

            if (this.production instanceof SubProduction) {
                persons.addAll(((SubProduction) this.production).getPersons());
            }

            final CreditView creditView = new CreditView(stage, this.production,
                    userController.canEdit(this.production.getUuid()));

            this.finalBox.getChildren().addAll(getGenres(), getLengthAndRating(), creditView.getActorView());

            this.finalBox.getChildren().addAll(getSaveButton(stage));

            validateFields();

            //   this.finalBox.setStyle("-fx-alignment: CENTER; -fx-max-width: 1000; -fx-background-color: RED;");
            this.finalBox.setStyle("-fx-alignment: CENTER;");
            ScrollPane scrollPane = new ScrollPane(this.finalBox);
            scrollPane.setStyle("-fx-alignment: CENTER; -fx-fit-to-width: true");

            stage.setScene(new Scene(scrollPane));
        }

        private void validateFields() {
            List<String> show = new LinkedList<>(Arrays.asList("header", "radioButtons", "backAndSave"));
            if (this.productionType == ProductionType.MOVIE) {
                show.addAll(Arrays.asList("genres", "lengthAndRating", "persons"));
            } else if (this.productionType == ProductionType.SERIES) {
                if (this.productionType == ProductionType.SERIES) {
                    show.add("genres");
                } else if (this.productionType == ProductionType.SEASON) {
                    // ???
                } else if (this.productionType == ProductionType.EPISODE) {
                    show.addAll(Arrays.asList("lengthAndRating", "persons"));
                }
            }

            finalBox.getChildren().forEach(n -> {
                if (n.getId() == null) {
                    return;
                }
                n.setVisible(show.contains(n.getId()));
            });
        }

        private Node getHeader(Stage stage) {
            Label nameLabel = new Label("Navn");
            Node nameField;
            Node internalIdField;
            Node descriptionField;
            ImageView img;

            if ((this.production != null && userController.canEdit(this.production.getUuid())) || (this.production == null && userController.canCreateProductions())) {
                nameField = new TextField(this.production == null ? "" : this.production.getTitle());
                internalIdField = new TextField(this.production == null ? "" : this.production.getInternalId());
                descriptionField = new TextArea(this.production == null ? "" : this.production.getDescription());

                this.nameField = (TextField) nameField;
                this.internalIdField = (TextField) internalIdField;
                this.descriptionField = (TextArea) descriptionField;

                if (this.production != null && this.production instanceof MasterProduction && ((MasterProduction) this.production).getImage() != null) {
                    img = new ImageView(((MasterProduction) this.production).getImage());
                } else {
                    img = new ImageView(new Image("default_poster.png"));
                }
            } else {
                nameField = new Label(this.production == null ? "Ukendt titel" : this.production.getTitle());
                internalIdField = new Label(this.production == null ? "" : this.production.getInternalId());
                descriptionField = new Text(this.production == null ? "" : this.production.getDescription());
                if (this.production != null && this.production instanceof MasterProduction && ((MasterProduction) this.production).getImage() != null) {
                    img = new ImageView(((MasterProduction) this.production).getImage());
                } else {
                    img = new ImageView(new Image("default_poster.png"));
                }
            }

            nameField.setId("name");
            internalIdField.setId("internalId");
            descriptionField.setId("description");

            nameField.setStyle("-fx-max-width: 800; -fx-pref-height: 40; -fx-vgap: 10; -fx-hgap: 10;");
            internalIdField.setStyle("-fx-max-width: 800; -fx-pref-height: 40; -fx-vgap: 10; -fx-hgap: 10;");
            descriptionField.setStyle("-fx-max-width: 800; -fx-pref-height: 200; -fx-vgap: 10; -fx-hgap: 10; -fx-wrap-text: true");
            img.setFitWidth(200);
            img.setPreserveRatio(true);

            VBox box1 = new VBox(nameLabel, nameField);
            box1.setStyle(" -fx-alignment: CENTER;");
            box1.setId("name");

            Label internalIdLabel = new Label("Internt ID");
            VBox box2 = new VBox(internalIdLabel, internalIdField);
            box2.setStyle(" -fx-alignment: CENTER;");
            box2.setId("internalId");


            Label descriptionLabel = new Label("Beskrivelse");

            VBox box3 = new VBox(descriptionLabel, descriptionField);
            box3.setStyle("-fx-alignment: CENTER;");
            box3.setId("description");

            VBox vBox = new VBox(box1, box2, box3);

            if ((this.production != null && userController.canEdit(this.production.getUuid())) || (this.production == null && userController.canCreateProductions())) {
                img.setOnMouseClicked(k -> {
                    File file = new FileChooser().showOpenDialog(stage);
                    if (file == null) {
                        return;
                    }
                    String fileName = file.getName().toLowerCase();
                    if (!fileName.endsWith(".png") && !fileName.endsWith("jpg")) {
                        return;
                    }
                    img.setImage(new Image(file.toURI().toString()));

                    this.image = img.getImage();
                });
            }


            HBox hBox = new HBox(img, vBox);
            hBox.setStyle("-fx-alignment: CENTER; -fx-spacing: 10");


            hBox.setId("header");
            return hBox;
        }

        private Node getRadioButtons() {
            ToggleGroup movieGroup = new ToggleGroup();

            RadioButton radioMovie = new RadioButton("Film");
            radioMovie.setToggleGroup(movieGroup);
            movieGroup.selectToggle(radioMovie);

            RadioButton radioSeries = new RadioButton("Serie");
            radioSeries.setToggleGroup(movieGroup);

            HBox productionType = new HBox(radioMovie, radioSeries);

            ToggleGroup seriesGroup = new ToggleGroup();

            RadioButton seriesSeries = new RadioButton("Serie");
            seriesSeries.setToggleGroup(seriesGroup);
            seriesGroup.selectToggle(seriesSeries);

            RadioButton seriesSeason = new RadioButton("Sæson");
            seriesSeason.setToggleGroup(seriesGroup);

            RadioButton seriesEpisode = new RadioButton("Episode");
            seriesEpisode.setToggleGroup(seriesGroup);

            HBox seriesType = new HBox(seriesSeries, seriesSeason, seriesEpisode);
            seriesType.setVisible(false);

            productionType.setStyle("-fx-alignment: CENTER");
            seriesType.setStyle("-fx-alignment: CENTER");

            VBox vBox = new VBox(productionType, seriesType);
            vBox.setStyle("-fx-padding: 10; -fx-wrap-text: true; -fx-max-width: 800; -fx-alignment: CENTER");

            movieGroup.selectedToggleProperty().addListener(aVoid -> {
                if (movieGroup.getSelectedToggle() == radioSeries) {
                    seriesType.setVisible(true);
                    seriesGroup.selectToggle(seriesSeries);
                    this.productionType = ProductionType.SERIES;
                } else if (movieGroup.getSelectedToggle() == radioMovie) {
                    seriesType.setVisible(false);
                    this.productionType = ProductionType.MOVIE;
                    this.production = new Movie(this.productionController.getRandomUUID());
                }
                validateFields();
            });

            seriesGroup.selectedToggleProperty().addListener(aVoid -> {
                if (seriesGroup.getSelectedToggle() == seriesSeries) {
                    this.productionType = ProductionType.SERIES;
                    this.production = new Series(this.productionController.getRandomUUID());
                } else if (seriesGroup.getSelectedToggle() == seriesSeason) {
                    this.productionType = ProductionType.SEASON;
                    this.production = new Season(this.productionController.getRandomUUID());
                } else if (seriesGroup.getSelectedToggle() == seriesEpisode) {
                    this.productionType = ProductionType.EPISODE;
                    this.production = new Episode(this.productionController.getRandomUUID());
                }
                validateFields();
            });

            vBox.setId("radioButtons");
            return vBox;
        }

        private Node getGenres() {
            this.genresField = new FlowPane();
            this.genresField.setId("genres");
            Set<Genre> genres = this.production != null && this.production instanceof MasterProduction && ((MasterProduction) this.production).getGenres() != null ? ((MasterProduction) this.production).getGenres() : new HashSet<>();
            boolean locked = !((this.production != null && userController.canEdit(this.production.getUuid())) || (this.production == null && userController.canCreateProductions()));
            Arrays.stream(Genre.values()).forEach(g -> {
                CheckBox box = new CheckBox(g.getFriendlyName());
                box.setStyle("-fx-padding: 4;");
                if (genres.contains(g)) {
                    box.setSelected(true);
                }
                if (locked) {
                    box.setDisable(true);
                }
                this.genresField.getChildren().add(box);
                box.setId("genre:" + g.toString());
            });
            this.genresField.setStyle("-fx-padding: 10; -fx-wrap-text: true; -fx-max-width: 800; -fx-alignment: CENTER");
            return this.genresField;
        }

        private Node getLengthAndRating() {
            Node lengthField;
            Node ratingField;
            Node yearField;

            Label lengthTitle = new Label("Længde");
            Label minLabel = new Label("min");

            Label ratingTitle = new Label("Bedømmelse");
            Label ratingLabel = new Label("/ 10");

            Label yearLabel = new Label("År");
            Tooltip yearTooltip = new Tooltip("Året produktionen udkom");

            if ((this.production != null && userController.canEdit(this.production.getUuid())) || (this.production == null && userController.canCreateProductions())) {
                SubProduction subProduction = this.production != null && this.production instanceof SubProduction ? (SubProduction) this.production : null;

                // Length field
                lengthField = new TextField(subProduction == null ? "" : String.valueOf(subProduction.getLength()));
                ((TextField) lengthField).setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("^\\d{0,4}$") ? change : null));
                Tooltip lengthTooltip = new Tooltip("Antal minutter produktionen varer");
                lengthTitle.setTooltip(lengthTooltip);
                ((TextField) lengthField).setTooltip(lengthTooltip);
                minLabel.setTooltip(lengthTooltip);

                // Rating field
                ratingField = new TextField(subProduction == null ? "" : String.valueOf(subProduction.getRating()));
                ((TextField) ratingField).setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("^(10|10\\.0{0,2}|[0-9]?(\\.\\d{0,2})?)$") ? change : null));
                Tooltip ratingTooltip = new Tooltip("Raiting på produktionen");
                ratingTitle.setTooltip(ratingTooltip);
                ((TextField) ratingField).setTooltip(ratingTooltip);
                ratingLabel.setTooltip(ratingTooltip);

                // Year field
                yearField = new TextField(subProduction == null ? "" : String.valueOf(subProduction.getYear()));
                ((TextField) yearField).setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("^\\d{0,4}$") ? change : null));
                yearLabel.setTooltip(yearTooltip);
                ((TextField) yearField).setTooltip(yearTooltip);

                //
                this.lengthField = (TextField) lengthField;
                this.ratingField = (TextField) ratingField;
                this.yearField = (TextField) yearField;

            } else {
                SubProduction subProduction = this.production != null && this.production instanceof SubProduction ? (SubProduction) this.production : null;
                lengthField = new Label(subProduction == null ? "NaN" : String.valueOf(subProduction.getLength()));
                ratingField = new Label(subProduction == null ? "NaN" : String.valueOf(subProduction.getRating()));
                yearField = new Label(subProduction == null ? "NaN" : String.valueOf(subProduction.getYear()));
            }

            VBox lengthBox = new VBox(lengthTitle, new HBox(lengthField, minLabel));
            lengthBox.setStyle("-fx-padding: 10; -fx-wrap-text: true; -fx-max-width: 800; -fx-alignment: CENTER");
            lengthBox.setId("length");

            VBox ratingBox = new VBox(ratingTitle, new HBox(ratingField, ratingLabel));
            ratingBox.setStyle("-fx-padding: 10; -fx-wrap-text: true; -fx-max-width: 800; -fx-alignment: CENTER");
            ratingBox.setId("rating");

            VBox yearBox = new VBox(yearLabel, yearField);
            yearBox.setStyle("-fx-padding: 10; -fx-wrap-text: true; -fx-max-width: 800; -fx-alignment: CENTER");
            yearBox.setId("year");


            HBox hBox = new HBox(lengthBox, ratingBox, yearBox);
            hBox.setStyle("-fx-padding: 10; -fx-wrap-text: true; -fx-max-width: 800; -fx-alignment: CENTER");
            hBox.setId("lengthAndRating");
            return hBox;
        }

        private Node getSaveButton(Stage stage) {
            HBox buttons = new HBox();
            buttons.setId("backAndSave");
            buttons.setStyle("-fx-max-width: 800; -fx-alignment: CENTER");

            Button backButton = new Button("Tilbage");
            backButton.setStyle("-fx-padding: 10; -fx-wrap-text: true; -fx-max-width: 800; -fx-alignment: CENTER");
            backButton.setId("back");
            backButton.setOnMouseClicked(aVoid -> this.frontpage.showStartScreen(stage));
            buttons.getChildren().add(backButton);

            if ((this.production != null && userController.canEdit(this.production.getUuid())) || (this.production == null && userController.canCreateProductions())) {
                Button saveButton = new Button("Save");
                saveButton.setStyle("-fx-padding: 10; -fx-wrap-text: true; -fx-max-width: 800; -fx-alignment: CENTER");
                saveButton.setId("save");

                saveButton.setOnMousePressed(aVoid -> {
                    String name = this.nameField == null ? null : this.nameField.getText();
                    String internalId = this.internalIdField == null ? null : this.internalIdField.getText() == null ? null : this.internalIdField.getText().matches("\\s*") ? null : this.internalIdField.getText();
                    String description = this.descriptionField == null ? "" : this.descriptionField.getText();
                    int length = this.lengthField == null ? 0 : this.lengthField.getText().equals("") ? 0 : Integer.parseInt(this.lengthField.getText());
                    float rating = this.ratingField == null ? 0.0F : this.ratingField.getText().equals("") ? 0.0F : Float.parseFloat(this.ratingField.getText());
                    int year = this.yearField == null ? 0 : this.yearField.getText().equals("") ? 0 : Integer.parseInt(this.yearField.getText());
                    Set<Genre> genre = new HashSet<>();
                    if (this.genresField == null) {
                        genre.add(Genre.UNKNOWN);
                    } else this.genresField.getChildren().forEach(c -> {
                        CheckBox cBox = (CheckBox) c;
                        if (cBox.isSelected()) {
                            genre.add(Genre.valueOf(cBox.getId().replaceFirst("genre:", "")));
                        }
                    });

                    if (this.productionController.createProduction(this.productionType, this.production == null ? null : this.production.getUuid(), name, internalId, description, this.image, genre, length, rating, year, this.persons)) {
                        this.frontpage.showStartScreen(stage);
                    }

                });
                buttons.getChildren().add(saveButton);
            }
            return buttons;
        }
    }
