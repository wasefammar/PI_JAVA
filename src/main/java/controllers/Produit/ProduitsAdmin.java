package controllers.Produit;

import controllers.Service.ServiceAdminItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
