package controllers.Produit;

import controllers.Service.ServiceAdminItem;
import controllers.User.SessionUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Produit.Produit;
import models.Services.Service;
import services.ServicesProduit.GProduit;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProduitsAdmin implements Initializable {
    public Button users_btn;
    public Button Services_btn;
    public Button product_btn;
    public Button Transactions_btn;
    public Button events_btn;
    public Button complaints_btn;
    public Button logout_btn;
    public MenuButton idCategory;
    public VBox idServices;
    GProduit gp= new GProduit();

    public void users(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/Dashuser.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
       // users_btn.getScene().setRoot(anchorPane);
    }

    public void service(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ServicesAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
        //users_btn.getScene().setRoot(root);
    }

    public void product(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ProduitsAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
        //users_btn.getScene().setRoot(root);
    }

    public void transaction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/AfficheEchangeService.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
        //users_btn.getScene().setRoot(root);
    }

    public void events(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
        //users_btn.getScene().setRoot(anchorPane);
    }


    public void logout(ActionEvent actionEvent) throws IOException {
        SessionUser.resetSession();
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login ");
        stage.centerOnScreen();
        stage.show();
        //users_btn.getScene().setRoot(anchorPane);
    }

    public void complaint(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
        //users_btn.getScene().setRoot(root);
    }

    public void returnToCategories(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/CategorieFX.fxml"));        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
        //users_btn.getScene().setRoot(anchorPane);
    }

    public void returnToHome(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/dash_admin.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
        //users_btn.getScene().setRoot(anchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Produit> services = new ArrayList<>();
        try {
            services = gp.recuperer();
            for (Produit service: services) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ProduitAdminItem.fxml"));
                HBox box = fxmlLoader.load();
                ProduitAdminItem lcItemController = fxmlLoader.getController();
                lcItemController.setData(service);
                idServices.getChildren().add(box);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
