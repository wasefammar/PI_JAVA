package controllers.Service;

import controllers.Produit.LcItemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    GestionService gs= new GestionService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Service> services = new ArrayList<>();
        try {
            services = gs.recuperer();
            for (Service service: services) {
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

    public void users(ActionEvent actionEvent) {
    }

    public void service(ActionEvent actionEvent) {
    }

    public void product(ActionEvent actionEvent) {
    }

    public void transaction(ActionEvent actionEvent) {
    }

    public void events(ActionEvent actionEvent) {
    }

    public void complaints(ActionEvent actionEvent) {
    }

    public void logout(ActionEvent actionEvent) {
    }

    public void stat(ActionEvent actionEvent) {
    }



}
