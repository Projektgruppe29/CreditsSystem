package org.present;

import javafx.fxml.FXML;

import java.io.IOException;

public class CastController {
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
