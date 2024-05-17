package controllers.Produit;


import Enums.State;
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

public class UpdateProduitController implements Initializable {

    public Label idID;
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
    private Label reqimage;

    @FXML
    private ImageView image;

    @FXML
    private Label idPhoto;

    File imageFile= null;


    @FXML
    void backtoproductlist(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) reqcategory.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        load();
    }

    private void load() {
        try {
            List<String> categories = gp.ListeCategories();
            for (String cat : categories) {
                category.getItems().add(cat);

            }
            for (State s : State.values()) {
                state.getItems().add(s.name());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void setFields(Produit produit) throws SQLException {
        this.title.setText(produit.getTitreProduit());
        this.idPhoto.setText(produit.getPhoto());
        this.description.setText(produit.getDescriptionProduit());
        this.city.setText(produit.getVille());
        this.exchange.setSelected(produit.getChoixEchange()!=0);
        this.category.setValue(gp.getCategoryById(produit.getIdCategorie()));
        this.state.setValue(produit.getEtat());
        this.price.setText(String.valueOf(produit.getPrix()));
        this.idID.setText("" + produit.getId());
        File imageFile= new File("F:\\ESPRIT\\pidev-main (2)\\pidev-main\\public\\uploads\\produits\\"+produit.getPhoto());;
        image.setImage(new Image(imageFile.toURI().toString()));

    }


    public void updateproduct(ActionEvent actionEvent) {
        String category = this.category.getValue();
        String state = this.state.getValue();
        int exchange = this.exchange.isSelected() ? 1 : 0;
        String title = this.title.getText();
        String description = this.description.getText();
        String city = this.city.getText();
        String image = this.idPhoto.getText();
        try {
            int id = gp.getCategoryByName(category);
            if (id != 0) {
                if (title.isEmpty() || description.isEmpty() || image.isEmpty() || city.isEmpty() || image.equals("Upload a photo")
                        || this.price.getText().isEmpty() || !this.price.getText().matches("([0-9]*[.])?[0-9]+") || state == null) {
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
                    if (this.price.getText().isEmpty()) {
                        this.price.setStyle("-fx-border-color: red;");

                    } else if (!this.price.getText().matches("([0-9]*[.])?[0-9]+")) {
                        this.price.setStyle("-fx-border-color: red;");
                        reqprice.setText("Enter a valid price!");

                    } else {
                        this.price.setStyle("-fx-border-color: green;");

                    }

                    reqstate.setVisible(state == null);

                    reqcategory.setVisible(false);

                } else {
                    gp.recuperer();
                    double price = Double.parseDouble(this.price.getText());
                    int idP= Integer.parseInt(this.idID.getText());
                    Produit produit = new Produit(idP,id, 2, exchange, price, title,
                            description, image, city, state);

                    Path sourcePath = Paths.get(imageFile.toURI());
                    Path destinationPath= Paths.get("F:\\ESPRIT\\pidev-main (2)\\pidev-main\\public\\uploads\\produits\\"+image);
                    Files.copy(sourcePath, destinationPath);

                    gp.modifier(produit);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) reqcategory.getScene().getWindow(); // Obtenir la scène actuelle
                    stage.setScene(new Scene(root));
                    stage.setTitle("Page ");
                    stage.centerOnScreen();
                    stage.show();
                }


            } else {

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
                if (this.price.getText().isEmpty()) {
                    this.price.setStyle("-fx-border-color: red;");

                } else if (!this.price.getText().matches("([0-9]*[.])?[0-9]+")) {
                    this.price.setStyle("-fx-border-color: red;");
                    reqprice.setText("Enter a valid price");

                } else {
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

    public void parcourir(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File file= fileChooser.showOpenDialog(new Stage());
        if(file != null){
            idPhoto.setText(file.getName());
            imageFile= new File(file.getAbsolutePath());;
            image.setImage(new Image(imageFile.toURI().toString()));
        }
    }
}