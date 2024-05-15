package utcn.ordermanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utcn.ordermanagement.ui.SceneLayout;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("landing-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SceneLayout.WIDTH, SceneLayout.HEIGHT);
        stage.setTitle("Order Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch();
    }
}