package org.present;

import javafx.event.ActionEvent;

import java.io.IOException;

public class AdminViewController {
    public void switchToCreateUser(ActionEvent actionEvent) throws IOException {
        App.setRoot("CreateUser");
    }
}
