package org.present;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class PrimaryController {

    @FXML
    public void switchToLogin() throws IOException {
        App.setRoot("Login");
    }

    public void switchToInfoKorpset(MouseEvent mouseEvent) throws IOException {
        App.setRoot("Korpset");
    }
}