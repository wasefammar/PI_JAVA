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
import javafx.stage.Stage;
import models.Echange.EchangeService;
import services.EchangeServices.ServiceEchangeService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.sql.SQLException;

public class AfficheEchangeService {
    @FXML
    private TableView<EchangeService> exchangeTableView;
    @FXML
    private TableColumn<EchangeService, String> serviceInColumn;
    @FXML
    private TableColumn<EchangeService, String> serviceOutColumn;
    @FXML
    private TableColumn<EchangeService, LocalDateTime> dateEchangeColumn;
    @FXML
    private TableColumn<EchangeService, Boolean> valideColumn;
    @FXML
    private TableColumn<EchangeService, ?> actionsColumn;
    @FXML
    private Button updateButton;
    private ServiceEchangeService serviceEchangeService;
    private ObservableList<EchangeService> exchangeServices;

    public AfficheEchangeService() {
        serviceEchangeService = new ServiceEchangeService();
        exchangeServices = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        serviceInColumn.setCellValueFactory(new PropertyValueFactory<>("serviceIn"));
        serviceOutColumn.setCellValueFactory(new PropertyValueFactory<>("serviceOut"));
        dateEchangeColumn.setCellValueFactory(new PropertyValueFactory<>("dateEchange"));
        valideColumn.setCellValueFactory(new PropertyValueFactory<>("valide"));
        actionsColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));
        exchangeTableView.setItems(exchangeServices);
        loadExchangeServices();
    }

    private void loadExchangeServices() {
        try {
            exchangeServices.clear();
            exchangeServices.addAll(serviceEchangeService.getAllExchanges());
        } catch (SQLException e) {
            System.err.println("Error loading exchange services: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void updateExchange(ActionEvent event) {
        // Get the selected exchange service from the table view
        EchangeService selectedExchange = exchangeTableView.getSelectionModel().getSelectedItem();
        if (selectedExchange != null) {
            try {
                serviceEchangeService.updateExchange(selectedExchange);
                loadExchangeServices();
            } catch (SQLException e) {
                System.err.println("Error updating exchanged services: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void deleteExchange(ActionEvent event) {
        // Get the selected exchange service from the table view
        EchangeService selectedExchange = exchangeTableView.getSelectionModel().getSelectedItem();
        if (selectedExchange != null) {
            try {
                serviceEchangeService.deleteExchange(selectedExchange);
                exchangeServices.remove(selectedExchange);
            } catch (SQLException e) {
                System.err.println("Error deleting exchange : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void addExchange(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("EchangeService.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
