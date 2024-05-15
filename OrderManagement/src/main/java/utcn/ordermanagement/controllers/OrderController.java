package utcn.ordermanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utcn.ordermanagement.data_access.*;
import utcn.ordermanagement.models.Bill;
import utcn.ordermanagement.models.Client;
import utcn.ordermanagement.models.Order;
import utcn.ordermanagement.models.Product;
import utcn.ordermanagement.services.BillService;
import utcn.ordermanagement.ui.AlertPanel;
import utcn.ordermanagement.ui.Router;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrderController {
    @FXML
    private TextField quantity;
    @FXML
    private ComboBox<Client> client;
    @FXML
    private ComboBox<Product> product;
    @FXML
    private Button submit;
    private final Router router;
    private final IClientRepository clientRepository;
    private final IProductRepository productRepository;
    private final IOrderRepository orderRepository;
    private final IBillRepository billRepository;

    public OrderController() {
        this.router = new Router();
        this.productRepository = new ProductRepository();
        this.clientRepository = new ClientRepository();
        this.orderRepository = new OrderRepository();
        this.billRepository = new BillRepository();
    }

    @FXML
    public void initialize() {
        try {
            client.getItems().addAll(clientRepository.findAll());
            product.getItems().addAll(productRepository.findAll());
        } catch (SQLException e) {
            AlertPanel.alert(e.getMessage(), Alert.AlertType.ERROR);
        }

        submit.setOnAction(event -> {
            placeNewOrder();
        });
    }

    public void placeNewOrder() {
        Client selectedClient = client.getValue();
        Product selectedProduct = product.getValue();
        int selectedQuantity = 0;
        try {
            selectedQuantity = Integer.parseInt(quantity.getText());
        } catch (NumberFormatException e) {
            AlertPanel.alert("Please enter a number", Alert.AlertType.WARNING);
        }

        if (selectedClient == null) {
            AlertPanel.alert("Select a client to proceed", Alert.AlertType.WARNING);
        }

        if (selectedProduct == null) {
            AlertPanel.alert("Select a product to proceed", Alert.AlertType.WARNING);
            return;
        }

        if (selectedQuantity > selectedProduct.getQuantity()) {
            AlertPanel.alert("Not enough stock. Please refill your deposit and comeback.", Alert.AlertType.WARNING);
            router.redirect("products-view.fxml", (Stage) submit.getScene().getWindow());
            return;
        }

        try {
            selectedProduct.setQuantity(selectedProduct.getQuantity() - selectedQuantity);
            double totalPrice = selectedQuantity * selectedProduct.getPrice();
            productRepository.update(selectedProduct);
            orderRepository.save(
                    new Order(
                            selectedQuantity,
                            totalPrice,
                            selectedClient,
                            selectedProduct
                    )
            );

            Bill bill = new Bill(
                    null,
                    totalPrice,
                    selectedClient.getName(),
                    selectedProduct.getName(),
                    LocalDateTime.now()
            );

            billRepository.save(bill);
            BillService.generateBill(bill);

            AlertPanel.alert("Order placed successfully.A bill was created", Alert.AlertType.CONFIRMATION);
            router.redirect("orders-view.fxml", (Stage) submit.getScene().getWindow());
        } catch (SQLException e) {
            AlertPanel.alert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
