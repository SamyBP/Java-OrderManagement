package utcn.ordermanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import utcn.ordermanagement.data_access.IOrderRepository;
import utcn.ordermanagement.data_access.OrderRepository;
import utcn.ordermanagement.models.Order;
import utcn.ordermanagement.ui.Router;
import utcn.ordermanagement.ui.TableFactory;

public class OrdersViewController {
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
    private TableView<Order> ordersTable;
    @FXML
    private Button addOrderButton;

    private final Router router;
    private final IOrderRepository orderRepository;

    public OrdersViewController() {
        this.router = new Router();
        this.orderRepository = new OrderRepository();
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

        addOrderButton.setOnAction(event -> {
            Stage stage = (Stage) addOrderButton.getScene().getWindow();
            router.redirect("place-order-view.fxml", stage);
        });

        TableFactory.render(orderRepository, ordersTable, Order.class);

    }
}
