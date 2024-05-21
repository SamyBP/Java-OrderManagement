package utcn.ordermanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utcn.ordermanagement.data_access.ClientRepository;
import utcn.ordermanagement.data_access.IClientRepository;
import utcn.ordermanagement.models.Client;
import utcn.ordermanagement.ui.AlertPanel;
import utcn.ordermanagement.ui.Router;

import java.sql.SQLException;

public class ClientController {
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField address;
    @FXML
    private TextField phoneNumber;
    @FXML
    private Button submit;
    private final IClientRepository clientRepository;
    private final Router router;

    public ClientController() {
        this.clientRepository = new ClientRepository();
        this.router = new Router();
    }

    public void addNewClient() {

        Client client = new Client(
          name.getText(),
          email.getText(),
          address.getText(),
          phoneNumber.getText()
        );

        try {
            clientRepository.save(client);
            router.redirect("clients-view.fxml", (Stage) submit.getScene().getWindow());
        } catch (SQLException e) {
            AlertPanel.alert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
