package org.present;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ViewCreditController {

    public void switchToPrimaryScene(ActionEvent actionEvent) throws IOException {
        App.setRoot("primary");
    }

    public void AddImage(MouseEvent mouseEvent) {
    }
}
