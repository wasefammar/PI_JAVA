package controllers.Echange;
import controllers.User.SessionUser;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Echange.EchangeService;
import models.Services.Service;

import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import services.EchangeServices.ServiceEchangeService;
import services.GestionServices.GestionService;

public class Transaction {
    public Button idValidate;
    public Button idWithdraw;
    public Button idRejectTransaction;
    public TableColumn<EchangeService, String>  id1;
    public TableColumn<EchangeService, String>  id2;
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
    GestionService userService = new GestionService();
    ServiceEchangeService serviceEchangeService = new ServiceEchangeService();

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
        int sessionUserId = userService.getIdUtilisateurByEmail(user.getAdresseEmail()).getId();
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
        serviceOutColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceOut().getTitreService()));

    }

    public void validateTransaction(ActionEvent actionEvent) throws SQLException {
        EchangeService echangeService = serviceOutTableView.getSelectionModel().getSelectedItem();
        System.out.println(echangeService.getServiceIn().getTitreService());
        serviceEchangeService.validateExchange(echangeService);
        serviceOutTableView.getItems().clear();
        populateServiceOutTable();

    }

    public void cancelTransaction(ActionEvent actionEvent) throws SQLException {
        EchangeService echangeService = serviceOutTableView.getSelectionModel().getSelectedItem();
        serviceEchangeService.deleteExchange(echangeService);
        serviceOutTableView.getItems().clear();
        populateServiceOutTable();
    }

    public void rejectTransaction(ActionEvent actionEvent) throws SQLException {
        EchangeService echangeService = serviceInTableView.getSelectionModel().getSelectedItem();
        serviceEchangeService.deleteExchange(echangeService);
        serviceInTableView.getItems().clear();
        populateServiceOutTable();
    }
}
