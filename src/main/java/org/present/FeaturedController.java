package org.present;

import javafx.event.ActionEvent;

import java.io.IOException;

public class FeaturedController {
    public void switchToMainScene(ActionEvent actionEvent) throws IOException {
        App.setRoot("primary");
    }
}
