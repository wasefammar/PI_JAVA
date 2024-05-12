package controllers.Echange;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Echange.EchangeProduit;
import services.EchangeServices.ServiceEchangeProduit;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AfficheEchangeProduit {
    @FXML
    private TableView<EchangeProduit> exchangeTableView;
    @FXML
    private TableColumn<EchangeProduit, String> productInColumn;
    @FXML
    private TableColumn<EchangeProduit, String> productOutColumn;
    @FXML
    private TableColumn<EchangeProduit, LocalDateTime> dateEchangeColumn;
    @FXML
    private TableColumn<EchangeProduit, Boolean> valideColumn;
    @FXML
    private TableColumn<EchangeProduit, ?> actionsColumn;
    @FXML
    private Button updateButton;
    private ServiceEchangeProduit serviceEchangeProduit;
    private ObservableList<EchangeProduit> exchangeProduits;

    public AfficheEchangeProduit() {
        serviceEchangeProduit = new ServiceEchangeProduit();
        exchangeProduits = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        productInColumn.setCellValueFactory(new PropertyValueFactory<>("productIn"));
        productOutColumn.setCellValueFactory(new PropertyValueFactory<>("productOut"));
        dateEchangeColumn.setCellValueFactory(new PropertyValueFactory<>("dateEchange"));
        valideColumn.setCellValueFactory(new PropertyValueFactory<>("valide"));
        actionsColumn.setCellValueFactory(new PropertyValueFactory<>("actions")); // Update this to match your EchangeProduit model
        exchangeTableView.setItems(exchangeProduits);
        loadExchangeProduits();
    }

    private void loadExchangeProduits() {
        try {
            exchangeProduits.clear();
            exchangeProduits.addAll(serviceEchangeProduit.getAllExchanges());
        } catch (SQLException e) {
            System.err.println("Error loading exchange produits: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void updateExchange(ActionEvent event) {
        // Get the selected exchange produit from the table view
        EchangeProduit selectedExchange = exchangeTableView.getSelectionModel().getSelectedItem();
        if (selectedExchange != null) {
            try {
                serviceEchangeProduit.updateExchange(selectedExchange);
                loadExchangeProduits();
            } catch (SQLException e) {
                System.err.println("Error updating exchanged produits: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void deleteExchange(ActionEvent event) {
        // Get the selected exchange produit from the table view
        EchangeProduit selectedExchange = exchangeTableView.getSelectionModel().getSelectedItem();
        if (selectedExchange != null) {
            try {
                serviceEchangeProduit.deleteExchange(selectedExchange);
                exchangeProduits.remove(selectedExchange);
            } catch (SQLException e) {
                System.err.println("Error deleting exchange : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void addExchange(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/EchangeProduit.fxml"));
            Stage stage = (Stage) exchangeTableView.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) exchangeTableView.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onmenuclicked(MouseEvent mouseEvent) {
    }

    public void onmenuclickedclose(MouseEvent mouseEvent) {
    }

    public void home(ActionEvent actionEvent) {
    }

    public void evenement(ActionEvent actionEvent) {
    }

    public void activite(ActionEvent actionEvent) {
    }

    public void reclamation(ActionEvent actionEvent) {
    }

    public void channel(ActionEvent actionEvent) {
    }
}
