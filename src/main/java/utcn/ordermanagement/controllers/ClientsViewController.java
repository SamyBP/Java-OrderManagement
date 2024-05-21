package utcn.ordermanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import utcn.ordermanagement.data_access.ClientRepository;
import utcn.ordermanagement.data_access.IClientRepository;
import utcn.ordermanagement.models.Client;
import utcn.ordermanagement.ui.AlertPanel;
import utcn.ordermanagement.ui.Router;
import utcn.ordermanagement.ui.TableFactory;

import java.sql.SQLException;
import java.util.List;

public class ClientsViewController {
    @FXML
    private Button homeButton;
    @FXML
    private Button clientsButton;
    @FXML
    private Button productsButton;
    @FXML
    private Button ordersButton;
    @FXML
    private Button billsButton;
    @FXML
    private Button addClientButton;

    @FXML
    private TableView<Client> clientsTable;
    private final IClientRepository clientRepository;
    private final Router router;

    public ClientsViewController() {
        this.clientRepository = new ClientRepository();
        this.router = new Router();
    }

    @FXML
    public void initialize() {
        SidebarController.init(
                homeButton,
                clientsButton,
                productsButton,
                ordersButton,
                billsButton,
                router
        );

        clientsTable.getColumns().clear();

        addClientButton.setOnAction(event -> {
            Stage stage = (Stage) addClientButton.getScene().getWindow();
            router.redirect("add-client-view.fxml", stage);
        });

        TableFactory.render(clientRepository, clientsTable, Client.class);
    }
}
