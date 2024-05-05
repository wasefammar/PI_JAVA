package org.example.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.models.Categorie;
import org.example.services.ServiceCategorie;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficheCategorie {

    @FXML
    private TableView<Categorie> categoryTable;

    @FXML
    private TableColumn<Categorie, String> nameColumn;

    @FXML
    private TableColumn<Categorie, String> typeColumn;

    @FXML
    private TextField SearchField;

    private final ServiceCategorie categorieService = new ServiceCategorie();
    private ObservableList<Categorie> categorieObservableList;

    @FXML
    private void initialize() {
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() throws SQLException {
        List<Categorie> categories = categorieService.recuperer();
        categorieObservableList = FXCollections.observableArrayList(categories);

        categoryTable.setItems(categorieObservableList);
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomCategorie()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
    }


    @FXML
    private void handleUpdate(ActionEvent event) {
        Categorie selectedCategorie = categoryTable.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null) {
            // Handle update action
        } else {
            showAlert("No Category Selected", "Please select a category to update.");
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        Categorie selectedCategorie = categoryTable.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null) {
            try {
                categorieService.supprimer(selectedCategorie.getId());
                categorieObservableList.remove(selectedCategorie);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Category Selected", "Please select a category to delete.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddCategory.fxml"));
            Parent root = loader.load();
            // Set any controller properties or methods here if needed
            categoryTable.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSortByType(ActionEvent event) {
        List<Categorie> sortedCategories = categorieService.SortByType( "ASC");
        categorieObservableList.setAll(sortedCategories);
    }
    @FXML
    private void handleSortByName(ActionEvent event) {
        List<Categorie> sortedCategories = categorieService.SortByName( "ASC");
        categorieObservableList.setAll(sortedCategories);
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String searchValue = SearchField.getText();
        List<Categorie> foundCategories = categorieService.chercher("nom_categorie", searchValue);
        categorieObservableList.setAll(foundCategories);
    }
    @FXML
    private void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AllServices.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Replace with a more robust logging mechanism
            System.out.println("An error occurred while loading the AjouterCategorie view: " + e.getMessage());
        }
    }
}
