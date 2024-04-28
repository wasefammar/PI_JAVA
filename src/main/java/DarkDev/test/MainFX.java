package DarkDev.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


    public class MainFX extends Application {
        public static final String CURRENCY = "DT";

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root,1315 , 809);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Gérer des produits");
                primaryStage.setResizable(false);
                primaryStage.show();

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

