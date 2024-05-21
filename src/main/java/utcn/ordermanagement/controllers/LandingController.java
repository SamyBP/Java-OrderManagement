package utcn.ordermanagement.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utcn.ordermanagement.ui.Router;

import java.io.IOException;

public class LandingController {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    private final Router router;

    public LandingController() {
        this.router = new Router();
    }

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            router.redirect("login-view.fxml", stage);
        });
        registerButton.setOnAction(event -> {
            Stage stage = (Stage) registerButton.getScene().getWindow();
            router.redirect("register-view.fxml", stage);
        });
    }
}