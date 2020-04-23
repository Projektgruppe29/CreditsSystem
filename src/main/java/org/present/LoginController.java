package org.present;

import javafx.fxml.FXML;
import java.io.IOException;

public class LoginController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("Production");
    }
}