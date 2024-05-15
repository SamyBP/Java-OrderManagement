package utcn.ordermanagement.ui;

import javafx.scene.control.Alert;

public class AlertPanel {
    public static void alert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Order management");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
