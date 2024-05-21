package utcn.ordermanagement.controllers;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import utcn.ordermanagement.ui.Router;

public class SidebarController {
    public static void init(
            Button homeButton,
            Button clientsButton,
            Button productsButton,
            Button ordersButton,
            Button billsButton,
            Router router
    ) {
        homeButton.setOnAction(event -> {
            Stage stage = (Stage) homeButton.getScene().getWindow();
            router.redirect("home-view.fxml", stage);
        });
        clientsButton.setOnAction(event -> {
            Stage stage = (Stage) clientsButton.getScene().getWindow();
            router.redirect("clients-view.fxml", stage);
        });
        productsButton.setOnAction(event -> {
            Stage stage = (Stage) productsButton.getScene().getWindow();
            router.redirect("products-view.fxml", stage);
        });
        ordersButton.setOnAction(event -> {
            Stage stage = (Stage) ordersButton.getScene().getWindow();
            router.redirect("orders-view.fxml", stage);
        });
        billsButton.setOnAction(event -> {
            Stage stage = (Stage) billsButton.getScene().getWindow();
            router.redirect("bill-view.fxml", stage);
        });
    }
}
