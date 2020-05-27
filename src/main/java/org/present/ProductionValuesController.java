package org.present;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.data.Production;
import org.domain.PersistanceHandler;

import java.io.IOException;

public class ProductionValuesController {
    public TextField nameTextField;
    public TextField genreTextField;
    public TextField releaseYearTextField;
    public Text nameProductionColumn;
    public Text genreProductionColumn;
    public Text releaseProductionYearColumn;

    public void confirmProduction(ActionEvent actionEvent) {
        PersistanceHandler.getInstance().createProduction(new Production(nameTextField.getText(), genreTextField.getText(), Integer.parseInt(releaseYearTextField.getText())));
        clearFields();
    }

    private void clearFields() {
        nameTextField.clear();
        genreTextField.clear();
        releaseYearTextField.clear();
    }

    public void switchToProduction(ActionEvent actionEvent) throws IOException {
        App.setRoot("Production");
    }
}
