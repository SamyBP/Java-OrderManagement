package utcn.ordermanagement.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utcn.ordermanagement.data_access.BillRepository;
import utcn.ordermanagement.data_access.ClientRepository;
import utcn.ordermanagement.data_access.IBillRepository;
import utcn.ordermanagement.data_access.IClientRepository;
import utcn.ordermanagement.models.Bill;
import utcn.ordermanagement.models.Client;
import utcn.ordermanagement.ui.Router;
import utcn.ordermanagement.ui.TableFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class BillViewController {
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
    private TableView<Bill> billTable;
    private final IBillRepository billRepository;
    private final Router router;

    public BillViewController() {
        this.billRepository = new BillRepository();
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

        billTable.getColumns().clear();
        try {
            List<Bill> bills = billRepository.findAll();

            if (!bills.isEmpty()) {
                TableColumn<Bill, Long> idColumn = new TableColumn<>("ID");
                idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().id()));

                TableColumn<Bill, Double> totalPriceColumn = new TableColumn<>("Total Price");
                totalPriceColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().totalPrice()));

                TableColumn<Bill, String> clientColumn = new TableColumn<>("Client");
                clientColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().clientName()));

                TableColumn<Bill, String> productColumn = new TableColumn<>("Product");
                productColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().productName()));

                TableColumn<Bill, LocalDateTime> createdAtColumn = new TableColumn<>("Emitted");
                createdAtColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().placedAt()));

                billTable.getColumns().addAll(idColumn, totalPriceColumn, clientColumn, productColumn, createdAtColumn);
                billTable.getItems().addAll(bills);
            }

            billTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
