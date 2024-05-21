package utcn.ordermanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import utcn.ordermanagement.data_access.ClientRepository;
import utcn.ordermanagement.data_access.IClientRepository;
import utcn.ordermanagement.data_access.IProductRepository;
import utcn.ordermanagement.data_access.ProductRepository;
import utcn.ordermanagement.models.Product;
import utcn.ordermanagement.models.TopClient;
import utcn.ordermanagement.ui.Router;

import java.sql.SQLException;
import java.util.List;


public class HomeViewController {
    @FXML
    private StackPane mainChartPane;
    @FXML
    private StackPane topClientChartPane;

    @FXML
    private Button homeButton;
    @FXML
    private Button clientsButton;
    @FXML
    private Button productsButton;
    @FXML
    private Button billsButton;
    @FXML
    private Button ordersButton;

    private final Router router;
    private final IProductRepository productRepository;
    private final IClientRepository clientRepository;

    public HomeViewController() {
        this.router = new Router();
        this.productRepository = new ProductRepository();
        this.clientRepository = new ClientRepository();
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


        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Deposit stock");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        List<Product> depositProducts;
        try {
            depositProducts = productRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Product product : depositProducts) {
            series.getData().add(new XYChart.Data<>(product.getName(), product.getQuantity()));
        }

        chart.getData().add(series);

        mainChartPane.getChildren().add(chart);

        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis yAxis2 = new NumberAxis();

        BarChart<String, Number> chart2 = new BarChart<>(xAxis2, yAxis2);
        chart2.setTitle("Top client");

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        List<TopClient> topClients;
        try {
            topClients = clientRepository.findTopClients();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (TopClient topClient : topClients) {
            System.out.println(topClient);
            series2.getData().add(new XYChart.Data<>(topClient.getName(), topClient.getPrice()));
        }

        chart2.getData().add(series2);

        topClientChartPane.getChildren().add(chart2);

    }
}
