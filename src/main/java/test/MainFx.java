package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        //-------------------ADMIN-------------


        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherReclamation.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouter_reclamation.fxml"));

        //---------------------------CLIENT---------------------
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherFront.fxml"));

       //Parent root = FXMLLoader.load(getClass().getResource("/afficherReclamation.fxml"));
          Parent root = FXMLLoader.load(getClass().getResource("/afficher_reclamation1.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/ajouter_reclamation.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/dash_admin.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
