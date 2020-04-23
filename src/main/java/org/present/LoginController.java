package org.present;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.domain.User;

import java.io.IOException;

public class LoginController {
    public TextField usernameTextField;
    public TextField passwordTextField;

    @FXML
    //Temporary code to allow switching to production fxml
    private void switchToProduction() throws IOException {
        App.setRoot("Production");
    }

    public void onEnterButtonClick() throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        Alert a = new Alert(Alert.AlertType.NONE);

        boolean success= User.login(username,password);
        if(success){
           App. setRoot("Production");
    }

        else {
            // set alert type
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Login ERROR!\nUsername or Password you entered is wrong!");
            // show the dialog
            a.show();
        }
    }
}