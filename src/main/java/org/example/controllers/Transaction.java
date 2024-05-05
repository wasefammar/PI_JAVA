package org.example.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.example.models.EchangeService;
import org.example.models.Service;
import org.example.services.ServiceEchangeService;
import org.example.services.GestionService;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

public class Transaction {
    @FXML
    private TableView<EchangeService> serviceInTableView;
    @FXML
    private TableColumn<EchangeService, String> serviceInColumn1;
    @FXML
    private TableColumn<EchangeService, String> serviceOutColumn1;
    @FXML
    private TableView<EchangeService> serviceOutTableView;
    @FXML
    private TableColumn<EchangeService, String> serviceInColumn2;
    @FXML
    private TableColumn<EchangeService, String> serviceOutColumn2;

    @FXML
    private void initialize() throws SQLException {
        // Populate tables
        populateServiceInTable();
        populateServiceOutTable();
        /*populateProduitInTable();
        populateProduitOutTable();*/
    }
    // Populate the TableView with EchangeService records where ServiceIn belongs to the current user
    private void populateServiceInTable() throws SQLException {
        // Fetch EchangeService records where ServiceIn belongs to the current user

        SessionUser user = SessionUser.getUser();
        int sessionUserId = user.getId();
        GestionService userService = new GestionService();
        List<Service> services = userService.getServiceByUserId(sessionUserId);
        ServiceEchangeService echangeService = new ServiceEchangeService();
        List<EchangeService> echangeServices = echangeService.getExchangeByServicesIn(services);
        // Populate the table view with fetched records
        serviceInTableView.getItems().addAll(echangeServices);
        serviceInColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));
        serviceOutColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));

    }

    private void populateServiceOutTable() throws SQLException {
        // Fetch EchangeService records where ServiceIn belongs to the current user
        SessionUser user = SessionUser.getUser();
        int sessionUserId = user.getId();
        GestionService userService = new GestionService();
        List<Service> services = userService.getServiceByUserId(sessionUserId);
        ServiceEchangeService echangeService = new ServiceEchangeService();
        List<EchangeService> echangeServices = echangeService.getExchangeByServicesOut(services);
        // Populate the table view with fetched records
        serviceOutTableView.getItems().addAll(echangeServices);
        serviceInColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));
        serviceOutColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));

    }

}
