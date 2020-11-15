package Main;

import Controllers.PrincipalController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainFX extends Application {
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("/fxml/Principal.fxml"));

        BorderPane root = loaderMenu.load();
        root.setMinWidth(500);
        root.setMaxWidth(850);
        root.setMinHeight(400);
        root.setMaxHeight(400);
        Scene scene = new Scene(root);

        primaryStage.setTitle("Tienda");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(true);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
