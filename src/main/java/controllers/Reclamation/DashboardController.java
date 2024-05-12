package controllers.Reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {


    public Button users_btn;
    public Button Services_btn;
    public Button product_btn;
    public Button Transactions_btn;
    public Button events_btn;
    public Button complaints_btn;
    public Button logout_btn;



    public void users(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/Dashuser.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void service(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ServicesAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void product(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ProduitsAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void transaction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/AfficheEchangeService.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void events(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }


    public void logout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void complaint(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void returnToCategories(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/CategorieFX.fxml"));        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void returnToHome(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/dash_admin.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }
}
