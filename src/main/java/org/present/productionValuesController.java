package org.present;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.data.Production;
import org.domain.PersistanceHandler;
import org.domain.ProductionDomain;

import java.io.IOException;

public class productionValuesController {
    public TextField IDTextField;
    public TextField nameTextField;
    public TextField genreTextField;
    public TextField releaseYearTextField;

    public Text idProductionColumn;
    public Text nameProductionColumn;
    public Text genreProductionColumn;
    public Text releaseProductionYearColumn;

    public void confirmProduction(ActionEvent actionEvent) {
        PersistanceHandler.getInstance().getProduction().add(new Production(Integer.parseInt(IDTextField.getText()), nameTextField.getText(), genreTextField.getText(), Integer.parseInt(releaseYearTextField.getText())));
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
