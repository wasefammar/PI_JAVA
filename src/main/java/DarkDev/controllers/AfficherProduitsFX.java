
package DarkDev.controllers;

import DarkDev.models.LigneCommande;
import DarkDev.models.Produit;
import DarkDev.services.GProduit;
import DarkDev.services.G_ligneCommande;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherProduitsFX implements Initializable {
    public Button idAddProduct;
    public Button idUpdateProduct;
    public TableColumn<Produit,String> exchange1;

    GProduit gp = new GProduit();

    G_ligneCommande gl = new G_ligneCommande();
    LigneCommande lc = new LigneCommande();

    ObservableList<Produit> obs;
    FilteredList<Produit> fp;

    @FXML
    private TableColumn<Produit, String> image1;

    @FXML
    private TableColumn<Produit, String> titre1;

    @FXML
    private TableColumn<Produit, String> description1;

    public TableColumn<Produit,Float> price1;

    @FXML
    private TableColumn<Produit, String> state1;

    @FXML
    private TableColumn<Produit, String> city1;

    @FXML
    private TableView<Produit> tableView;


    @FXML
    void delete (ActionEvent event) {
        try {
            Produit produit = tableView.getSelectionModel().getSelectedItem();

            if (produit != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Deletion");
                alert.setHeaderText("Are you sure you want to delete this product ?");
                alert.setContentText("This action cannot be undone.");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            gp.supprimer(produit.getId());
                            obs.remove(produit);
                        } catch (SQLException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Product Selected");
                alert.setHeaderText("Please select a product to delete.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<Produit> list = gp.recuperer();
            obs = FXCollections.observableArrayList(list);


            tableView.setItems(obs);
            image1.setCellValueFactory(new PropertyValueFactory<>("photo"));
            titre1.setCellValueFactory(new PropertyValueFactory<>("titreProduit"));
            description1.setCellValueFactory(new PropertyValueFactory<>("descriptionProduit"));
            city1.setCellValueFactory(new PropertyValueFactory<>("ville"));
            state1.setCellValueFactory(new PropertyValueFactory<>("etat"));
            price1.setCellValueFactory(new PropertyValueFactory<>("prix"));
            price1.setCellValueFactory(new PropertyValueFactory<>("prix"));
            exchange1.setCellValueFactory(cellData -> {
                String value = cellData.getValue().getChoixEchange() == 0 ? "Yes" : "No";
                return new SimpleStringProperty(value);}
            );




/*            image1.setCellFactory(
                    param -> new TableCell<Produit, String>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        try {
                            // Load the image from the file path
                            Image image = new Image(new File(item).toURI().toString());
                            imageView.setImage(image);
                            imageView.setFitHeight(10);
                            imageView.setFitWidth(10);
                            setGraphic(imageView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });*/



        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    public void addProduct(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduitFX.fxml"));
            Parent root = loader.load();
            tableView.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateProduct(ActionEvent actionEvent) {
        try {
            Produit produit = tableView.getSelectionModel().getSelectedItem();

            if (produit != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateProduitFX.fxml"));
                Parent root = loader.load();
                UpdateProduit controller = loader.getController();
                controller.setFields(produit.getId(), produit.getTitreProduit(), produit.getDescriptionProduit(), produit.getPhoto(), produit.getVille(), produit.getChoixEchange(), gp.getCategoryById(produit.getId()),produit.getEtat(),produit.getPrix());
                tableView.getScene().setRoot(root);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Product Selected");
                alert.setHeaderText("Please select a Product to Update.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void addtocard(ActionEvent actionEvent) {


        try {
            Produit produit = tableView.getSelectionModel().getSelectedItem();

            if (produit != null) {
                if(gl.tester(produit.getId())){
                    lc.setIdProduit(produit.getId());
                    lc.setIdPanier(1);
                    gl.ajouter(lc);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLigneCommandeFX.fxml"));
                    Parent root = loader.load();
//                AfficherLigneCommandeFX controller = loader.getController();
//                controller.
                    tableView.getScene().setRoot(root);
                }else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Echec");
                    alert.setHeaderText("Product already exist in card.");
                    alert.showAndWait();
                }


            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Product Selected");
                alert.setHeaderText("Please select a Product to add to card.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    }





