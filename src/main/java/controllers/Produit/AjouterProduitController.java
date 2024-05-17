package controllers.Produit;


import Enums.State;
import controllers.User.SessionUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Produit.Produit;
import services.GestionServices.GestionService;
import services.ServicesProduit.GProduit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterProduitController implements Initializable {

    public ImageView idImage;
    GProduit gp = new GProduit();
    @FXML
    private TextField title;

    @FXML
    private Label reqtitle;

    @FXML
    private Label reqdescription;

    @FXML
    private TextArea description;

    @FXML
    private TextField price;

    @FXML
    private Label reqprice;

    @FXML
    private TextField city;

    @FXML
    private Label reqcity;

    @FXML
    private Label reqcategory;

    @FXML
    private ChoiceBox<String> category;

    @FXML
    private Label reqstate;

    @FXML
    private ChoiceBox<String> state;

    @FXML
    private Label reqexchange;

    @FXML
    private CheckBox exchange;

    @FXML
    private Circle image;

    @FXML
    private Label reqimage;


    @FXML
    private Label idPhoto;

    File imageFile= null;

    GestionService gs= new GestionService();

    public void addproduct(ActionEvent actionEvent) {
        String category = this.category.getValue();
        String state = this.state.getValue();
        int exchange = this.exchange.isSelected() ? 1 : 0;
        String title = this.title.getText();
        String description = this.description.getText();
        String city = this.city.getText();
        String image = this.idPhoto.getText();
        try{
            int id = gp.getCategoryByName(category);
            if(id!=0){
                if (title.isEmpty() || description.isEmpty() || image.isEmpty() || city.isEmpty() || image.equals("Upload a photo")
                    || this.price.getText().isEmpty() || !this.price.getText().matches("([0-9]*[.])?[0-9]+") || state == null){
                    reqtitle.setVisible(title.isEmpty());
                    if (title.isEmpty()) {
                        this.title.setStyle("-fx-border-color: red;");
                    } else {
                        this.title.setStyle("-fx-border-color: green;");
                    }

                    reqdescription.setVisible(description.isEmpty());
                    if (description.isEmpty()) {
                        this.description.setStyle("-fx-border-color: red;");
                    } else {
                        this.description.setStyle("-fx-border-color: green;");
                    }

                    reqimage.setVisible(image.isEmpty() || image.equals("Upload a photo"));

                    reqcity.setVisible(city.isEmpty());
                    if (city.isEmpty()) {
                        this.city.setStyle("-fx-border-color: red;");
                    } else {
                        this.city.setStyle("-fx-border-color: green;");
                    }

                    reqprice.setVisible(this.price.getText().isEmpty() || !this.price.getText().matches("([0-9]*[.])?[0-9]+"));
                    if (this.price.getText().isEmpty()){
                        this.price.setStyle("-fx-border-color: red;");

                    }else if(!this.price.getText().matches("([0-9]*[.])?[0-9]+")){
                        this.price.setStyle("-fx-border-color: red;");
                        reqprice.setText("Enter a valid price!");

                    }else {
                        this.price.setStyle("-fx-border-color: green;");

                    }

                    reqstate.setVisible(state == null);

                    reqcategory.setVisible(false);

                }
                else {
                    List<Produit> produits = gp.recuperer();
                    boolean test= false;
                    double price = Double.parseDouble(this.price.getText());
                    int idUtilisateur= gs.getIdUtilisateurByEmail(SessionUser.getUser().getAdresseEmail()).getId() ;
                    Produit produit = new Produit( id, idUtilisateur, exchange , price, title,
                            description, this.idPhoto.getText(),  city, state);
                    for (Produit produit1: produits){
                        if(produit.getTitreProduit().equals(produit1.getTitreProduit())
                        && produit.getDescriptionProduit().equals(produit1.getDescriptionProduit())
                        && produit.getPrix()==produit1.getPrix()
                        && produit.getPhoto().equals(produit1.getPhoto())
                        && produit.getVille().equals(produit1.getVille())
                        && produit.getEtat().equals(produit1.getEtat())
                        && produit.getIdCategorie()==produit1.getIdCategorie()
                        && produit.getChoixEchange()==produit1.getChoixEchange()){
                            test=true;
                            break;
                        }
                    }
                    System.out.println(produit);
                    if(test==false){
                        Path sourcePath = Paths.get(imageFile.toURI());
                        Path destinationPath= Paths.get("F:\\ESPRIT\\pidev-main (2)\\pidev-main\\public\\uploads\\produits\\"+image);
                        Files.copy(sourcePath, destinationPath);
                        gp.ajouter(produit);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) reqcategory.getScene().getWindow(); // Obtenir la scène actuelle
                        stage.setScene(new Scene(root));
                        stage.setTitle("Page ");
                        stage.centerOnScreen();
                        stage.show();
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Product already exist.");
                        alert.setContentText("this product already exist!");

                        alert.showAndWait();
                    }

                }


            }
            else{

                reqtitle.setVisible(title.isEmpty());
                if (title.isEmpty()) {
                    this.title.setStyle("-fx-border-color: red;");
                } else {
                    this.title.setStyle("-fx-border-color: green;");
                }

                reqdescription.setVisible(description.isEmpty());
                if (description.isEmpty()) {
                    this.description.setStyle("-fx-border-color: red;");
                } else {
                    this.description.setStyle("-fx-border-color: green;");
                }

                reqimage.setVisible(image.isEmpty() || image.equals("Upload a photo"));

                reqcity.setVisible(city.isEmpty());
                if (city.isEmpty()) {
                    this.city.setStyle("-fx-border-color: red;");
                } else {
                    this.city.setStyle("-fx-border-color: green;");
                }

                reqprice.setVisible(this.price.getText().isEmpty() || !this.price.getText().matches("([0-9]*[.])?[0-9]+"));
                if (this.price.getText().isEmpty()){
                    this.price.setStyle("-fx-border-color: red;");

                }else if(!this.price.getText().matches("([0-9]*[.])?[0-9]+")){
                    this.price.setStyle("-fx-border-color: red;");
                    reqprice.setText("Enter a valid price");

                }else {
                    this.price.setStyle("-fx-border-color: green;");

                }

                reqstate.setVisible(state == null);

                reqcategory.setVisible(true);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }



    }

    @FXML
    void backtoproductlist(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) title.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public void parcourir(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File file= fileChooser.showOpenDialog(new Stage());
        if(file != null){
            idPhoto.setText(file.getName());
            System.out.println(idPhoto.getText());
            imageFile= new File(file.getAbsolutePath());
            idImage.setImage(new Image(imageFile.toURI().toString()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<String> categories= gp.ListeCategories();
            for (String cat: categories) {
                category.getItems().add(cat);

            }
            for (State s: State.values()){
                state.getItems().add(s.name());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
