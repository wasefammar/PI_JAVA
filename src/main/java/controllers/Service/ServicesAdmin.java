package controllers.Service;

import controllers.Produit.LcItemController;
import controllers.User.SessionUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Services.Service;
import services.GestionServices.GestionService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ServicesAdmin implements Initializable {
    public VBox idServices;
    public MenuButton idValidation;
    public MenuButton idCategory;

    GestionService gs = new GestionService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Service> services = new ArrayList<>();
        try {
            services = gs.recuperer();
            for (Service service : services) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ServiceAdminItem.fxml"));
                HBox box = fxmlLoader.load();
                ServiceAdminItem lcItemController = fxmlLoader.getController();
                lcItemController.setData(service);
                idServices.getChildren().add(box);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void users(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/Dashuser.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) idServices.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void service(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ServicesAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) idServices.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void product(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ProduitsAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) idServices.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void transaction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/AfficheEchangeService.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) idServices.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void events(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) idServices.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }


    public void logout(ActionEvent actionEvent) throws IOException {
        SessionUser.resetSession();
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) idServices.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void complaint(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) idServices.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void returnToCategories(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/CategorieFX.fxml"));        Parent anchorPane = loader.load();
        Stage stage = (Stage) idServices.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void returnToHome(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/dash_admin.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) idServices.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }
}
