package DarkDev.controllers;

import DarkDev.models.LigneCommande;
import DarkDev.models.Produit;
import DarkDev.services.GProduit;
import DarkDev.services.G_ligneCommande;
import DarkDev.test.MainFX;
import DarkDev.test.MyListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AfficherProduitsController implements Initializable {



    List<Produit> produits = new ArrayList<>();
    GProduit gp = new GProduit();

    G_ligneCommande glc= new G_ligneCommande();
    Produit produit = new Produit();
    public MenuButton idcurrency;
    public MenuButton idFilterByExchange;
    public MenuButton idOrderByPrice;
    public MenuButton idFilterByState1;
    public MenuButton idFilterByPrice;
    public TextField idSearchField;
    public MenuButton idCategories;
    public Label idProduit;
    @FXML
    private VBox ChosenFruitCtard;

    @FXML
    private Label titre1;

    @FXML
    private ImageView image1;

    @FXML
    private Label price1;



    @FXML
    private Text description1;


    @FXML
    private Label exchange1;

    @FXML
    private Label state1;

    @FXML
    private Label city1;


    @FXML
    private Text category1;


    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    private Image image;
    private MyListener myListener;


    int column = 3;
    int row = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            produits= gp.recuperer();
            List<String> categories = gp.ListeCategories();
            MenuItem menuItem1 = new MenuItem("All");
            menuItem1.addEventHandler(ActionEvent.ACTION, e->{
                try {
                    filtreByCategory("All");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            idCategories.getItems().add(menuItem1);
            for (MenuItem menuItem: idFilterByPrice.getItems()){
                menuItem.addEventHandler(ActionEvent.ACTION, e->{
                    filterByPrice(menuItem.getText());
                });
            }

            for (MenuItem menuItem: idFilterByState1.getItems()){
                menuItem.addEventHandler(ActionEvent.ACTION, e->{
                    filterByState(menuItem.getText());
                });
            }

            for (MenuItem menuItem: idOrderByPrice.getItems()){
                menuItem.addEventHandler(ActionEvent.ACTION, e->{
                    OrdererByPrice(menuItem.getText());
                });
            }

            for (MenuItem menuItem: idcurrency.getItems()){
                menuItem.addEventHandler(ActionEvent.ACTION, e->{
                    changeCurrency(menuItem.getText());
                });
            }

            idFilterByExchange.getItems().get(0).addEventHandler(
                    ActionEvent.ACTION, e->{
                        filterByExchangeChoice("Both");
                    }
            );
            idFilterByExchange.getItems().get(1).addEventHandler(
                    ActionEvent.ACTION, e->{
                        filterByExchangeChoice("Exchangeable");
                    }
            );
            idFilterByExchange.getItems().get(2).addEventHandler(
                    ActionEvent.ACTION, e->{
                        filterByExchangeChoice("Non exchangeable");
                    }
            );

            for (String cat: categories){
                MenuItem menuItem = new MenuItem(cat);
                menuItem.addEventHandler(ActionEvent.ACTION, e->{
                    try {
                        filtreByCategory(cat);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                idCategories.getItems().add(menuItem);
            }

            if (!produits.isEmpty()) {
                setChosenFruit(produits.get(0));
                myListener = this::setChosenFruit;
            }
            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemController.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
//                VBox box = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(produits.get(i), myListener,"DT");

              if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, ++column, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

            //searchFilter();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



    private void setChosenFruit(Produit produit) {
        if (produit!=null){
            ObservableList<Node> list= ChosenFruitCtard.getChildren();
            for (Node node: list){
                node.setVisible(true);
            }
            idProduit.setText(""+produit.getId());
            titre1.setText(produit.getTitreProduit());
            if(idcurrency.getText().equals("DT") || idcurrency.getText().equals("currency")){
                price1.setText( produit.getPrix()+" "+"DT" );
            } else if (idcurrency.getText().equals("Euro €")) {
                price1.setText( String.format("%.2f",produit.getPrix()*0.3)+" "+"Euro €" );
            }else if (idcurrency.getText().equals("Dollar $")){
                price1.setText( String.format("%.2f",produit.getPrix()*0.32)+" "+"Dollar $" );
            }
            description1.setText(produit.getDescriptionProduit());
            try {
                category1.setText(gp.getCategoryById(produit.getIdCategorie()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            exchange1.setText(produit.getChoixEchange() == 0 ? "No":"Yes");
            city1.setText(produit.getVille());
            state1.setText(produit.getEtat());
            File imageFile= new File(produit.getPhoto());
            image1.setImage(new Image(imageFile.toURI().toString()));
        }
        else {
            HBox box= (HBox) ChosenFruitCtard.getChildren().get(0);
           Label label= (Label) box.getChildren().get(0);
           label.setText("No Product");
            ObservableList<Node> list= ChosenFruitCtard.getChildren();
            for (int i=1; i< list.size(); i++){
                list.get(i).setVisible(false);
            }
        }


    }

    public void addform(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
            Parent root = loader.load();
            titre1.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void updateform(ActionEvent event) {

        try {
            Produit produit1 = gp.getProduitById(Integer.parseInt(idProduit.getText()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateProduit.fxml"));
            Parent root = loader.load();
            UpdateProduitController updateProduit = loader.getController();
            updateProduit.setFields(produit1);
            titre1.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void cartform(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
            Parent root = loader.load();
            PanierController panierController = loader.getController();
            panierController.setFields(idcurrency.getText());
            titre1.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }


    @FXML
    void deleteproduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this Product?");
        alert.setContentText("This action cannot be undone.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    gp.supprimer(Integer.parseInt(idProduit.getText()));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
                    Parent root = loader.load();
                    description1.getScene().setRoot(root);

                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    @FXML
    void addtocart(ActionEvent event) {

        try {
            LigneCommande lc = new LigneCommande(1,Integer.parseInt(idProduit.getText()));
            if(glc.tester(lc.getIdProduit())){
                glc.ajouter(lc);

            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Duplicate");
                alert.setHeaderText("This product already exist in your cart");

                alert.showAndWait();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }




}
