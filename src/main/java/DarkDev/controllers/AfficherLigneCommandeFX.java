package DarkDev.controllers;

import DarkDev.models.LigneCommande;
import DarkDev.models.Produit;
import DarkDev.services.G_ligneCommande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherLigneCommandeFX implements Initializable {
    G_ligneCommande gl =new G_ligneCommande();
    ObservableList<Produit> obs;
    public TableView<Produit> tableViewlc;
    public TableColumn<Produit,String> imagelc;
    public TableColumn<Produit,String>  titrelc;
    public TableColumn<Produit,String> pricelc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            List<Produit> list = gl.ListeProduits();
            obs = FXCollections.observableArrayList(list);


        tableViewlc.setItems(obs);
        imagelc.setCellValueFactory(new PropertyValueFactory<>("photo"));
        titrelc.setCellValueFactory(new PropertyValueFactory<>("titreProduit"));
        pricelc.setCellValueFactory(new PropertyValueFactory<>("prix"));
    }  catch (SQLException e) {
        throw new RuntimeException(e);
    }}


    public void deletelc(ActionEvent actionEvent) {

        try {
            Produit produit = tableViewlc.getSelectionModel().getSelectedItem();

            if (produit != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Deletion");
                alert.setHeaderText("Are you sure you want to delete this command line from the card ?");
                alert.setContentText("This action cannot be undone.");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            gl.supprimer(produit.getId());
                            obs.remove(produit);
                        } catch (SQLException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Product Selected");
                alert.setHeaderText("Please select a command line to delete.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }



    }
}
