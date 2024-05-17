package controllers.Produit;


import controllers.Echange.EchangeProduitController;
import controllers.Echange.EchangeServiceController;
import controllers.User.SessionTempo;
import controllers.User.SessionUser;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Produit.LigneCommande;
import models.Produit.Produit;
import services.GestionServices.GestionService;
import services.ServicesProduit.GProduit;
import services.ServicesProduit.G_ligneCommande;
import utils.MyListener;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AfficherProduitsController implements Initializable {


    public Button idDelete;
    public Button idAddToCart;
    public Button idUpdate;
    public Button idExchange;
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

    GestionService gs = new GestionService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            System.out.println(SessionTempo.getUser());
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

            searchFilter();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



    private void setChosenFruit(Produit produit) throws SQLException {
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
            if(produit.getIdUtilisateur()!=gs.getIdUtilisateurByEmail(SessionUser.getUser().getAdresseEmail()).getId()){
              idDelete.setVisible(false);
              idUpdate.setVisible(false);
              idAddToCart.setVisible(true);
              idExchange.setVisible(produit.getChoixEchange() == 1);

            }else {
                idDelete.setVisible(true);
                idUpdate.setVisible(true);
                idAddToCart.setVisible(false);
                idExchange.setVisible(false);
            }
            exchange1.setText(produit.getChoixEchange() == 0 ? "No":"Yes");
            city1.setText(produit.getVille());
            state1.setText(produit.getEtat());
            File imageFile= new File("F:\\ESPRIT\\pidev-main (2)\\pidev-main\\public\\uploads\\produits\\"+produit.getPhoto());;
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
            Stage stage = (Stage) idProduit.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
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
            Stage stage = (Stage) titre1.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
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
            Stage stage = (Stage) titre1.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
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
            int panierId = glc.getPanierByIdUtilisateur(gs.getIdUtilisateurByEmail(SessionUser.getUser().getAdresseEmail()).getId());
            LigneCommande lc = new LigneCommande(panierId,Integer.parseInt(idProduit.getText()));
            if(glc.tester(lc.getIdProduit())){
                glc.ajouter(lc);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Product added to cart");
                alert.setHeaderText("Product added to cart successfully");

                alert.showAndWait();

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


    public void filtreByCategory(String category) throws SQLException, IOException {
        int column=3, row=0;
        if(!idCategories.getText().equals(category)){
            grid.getChildren().clear();
            idCategories.setText(category);
            try {
                if(!category.equals("All") && !idCategories.getText().equals("FilterByCategory")){
                    produits = gp.rechercherParCategory(gp.getCategoryByName(category));
                    if (idFilterByState1.getText().equals("All")|| idFilterByState1.getText().equals("FilterByState")){
                        idFilterByState1.setText("All");
                    }
                    if (idFilterByState1.getText().equals("New")){
                        idFilterByState1.setText("New");
                        produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("New")).toList();
                    }
                    if (idFilterByState1.getText().equals("Hardly_Used")){
                        idFilterByState1.setText("Hardly_Used");

                        produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Hardly_Used")).toList();
                    }
                    if (idFilterByState1.getText().equals("Used")){
                        idFilterByState1.setText("Used");
                        produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Used")).toList();
                    }

                    if (idFilterByPrice.getText().equals("All") || idFilterByPrice.getText().equals("FilterByPrice")){
                        idFilterByPrice.setText("All");
                    }
                    if (idFilterByPrice.getText().equals("0-100 DT")){
                        idFilterByPrice.setText("0-100 DT");
                        produits= produits.stream().filter(produit1 -> produit1.getPrix()>0 && produit1.getPrix()<= 100).toList();
                    }
                    if (idFilterByPrice.getText().equals("100-200 DT")){
                        idFilterByPrice.setText("All");idFilterByPrice.setText("100-200 DT");
                        produits= produits.stream().filter(produit1 -> produit1.getPrix()>100 && produit1.getPrix()<= 200).toList();
                    }
                    if (idFilterByPrice.getText().equals("200-300 DT")){
                        idFilterByPrice.setText("200-300 DT");
                        produits= produits.stream().filter(produit1 -> produit1.getPrix()>200 && produit1.getPrix()<= 300).toList();
                    }
                    if (idFilterByPrice.getText().equals("300-400 DT")){
                        idFilterByPrice.setText("300-400 DT");
                        produits= produits.stream().filter(produit1 -> produit1.getPrix()>300 && produit1.getPrix()<= 400).toList();
                    }
                    if (idFilterByPrice.getText().equals(">400 DT")){
                        idFilterByPrice.setText(">400 DT");
                        produits= produits.stream().filter(produit1 -> produit1.getPrix()>400).toList();
                    }

                    if(idOrderByPrice.getText().equals("All") || idOrderByPrice.getText().equals("OrderByPrice")){
                        idOrderByPrice.setText("All");
                    }
                    if(idOrderByPrice.getText().equals("ASC")){
                        idOrderByPrice.setText("ASC");
                        produits= produits.stream().sorted((p1,p2)-> (int) (p1.getPrix()-p2.getPrix())).toList();

                    }
                    if(idOrderByPrice.getText().equals("DESC")){
                        idOrderByPrice.setText("DESC");
                        produits=  produits.stream().sorted((p1, p2)-> (int) (p2.getPrix()-p1.getPrix())).toList();
                        //Collections.reverse(produits);
                    }

                    if(idFilterByExchange.getText().equals("Both") || idFilterByExchange.getText().equals("FilterByExchange")){
                        idFilterByExchange.setText("Both");
                    }
                    if(idFilterByExchange.getText().equals("Exchangeable")){
                        idFilterByExchange.setText("Exchangeable");
                        produits= produits.stream().filter(e->e.getChoixEchange()==1).toList();

                    }
                    if(idFilterByExchange.getText().equals("Non exchangeable")){
                        idFilterByExchange.setText("Non exchangeable");
                        produits= produits.stream().filter(e->e.getChoixEchange()==0).toList();

                    }

                    if(idcurrency.getText().equals("DT") || idcurrency.getText().equals("currency")){
                        idcurrency.setText("DT");
                    }
                    if(idcurrency.getText().equals("Dollar $")){
                        idcurrency.setText("Dollar $");
                    }
                    if(idcurrency.getText().equals("Euro €")){
                        idcurrency.setText("Euro €");
                    }

                    if (!produits.isEmpty()) {
                        setChosenFruit(produits.get(0));
                        myListener = this::setChosenFruit;
                    }else {
                        setChosenFruit(null);
                    }
                    for (int i = 0; i < produits.size(); i++) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/ItemController.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
//                VBox box = fxmlLoader.load();

                        ItemController itemController = fxmlLoader.getController();
                        itemController.setData(produits.get(i), myListener, idcurrency.getText());

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
                }
                else {
                    produits = gp.recuperer();
                    if (idFilterByState1.getText().equals("All")|| idFilterByState1.getText().equals("FilterByState")){
                        idFilterByState1.setText("All");
                    }
                    if (idFilterByState1.getText().equals("New")){
                        idFilterByState1.setText("New");
                        produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("New")).toList();
                    }
                    if (idFilterByState1.getText().equals("Hardly_Used")){
                        idFilterByState1.setText("Hardly_Used");

                        produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Hardly_Used")).toList();
                    }
                    if (idFilterByState1.getText().equals("Used")){
                        idFilterByState1.setText("Used");
                        produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Used")).toList();
                    }

                    if (idFilterByPrice.getText().equals("All") || idFilterByPrice.getText().equals("FilterByPrice")){
                        idFilterByPrice.setText("All");
                    }
                    if (idFilterByPrice.getText().equals("0-100 DT")){
                        idFilterByPrice.setText("0-100 DT");
                        produits= produits.stream().filter(produit1 -> produit1.getPrix()>0 && produit1.getPrix()<= 100).toList();
                    }
                    if (idFilterByPrice.getText().equals("100-200 DT")){
                        idFilterByPrice.setText("All");idFilterByPrice.setText("100-200 DT");
                        produits= produits.stream().filter(produit1 -> produit1.getPrix()>100 && produit1.getPrix()<= 200).toList();
                    }
                    if (idFilterByPrice.getText().equals("200-300 DT")){
                        idFilterByPrice.setText("200-300 DT");
                        produits= produits.stream().filter(produit1 -> produit1.getPrix()>200 && produit1.getPrix()<= 300).toList();
                    }
                    if (idFilterByPrice.getText().equals("300-400 DT")){
                        idFilterByPrice.setText("300-400 DT");
                        produits= produits.stream().filter(produit1 -> produit1.getPrix()>300 && produit1.getPrix()<= 400).toList();
                    }
                    if (idFilterByPrice.getText().equals(">400 DT")){
                        idFilterByPrice.setText(">400 DT");
                        produits= produits.stream().filter(produit1 -> produit1.getPrix()>400).toList();
                    }

                    if(idOrderByPrice.getText().equals("All") || idOrderByPrice.getText().equals("OrderByPrice")){
                        idOrderByPrice.setText("All");
                    }
                    if(idOrderByPrice.getText().equals("ASC")){
                        idOrderByPrice.setText("ASC");
                        produits= produits.stream().sorted((p1,p2)-> (int) (p1.getPrix()-p2.getPrix())).toList();

                    }
                    if(idOrderByPrice.getText().equals("DESC")){
                        idOrderByPrice.setText("DESC");
                        produits=  produits.stream().sorted((p1, p2)-> (int) (p2.getPrix()-p1.getPrix())).toList();
                       // Collections.reverse(produits);
                        }

                    if(idFilterByExchange.getText().equals("Both") || idFilterByExchange.getText().equals("FilterByExchange")){
                        idFilterByExchange.setText("Both");
                    }
                    if(idFilterByExchange.getText().equals("Exchangeable")){
                        idFilterByExchange.setText("Exchangeable");
                        produits= produits.stream().filter(e->e.getChoixEchange()==1).toList();

                    }
                    if(idFilterByExchange.getText().equals("Non exchangeable")){
                        idFilterByExchange.setText("Non exchangeable");
                        produits= produits.stream().filter(e->e.getChoixEchange()==0).toList();

                    }

                    if(idcurrency.getText().equals("DT") || idcurrency.getText().equals("currency")){
                        idcurrency.setText("DT");
                    }
                    if(idcurrency.getText().equals("Dollar $")){
                        idcurrency.setText("Dollar $");
                    }
                    if(idcurrency.getText().equals("Euro €")){
                        idcurrency.setText("Euro €");
                    }


                    if (!produits.isEmpty()) {
                        setChosenFruit(produits.get(0));
                        myListener = this::setChosenFruit;
                    }else {
                        setChosenFruit(null);
                    }
                    for (int i = 0; i < produits.size(); i++) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/ItemController.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        //                VBox box = fxmlLoader.load();

                        ItemController itemController = fxmlLoader.getController();
                        itemController.setData(produits.get(i), myListener, idcurrency.getText());

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
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void searchFilter() throws SQLException {
        List<Produit> list = gp.recuperer();
        if(idcurrency.getText().equals("DT") || idcurrency.getText().equals("currency")){
            idcurrency.setText("DT");
        }
        if(idcurrency.getText().equals("Dollar $")){
            idcurrency.setText("Dollar $");
        }
        if(idcurrency.getText().equals("Euro €")){
            idcurrency.setText("Euro €");
        }

        ObservableList<Produit> observableList = FXCollections.observableList(list);
        FilteredList<Produit> filteredList= new FilteredList<>(observableList, e->true);
        idSearchField.setOnKeyReleased(e->{

            int column=3, row=0;
            idSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super Produit >) cust->{
                    if(newValue==null){
                        return true;
                    }else if(cust.getTitreProduit().toLowerCase().contains(newValue.toLowerCase())){
                        return true;
                    }else if(cust.getVille().toLowerCase().contains(newValue.toLowerCase())){
                        return true;
                    }
                    return false;
                });
            });

            /*final SortedList<Customer> customers = new SortedList<>(filterData);
            customers.comparatorProperty().bind(tblCustomer.comparatorProperty());*/
            System.out.println(filteredList);
            List<Produit> filtered= new ArrayList<>();
            for (Produit service : filteredList) {
                filtered.add(service);
            }
            produits.clear();
            produits.addAll(filtered);
            System.out.println(filtered);
            grid.getChildren().clear();

            if (!produits.isEmpty()) {
                try {
                    setChosenFruit(produits.get(0));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                myListener = this::setChosenFruit;
            }else {
                try {
                    setChosenFruit(null);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemController.fxml"));
                AnchorPane anchorPane = null;
                try {
                    anchorPane = fxmlLoader.load();
                    //                VBox box = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(produits.get(i), myListener, idcurrency.getText());

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
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


                GridPane.setMargin(anchorPane, new Insets(10));
            }

            //ok let's check it
        });

    }

    public void filterByPrice(String price){
        try {
            produits=gp.recuperer();
            if (idCategories.getText().equals("All")|| idCategories.getText().equals("FilterByCategory")){
                idCategories.setText("All");
            }
            if(!idCategories.getText().equals("All") && !idCategories.getText().equals("FilterByCategory"))
            {
                produits = produits.stream().filter(e-> {
                    try {
                        return e.getIdCategorie()==gp.getCategoryByName(String.valueOf(idCategories.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }).toList();

            }
            if (idFilterByState1.getText().equals("All")|| idFilterByState1.getText().equals("FilterByState")){
                idFilterByState1.setText("All");
            }
            if (idFilterByState1.getText().equals("New")){
                idFilterByState1.setText("New");
                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("New")).toList();
            }
            if (idFilterByState1.getText().equals("Hardly_Used")){
                idFilterByState1.setText("Hardly_Used");

                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Hardly_Used")).toList();
            }
            if (idFilterByState1.getText().equals("Used")){
                idFilterByState1.setText("Used");
                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Used")).toList();
            }

            if (price.equals("All")){
                idFilterByPrice.setText("All");
            }
            if (price.equals("0-100 DT")){
                idFilterByPrice.setText("0-100 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>0 && produit1.getPrix()<= 100).toList();
            }
            if (price.equals("100-200 DT")){
                idFilterByPrice.setText("All");idFilterByPrice.setText("100-200 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>100 && produit1.getPrix()<= 200).toList();
            }
            if (price.equals("200-300 DT")){
                idFilterByPrice.setText("200-300 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>200 && produit1.getPrix()<= 300).toList();
            }
            if (price.equals("300-400 DT")){
                idFilterByPrice.setText("300-400 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>300 && produit1.getPrix()<= 400).toList();
            }
            if (price.equals(">400 DT")){
                idFilterByPrice.setText(">400 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>400).toList();
            }

            if(idOrderByPrice.getText().equals("All") || idOrderByPrice.getText().equals("OrderByPrice")){
                idOrderByPrice.setText("All");
            }
            if(idOrderByPrice.getText().equals("ASC")){
                idOrderByPrice.setText("ASC");
                produits= produits.stream().sorted((p1,p2)-> (int) (p1.getPrix()-p2.getPrix())).toList();

            }
            if(idOrderByPrice.getText().equals("DESC")){
                idOrderByPrice.setText("DESC");
                produits=  produits.stream().sorted((p1, p2)-> (int) (p2.getPrix()-p1.getPrix())).toList();
                //Collections.reverse(produits);
                }

            if(idFilterByExchange.getText().equals("Both") || idFilterByExchange.getText().equals("FilterByExchange")){
                idFilterByExchange.setText("Both");
            }
            if(idFilterByExchange.getText().equals("Exchangeable")){
                idFilterByExchange.setText("Exchangeable");
                produits= produits.stream().filter(e->e.getChoixEchange()==1).toList();

            }
            if(idFilterByExchange.getText().equals("Non exchangeable")){
                idFilterByExchange.setText("Non exchangeable");
                produits= produits.stream().filter(e->e.getChoixEchange()==0).toList();

            }

            if(idcurrency.getText().equals("DT") || idcurrency.getText().equals("currency")){
                idcurrency.setText("DT");
            }
            if(idcurrency.getText().equals("Dollar $")){
                idcurrency.setText("Dollar $");
            }
            if(idcurrency.getText().equals("Euro €")){
                idcurrency.setText("Euro €");
            }

            if (!produits.isEmpty()) {
                setChosenFruit(produits.get(0));
                myListener = this::setChosenFruit;
            }else {

                setChosenFruit(null);
            }
            int column=3, row=0;
            grid.getChildren().clear();

            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemController.fxml"));
                AnchorPane anchorPane = null;
                try {
                    anchorPane = fxmlLoader.load();
                    //                VBox box = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(produits.get(i), myListener, idcurrency.getText());

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
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void filterByState(String price){
        try {
            produits=gp.recuperer();
            System.out.println(produits);
            if (idCategories.getText().equals("All")|| idCategories.getText().equals("FilterByCategory")){
                idCategories.setText("All");
            }
            if(!idCategories.getText().equals("All") && !idCategories.getText().equals("FilterByCategory"))
            {
                idCategories.setText(idCategories.getText());
                produits = produits.stream().filter(e-> {
                    try {
                        return e.getIdCategorie()==gp.getCategoryByName(String.valueOf(idCategories.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }).toList();

            }if (idFilterByPrice.getText().equals("All")|| idFilterByPrice.getText().equals("FilterByPrice")){
                idFilterByPrice.setText("All");
            }
            if (idFilterByPrice.getText().equals("0-100 DT")){
                idFilterByPrice.setText("0-100 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>0 && produit1.getPrix()<= 100).toList();
            }
            if (idFilterByPrice.getText().equals("100-200 DT")){
                idFilterByPrice.setText("All");idFilterByPrice.setText("100-200 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>100 && produit1.getPrix()<= 200).toList();
            }
            if (idFilterByPrice.getText().equals("200-300 DT")){
                idFilterByPrice.setText("200-300 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>200 && produit1.getPrix()<= 300).toList();
            }
            if (idFilterByPrice.getText().equals("300-400 DT")){
                idFilterByPrice.setText("300-400 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>300 && produit1.getPrix()<= 400).toList();
            }
            if (idFilterByPrice.getText().equals(">400 DT")){
                idFilterByPrice.setText(">400 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>400).toList();
            }
            if (price.equals("All")){
                idFilterByState1.setText("All");
            }
            if (price.equals("New")){
                idFilterByState1.setText("New");
                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("New")).toList();
            }
            if (price.equals("Hardly_Used")){
                idFilterByState1.setText("Hardly_Used");

                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Hardly_Used")).toList();
            }
            if (price.equals("Used")){
                idFilterByState1.setText("Used");
                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Used")).toList();
            }

            if(idOrderByPrice.getText().equals("All") || idOrderByPrice.getText().equals("OrderByPrice")){
                idOrderByPrice.setText("All");
            }
            if(idOrderByPrice.getText().equals("ASC")){
                idOrderByPrice.setText("ASC");
                produits= produits.stream().sorted((p1,p2)-> (int) (p1.getPrix()-p2.getPrix())).toList();

            }
            if(idOrderByPrice.getText().equals("DESC")){
                idOrderByPrice.setText("DESC");
                produits=  produits.stream().sorted((p1, p2)-> (int) (p2.getPrix()-p1.getPrix())).toList();
               // Collections.reverse(produits);
                }

            if(idFilterByExchange.getText().equals("Both") || idFilterByExchange.getText().equals("FilterByExchange")){
                idFilterByExchange.setText("Both");
            }
            if(idFilterByExchange.getText().equals("Exchangeable")){
                idFilterByExchange.setText("Exchangeable");
                produits= produits.stream().filter(e->e.getChoixEchange()==1).toList();

            }
            if(idFilterByExchange.getText().equals("Non exchangeable")){
                idFilterByExchange.setText("Non exchangeable");
                produits= produits.stream().filter(e->e.getChoixEchange()==0).toList();

            }

            if(idcurrency.getText().equals("DT") || idcurrency.getText().equals("currency")){
                idcurrency.setText("DT");
            }
            if(idcurrency.getText().equals("Dollar $")){
                idcurrency.setText("Dollar $");
            }
            if(idcurrency.getText().equals("Euro €")){
                idcurrency.setText("Euro €");
            }

            if (!produits.isEmpty()) {
                setChosenFruit(produits.get(0));
                myListener = this::setChosenFruit;
            }else {

                setChosenFruit(null);
            }
            int column=3, row=0;
            grid.getChildren().clear();

            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemController.fxml"));
                AnchorPane anchorPane = null;
                try {
                    anchorPane = fxmlLoader.load();
                    //                VBox box = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(produits.get(i), myListener, idcurrency.getText());

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
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void OrdererByPrice(String orderprice){
        try {
            produits=gp.recuperer();
            if (idCategories.getText().equals("All")|| idCategories.getText().equals("FilterByCategory")){
                idCategories.setText("All");
            }
            if(!idCategories.getText().equals("All") && !idCategories.getText().equals("FilterByCategory"))
            {
                produits = produits.stream().filter(e-> {
                    try {
                        return e.getIdCategorie()==gp.getCategoryByName(String.valueOf(idCategories.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }).toList();


            }
            if (idFilterByState1.getText().equals("All")|| idFilterByState1.getText().equals("FilterByState")){
                idFilterByState1.setText("All");
            }

            if (idFilterByState1.getText().equals("New")){
                idFilterByState1.setText("New");
                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("New")).toList();
            }
            if (idFilterByState1.getText().equals("Hardly_Used")){
                idFilterByState1.setText("Hardly_Used");

                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Hardly_Used")).toList();
            }
            if (idFilterByState1.getText().equals("Used")){
                idFilterByState1.setText("Used");
                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Used")).toList();
            }
            if (idFilterByPrice.getText().equals("All")|| idFilterByPrice.getText().equals("FilterByPrice")){
                idFilterByPrice.setText("All");
            }

            if (idFilterByPrice.getText().equals("All")){
                idFilterByPrice.setText("All");
            }
            if ((idFilterByPrice.getText().equals("0-100 DT"))){
                idFilterByPrice.setText("0-100 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>0 && produit1.getPrix()<= 100).toList();
            }
            if ((idFilterByPrice.getText().equals("100-200 DT"))){
                idFilterByPrice.setText("All");idFilterByPrice.setText("100-200 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>100 && produit1.getPrix()<= 200).toList();
            }
            if ((idFilterByPrice.getText().equals("200-300 DT"))){
                idFilterByPrice.setText("200-300 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>200 && produit1.getPrix()<= 300).toList();
            }
            if ((idFilterByPrice.getText().equals("300-400 DT"))){
                idFilterByPrice.setText("300-400 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>300 && produit1.getPrix()<= 400).toList();
            }
            if ((idFilterByPrice.getText().equals(">400 DT"))){
                idFilterByPrice.setText(">400 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>400).toList();
            }

            if(orderprice.equals("All")){
                idOrderByPrice.setText("All");
            }
            if(orderprice.equals("ASC")){
                idOrderByPrice.setText("ASC");
                produits= produits.stream().sorted((p1,p2)-> (int) (p1.getPrix()-p2.getPrix())).toList();

            }
            if(orderprice.equals("DESC")){
                idOrderByPrice.setText("DESC");
                produits=  produits.stream().sorted((p1, p2)-> (int) (p2.getPrix()-p1.getPrix())).toList();
               // Collections.reverse(produits);
                }

            if(idFilterByExchange.getText().equals("Both") || idFilterByExchange.getText().equals("FilterByExchange")){
                idFilterByExchange.setText("Both");
            }
            if(idFilterByExchange.getText().equals("Exchangeable")){
                idFilterByExchange.setText("Exchangeable");
                produits= produits.stream().filter(e->e.getChoixEchange()==1).toList();

            }
            if(idFilterByExchange.getText().equals("Non exchangeable")){
                idFilterByExchange.setText("Non exchangeable");
                produits= produits.stream().filter(e->e.getChoixEchange()==0).toList();

            }


            if(idcurrency.getText().equals("DT") || idcurrency.getText().equals("currency")){
                idcurrency.setText("DT");
            }
            if(idcurrency.getText().equals("Dollar $")){
                idcurrency.setText("Dollar $");
            }
            if(idcurrency.getText().equals("Euro €")){
                idcurrency.setText("Euro €");
            }


            if (!produits.isEmpty()) {
                setChosenFruit(produits.get(0));
                myListener = this::setChosenFruit;
            }else {

                setChosenFruit(null);
            }
            int column=3, row=0;
            grid.getChildren().clear();

            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemController.fxml"));
                AnchorPane anchorPane = null;
                try {
                    anchorPane = fxmlLoader.load();
                    //                VBox box = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(produits.get(i), myListener, idcurrency.getText());

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
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public  void filterByExchangeChoice(String exchange){
        try {
            produits=gp.recuperer();
            if (idCategories.getText().equals("All")|| idCategories.getText().equals("FilterByCategory")){
                idCategories.setText("All");
            }
            if(!idCategories.getText().equals("All") && !idCategories.getText().equals("FilterByCategory"))
            {
                produits = produits.stream().filter(e-> {
                    try {
                        return e.getIdCategorie()==gp.getCategoryByName(String.valueOf(idCategories.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }).toList();


            }
            if (idFilterByState1.getText().equals("All")|| idFilterByState1.getText().equals("FilterByState")){
                idFilterByState1.setText("All");
            }

            if (idFilterByState1.getText().equals("New")){
                idFilterByState1.setText("New");
                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("New")).toList();
            }
            if (idFilterByState1.getText().equals("Hardly_Used")){
                idFilterByState1.setText("Hardly_Used");

                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Hardly_Used")).toList();
            }
            if (idFilterByState1.getText().equals("Used")){
                idFilterByState1.setText("Used");
                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Used")).toList();
            }
            if (idFilterByPrice.getText().equals("All")|| idFilterByPrice.getText().equals("FilterByPrice")){
                idFilterByPrice.setText("All");
            }

            if (idFilterByPrice.getText().equals("All")){
                idFilterByPrice.setText("All");
            }
            if ((idFilterByPrice.getText().equals("0-100 DT"))){
                idFilterByPrice.setText("0-100 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>0 && produit1.getPrix()<= 100).toList();
            }
            if ((idFilterByPrice.getText().equals("100-200 DT"))){
                idFilterByPrice.setText("All");idFilterByPrice.setText("100-200 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>100 && produit1.getPrix()<= 200).toList();
            }
            if ((idFilterByPrice.getText().equals("200-300 DT"))){
                idFilterByPrice.setText("200-300 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>200 && produit1.getPrix()<= 300).toList();
            }
            if ((idFilterByPrice.getText().equals("300-400 DT"))){
                idFilterByPrice.setText("300-400 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>300 && produit1.getPrix()<= 400).toList();
            }
            if ((idFilterByPrice.getText().equals(">400 DT"))){
                idFilterByPrice.setText(">400 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>400).toList();
            }

            if(idOrderByPrice.getText().equals("All") || idOrderByPrice.getText().equals("OrderByPrice")){
                idOrderByPrice.setText("All");
            }
            if(idOrderByPrice.getText().equals("ASC")){
                idOrderByPrice.setText("ASC");
                produits= produits.stream().sorted((p1,p2)-> (int) (p1.getPrix()-p2.getPrix())).toList();

            }
            if(idOrderByPrice.getText().equals("DESC")){
                idOrderByPrice.setText("DESC");
                produits=  produits.stream().sorted((p1, p2)-> (int) (p2.getPrix()-p1.getPrix())).toList();
               // Collections.reverse(produits);
            }

            if(exchange.equals("Both")){
                idFilterByExchange.setText("Both");
            }
            if(exchange.equals("Exchangeable")){
                idFilterByExchange.setText("Exchangeable");
                produits= produits.stream().filter(e->e.getChoixEchange()==1).toList();

            }
            if(exchange.equals("Non exchangeable")){
                idFilterByExchange.setText("Non exchangeable");
                produits= produits.stream().filter(e->e.getChoixEchange()==0).toList();

            }

            if(idcurrency.getText().equals("DT") || idcurrency.getText().equals("currency")){
                idcurrency.setText("DT");
            }
            if(idcurrency.getText().equals("Dollar $")){
                idcurrency.setText("Dollar $");
            }
            if(idcurrency.getText().equals("Euro €")){
                idcurrency.setText("Euro €");
            }




            if (!produits.isEmpty()) {
                setChosenFruit(produits.get(0));
                myListener = this::setChosenFruit;
            }else {

                setChosenFruit(null);
            }
            int column=3, row=0;
            grid.getChildren().clear();

            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemController.fxml"));
                AnchorPane anchorPane = null;
                try {
                    anchorPane = fxmlLoader.load();
                    //                VBox box = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(produits.get(i), myListener, idcurrency.getText());

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
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void changeCurrency(String currency) {

        try {
            produits=gp.recuperer();
            if (idCategories.getText().equals("All")|| idCategories.getText().equals("FilterByCategory")){
                idCategories.setText("All");
            }
            if(!idCategories.getText().equals("All") && !idCategories.getText().equals("FilterByCategory"))
            {
                produits = produits.stream().filter(e-> {
                    try {
                        return e.getIdCategorie()==gp.getCategoryByName(String.valueOf(idCategories.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }).toList();


            }
            if (idFilterByState1.getText().equals("All")|| idFilterByState1.getText().equals("FilterByState")){
                idFilterByState1.setText("All");
            }

            if (idFilterByState1.getText().equals("New")){
                idFilterByState1.setText("New");
                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("New")).toList();
            }
            if (idFilterByState1.getText().equals("Hardly_Used")){
                idFilterByState1.setText("Hardly_Used");

                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Hardly_Used")).toList();
            }
            if (idFilterByState1.getText().equals("Used")){
                idFilterByState1.setText("Used");
                produits= produits.stream().filter(produit1 -> produit1.getEtat().equals("Used")).toList();
            }
            if (idFilterByPrice.getText().equals("All")|| idFilterByPrice.getText().equals("FilterByPrice")){
                idFilterByPrice.setText("All");
            }

            if (idFilterByPrice.getText().equals("All")){
                idFilterByPrice.setText("All");
            }
            if ((idFilterByPrice.getText().equals("0-100 DT"))){
                idFilterByPrice.setText("0-100 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>0 && produit1.getPrix()<= 100).toList();
            }
            if ((idFilterByPrice.getText().equals("100-200 DT"))){
                idFilterByPrice.setText("All");idFilterByPrice.setText("100-200 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>100 && produit1.getPrix()<= 200).toList();
            }
            if ((idFilterByPrice.getText().equals("200-300 DT"))){
                idFilterByPrice.setText("200-300 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>200 && produit1.getPrix()<= 300).toList();
            }
            if ((idFilterByPrice.getText().equals("300-400 DT"))){
                idFilterByPrice.setText("300-400 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>300 && produit1.getPrix()<= 400).toList();
            }
            if ((idFilterByPrice.getText().equals(">400 DT"))){
                idFilterByPrice.setText(">400 DT");
                produits= produits.stream().filter(produit1 -> produit1.getPrix()>400).toList();
            }

            if(idOrderByPrice.equals("All")){
                idOrderByPrice.setText("All");
            }
            if( idOrderByPrice.equals("ASC")){
                idOrderByPrice.setText("ASC");
                produits= produits.stream().sorted((p1,p2)-> (int) (p1.getPrix()-p2.getPrix())).toList();

            }
            if( idOrderByPrice.equals("DESC")){
                idOrderByPrice.setText("DESC");
              produits=  produits.stream().sorted((p1, p2)-> (int) (p2.getPrix()-p1.getPrix())).toList();
              //Collections.reverse(produits);
            }

            if(idFilterByExchange.getText().equals("Both") || idFilterByExchange.getText().equals("FilterByExchange")){
                idFilterByExchange.setText("Both");
            }
            if(idFilterByExchange.getText().equals("Exchangeable")){
                idFilterByExchange.setText("Exchangeable");
                produits= produits.stream().filter(e->e.getChoixEchange()==1).toList();

            }
            if(idFilterByExchange.getText().equals("Non exchangeable")){
                idFilterByExchange.setText("Non exchangeable");
                produits= produits.stream().filter(e->e.getChoixEchange()==0).toList();

            }


            if(currency.equals("DT")){
                idcurrency.setText("DT");
            }
            if(currency.equals("Dollar $")){
                idcurrency.setText("Dollar $");
            }
            if(currency.equals("Euro €")){
                idcurrency.setText("Euro €");
            }






            if (!produits.isEmpty()) {
                setChosenFruit(produits.get(0));
                myListener = this::setChosenFruit;
            }else {

                setChosenFruit(null);
            }
            int column=3, row=0;
            grid.getChildren().clear();

            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemController.fxml"));
                AnchorPane anchorPane = null;
                try {
                    anchorPane = fxmlLoader.load();
                    //                VBox box = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(produits.get(i), myListener, idcurrency.getText());

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
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void exchange(ActionEvent actionEvent) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EchangeProduit.fxml"));
        Parent root = loader.load();
        EchangeProduitController echangeServiceController = loader.getController();
        echangeServiceController.setData(Integer.parseInt(idProduit.getText()));
        Stage stage = (Stage) idProduit.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void retour(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Services.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) idDelete.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
       // idDelete.getScene().setRoot(root);
    }
}
