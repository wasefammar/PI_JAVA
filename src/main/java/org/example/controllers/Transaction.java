package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.example.models.EchangeService;
import org.example.models.Service;
import org.example.services.ServiceEchangeService;
import org.example.services.GestionService;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Transaction {

    @FXML
    private TableView<EchangeService> serviceInTableView;

    @FXML
    private TableView<EchangeService> serviceOutTableView;

    @FXML
    private TableView<EchangeProduit> produitInTableView;

    @FXML
    private TableView<EchangeProduit> produitOutTableView;

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
        serviceInTableView.getItems().addAll(echangeServices);
    }

}
