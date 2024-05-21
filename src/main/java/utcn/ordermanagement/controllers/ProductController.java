package utcn.ordermanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utcn.ordermanagement.data_access.IProductRepository;
import utcn.ordermanagement.data_access.ProductRepository;
import utcn.ordermanagement.models.Product;
import utcn.ordermanagement.ui.AlertPanel;
import utcn.ordermanagement.ui.Router;

import java.sql.SQLException;

public class ProductController {

    @FXML
    private Button submit;
    @FXML
    private TextField quantity;
    @FXML
    private TextField name;
    @FXML
    private TextField price;

    private final IProductRepository productRepository;
    private final Router router;

    public ProductController() {
        this.productRepository = new ProductRepository();
        this.router = new Router();
    }
    public void addNewProduct() {

        Product product = new Product(
                Integer.parseInt(quantity.getText()),
                name.getText(),
                Double.parseDouble(price.getText())
        );

        try {
            productRepository.save(product);
            Stage stage = (Stage) submit.getScene().getWindow();
            router.redirect("products-view.fxml", stage);
        } catch (SQLException e) {
            AlertPanel.alert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
