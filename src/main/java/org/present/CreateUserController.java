package org.present;

import javafx.event.ActionEvent;

import java.io.IOException;

public class CreateUserController {
    public void switchToAdminView(ActionEvent actionEvent) throws IOException {
        App.setRoot("AdminView");
}
}
