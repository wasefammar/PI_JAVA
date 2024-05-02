package org.example.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.Service;
import org.example.services.GestionService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AllServices implements Initializable {

    public Button idDeleteService;
    public Button idAddService;
    public Button idUpdateService;
    public Button idShowComments;
    GestionService gs = new GestionService();
    ObservableList<Service> serviceObservableList;
    public TableView<Service> servicesTable;
    public TableColumn<Service, Integer> Id;
    public TableColumn<Service, String> photo;
    public TableColumn<Service, String> title;
    public TableColumn<Service, String> description;
    public TableColumn<Service, String> ville;
    public TableColumn<Service, String> exchange;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("java version: "+System.getProperty("java.version"));
            System.out.println("javafx.version: " + System.getProperty("javafx.version"));
            List<Service> list = gs.Afficher();
            serviceObservableList = FXCollections.observableArrayList(list);

            servicesTable.setItems(serviceObservableList);
            Id.setCellValueFactory(new PropertyValueFactory<>("id"));
            photo.setCellValueFactory(new PropertyValueFactory<>("photo"));
            title.setCellValueFactory(new PropertyValueFactory<>("titreService"));
            description.setCellValueFactory(new  PropertyValueFactory<>("descriptionService"));
            ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
            exchange.setCellValueFactory(cellData -> {
                String value = cellData.getValue().isChoixEchange() == 0 ? "Yes" : "No";
                return new SimpleStringProperty(value);}
);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addService(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddService.fxml"));
            Parent root = loader.load();
            servicesTable.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void updateService(ActionEvent event) {
        try {
            Service service = servicesTable.getSelectionModel().getSelectedItem();

            if (service != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateServiceFX.fxml"));
                Parent root = loader.load();
                UpdateService controller = loader.getController();
               // controller.setFields(service.getId(), service.getTitreService(), service.getDescriptionService(), service.getPhoto(), service.getVille(), service.isChoixEchange(), gs.getCategoryById(service.getId()));
                servicesTable.getScene().setRoot(root);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Service Selected");
                alert.setHeaderText("Please select a service to delete.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteService(ActionEvent event) {
        try {
            Service service = servicesTable.getSelectionModel().getSelectedItem();

            if (service != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Deletion");
                alert.setHeaderText("Are you sure you want to delete this service?");
                alert.setContentText("This action cannot be undone.");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            gs.supprimer(service.getId());
                            serviceObservableList.remove(service);
                        } catch (SQLException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Service Selected");
                alert.setHeaderText("Please select a service to delete.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void showComments(ActionEvent event) {

        try {
            Service service = servicesTable.getSelectionModel().getSelectedItem();

            if (service != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommentsFX.fxml"));
                Parent root = loader.load();
                Comments controller = loader.getController();
                controller.setFields( service.getTitreService(), service.getId());
                servicesTable.getScene().setRoot(root);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Service Selected");
                alert.setHeaderText("Please select a service to show its comments.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
