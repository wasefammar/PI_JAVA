package controllers.Echange;
import controllers.User.SessionUser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Echange.EchangeProduit;
import models.Echange.EchangeService;
import models.Produit.Produit;

import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import models.Services.Service;
import services.EchangeServices.ServiceEchangeProduit;
import services.EchangeServices.ServiceEchangeService;
import services.GestionServices.GestionService;
import services.ServicesProduit.GProduit;

public class Transaction2 {
    public Button idValidate;
    public Button idWithdraw;
    public Button idRejectTransaction;
    public TableColumn<EchangeService, String>  id1;
    public TableColumn<EchangeService, String>  id2;
    @FXML
    private TableView<EchangeProduit> produitInTableView;
    @FXML
    private TableColumn<EchangeProduit, String> produitInColumn1;
    @FXML
    private TableColumn<EchangeProduit, String> produitOutColumn1;
    @FXML
    private TableView<EchangeProduit> produitOutTableView;
    @FXML
    private TableColumn<EchangeProduit, String> produitInColumn2;
    @FXML
    private TableColumn<EchangeProduit, String> produitOutColumn2;
    GProduit userProduit = new GProduit();
    GestionService userService = new GestionService();
    ServiceEchangeProduit serviceEchangeProduit = new ServiceEchangeProduit();

    @FXML
    private void initialize() throws SQLException {
        // Populate tables
        populateProduitInTable();
        populateProduitOutTable();
        /*populateProduitInTable();
        populateProduitOutTable();*/
    }

    private void populateProduitInTable() throws SQLException {

        SessionUser user = SessionUser.getUser();
        int sessionUserId = userService.getIdUtilisateurByEmail(user.getAdresseEmail()).getId();
        List<Produit> produits = userProduit.getProduitByUserId(sessionUserId);
        ServiceEchangeProduit echangeProduit = new ServiceEchangeProduit();
        List<EchangeProduit> echangeProduits = echangeProduit.getExchangeByProduitsIn(produits);

        // Populate the table view with fetched records
        produitInTableView.getItems().addAll(echangeProduits);
        produitInColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduitOut().getTitreProduit()));
        produitOutColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduitOut().getTitreProduit()));
    }

    private void populateProduitOutTable() throws SQLException {
        // Fetch EchangeService records where ServiceIn belongs to the current user
        SessionUser user = SessionUser.getUser();
        int sessionUserId = userService.getIdUtilisateurByEmail(user.getAdresseEmail()).getId();
        GProduit userProduit = new GProduit();
        List<Produit> produits = userProduit.getProduitByUserId(sessionUserId);
        ServiceEchangeProduit echangeProduit = new ServiceEchangeProduit();
        List<EchangeProduit> echangeProduits = echangeProduit.getExchangeByProduitsOut(produits);
        System.out.println("Produits out:\n"+echangeProduits);
        // Populate the table view with fetched records
        produitOutTableView.getItems().addAll(echangeProduits);
        produitInColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduitIn().getTitreProduit()));
        produitOutColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduitOut().getTitreProduit()));

    }

    public void validateTransaction(ActionEvent actionEvent) throws SQLException {
        EchangeProduit echangeProduit = produitOutTableView.getSelectionModel().getSelectedItem();
        System.out.println(echangeProduit.getProduitIn().getTitreProduit());
        serviceEchangeProduit.validateExchange(echangeProduit);
        produitOutTableView.getItems().clear();
        populateProduitOutTable();
    }

    public void cancelTransaction(ActionEvent actionEvent) throws SQLException {
        EchangeProduit echangeProduit = produitInTableView.getSelectionModel().getSelectedItem();
        serviceEchangeProduit.deleteExchange(echangeProduit);
        produitInTableView.getItems().clear();
        populateProduitOutTable();
    }

    public void rejectTransaction(ActionEvent actionEvent) throws SQLException {
        EchangeProduit echangeProduit = produitOutTableView.getSelectionModel().getSelectedItem();
        serviceEchangeProduit.deleteExchange(echangeProduit);
        produitOutTableView.getItems().clear();
        populateProduitOutTable();
    }
}
