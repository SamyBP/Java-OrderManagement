package utcn.ordermanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import utcn.ordermanagement.data_access.IProductRepository;
import utcn.ordermanagement.data_access.ProductRepository;
import utcn.ordermanagement.models.Product;
import utcn.ordermanagement.ui.AlertPanel;
import utcn.ordermanagement.ui.Router;
import utcn.ordermanagement.ui.TableFactory;

import java.sql.SQLException;
import java.util.List;

public class ProductsViewController {
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
    private Button addProductButton;
    @FXML
    private TableView<Product> productsTable;

    private final IProductRepository productRepository;
    private final Router router;

    public ProductsViewController() {
        this.productRepository = new ProductRepository();
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

        productsTable.getColumns().clear();

        addProductButton.setOnAction(event -> {
            Stage stage = (Stage) addProductButton.getScene().getWindow();
            router.redirect("add-product-view.fxml", stage);
        });

       TableFactory.render(productRepository, productsTable, Product.class);
    }
}
