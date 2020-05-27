package org.present;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.data.User;

import java.io.IOException;

public class LoginController {
    public TextField usernameTextField;
    public TextField passwordTextField;
    User user;

    @FXML
    //Temporary code to allow switching to production fxml
    private void switchToProduction() throws IOException {
        App.setRoot("Production");
    }

    public void onEnterButtonClick() throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        user = new User();
        user.getUsers();

        Alert a = new Alert(Alert.AlertType.NONE);

        boolean success= user.loginUser(username,password);
        if(success) {
            App.setRoot("Production");
        }
        else {
            // set alert type
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Login ERROR!\nUsername or Password you entered is wrong!");
            // show the dialog
            a.show();
        }
    }

    public void switchToPrimary(ActionEvent actionEvent) throws IOException {
        App.setRoot("primary");
    }
}