package utcn.ordermanagement.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * A utility class used to rout and load a view in the existing stage
 */
public class Router {
    private static final String ROOT_DIR = "/utcn/ordermanagement/";

    /**
     * The method used to redirect to a specific view
     *
     * @param view A String representing a valid fxml view
     * @param stage A Stage representing the stage in which the view will be rendered
     *
     * @implNote The view and stage need to be valid
     * Example usage:
     *  Stage stage = (Stage) ?.getScene().getWindow();
     *  Router.redirect("view.fxml", stage);
     */
    public void redirect(String view, Stage stage) {
        try {

            String resource = ROOT_DIR + view;

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource)));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
