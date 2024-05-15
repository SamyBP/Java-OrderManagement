package utcn.ordermanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utcn.ordermanagement.data_access.UserRepository;
import utcn.ordermanagement.services.AuthenticationService;
import utcn.ordermanagement.services.InvalidCredentialsException;
import utcn.ordermanagement.services.UsernameAlreadyTakenException;
import utcn.ordermanagement.ui.AlertPanel;
import utcn.ordermanagement.ui.Router;

import java.sql.SQLException;

public class AuthenticationController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    private final AuthenticationService authenticationService;
    private final Router router;

    public AuthenticationController() {
        this.authenticationService = new AuthenticationService(new UserRepository());
        this.router = new Router();
    }

    public void login() {
        try {
            if (authenticationService.login(username.getText(), password.getText())) {
                router.redirect("home-view.fxml", (Stage) username.getScene().getWindow());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidCredentialsException e) {
            AlertPanel.alert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void register() {
        if (password.getText().equals(confirmPassword.getText())) {
            try {
                authenticationService.register(username.getText(), password.getText());
                router.redirect("login-view.fxml", (Stage) username.getScene().getWindow());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (UsernameAlreadyTakenException e) {
                AlertPanel.alert(e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
}
