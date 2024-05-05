package controllers.Reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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
        FXMLLoader loader  = FXMLLoader.load(getClass().getResource("/afficher_reclamation1.fxml"));
        AnchorPane anchorPane = loader.load();
        users_btn.getScene().setRoot(anchorPane);
    }

    public void service(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ServicesAdmin.fxml"));
        Parent root = loader.load();
        users_btn.getScene().setRoot(root);
    }

    public void product(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ProduitsAdmin.fxml"));
        Parent root = loader.load();
        users_btn.getScene().setRoot(root);
    }

    public void transaction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = FXMLLoader.load(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent root = loader.load();
        users_btn.getScene().setRoot(root);
    }

    public void events(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = FXMLLoader.load(getClass().getResource("/afficher_reclamation1.fxml"));
        AnchorPane anchorPane = loader.load();
        users_btn.getScene().setRoot(anchorPane);
    }


    public void logout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = FXMLLoader.load(getClass().getResource("/afficher_reclamation1.fxml"));
        AnchorPane anchorPane = loader.load();
        users_btn.getScene().setRoot(anchorPane);
    }

    public void complaint(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent root = loader.load();
        users_btn.getScene().setRoot(root);
    }
}
