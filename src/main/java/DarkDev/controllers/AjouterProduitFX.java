package DarkDev.controllers;

import DarkDev.Enums.State;
import DarkDev.models.Produit;
import DarkDev.services.GProduit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterProduitFX implements Initializable {

    public Text reqtitle;
    public Text reqimage;
    public Text reqdescription;
    public Text reqprice;
    public Text reqstate;
    public Text reqcategory;
    public Text reqcity;
    public Text reqexchange;
    GProduit gp = new GProduit();
    Produit p = new Produit();
    @FXML
    private TextField title;

    @FXML
    private TextField city;

    @FXML
    private TextField price;

    @FXML
    private CheckBox exchange;

    @FXML
    private ChoiceBox<String> state;

    @FXML
    private ChoiceBox<String> category;

    @FXML
    private TextField image;

    @FXML
    private TextArea description;


    private byte[] fichier;


 /*   @FXML
    private void parcourir(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            image.setText(file.getAbsolutePath());
            try {
                Path path = Paths.get(file.getAbsolutePath());
                fichier = Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}*/


    @FXML
    private void addproduct (ActionEvent event) throws SQLException {
        // Check if any of the required fields are empty
        try {
            int id = gp.getCategoryByName(category.getValue());
            if (id!=0){
              if (title.getText().isEmpty() || city.getText().isEmpty() || price.getText().isEmpty() || state.getValue() == null  || description.getText().isEmpty() || image.getText().isEmpty() ) {
                    // If any required field is empty, display an error message using an Alert

                      reqtitle.setVisible(title.getText().isEmpty());

                      reqcity.setVisible(city.getText().isEmpty());

                          if(price.getText().isEmpty()){
                              reqprice.setVisible(true);

                          } else if (!price.getText().matches("([0-9]*[.])?[0-9]+")) {
                              reqprice.setText("this field can accept only numeric value");
                              reqprice.setVisible(true);
                          }



                      reqstate.setVisible(state.getValue() == null);

                      reqdescription.setVisible(description.getText().isEmpty());

                      reqimage.setVisible(image.getText().isEmpty());

                      reqcategory.setVisible(false);


              } else {
                  reqtitle.setVisible(title.getText().isEmpty());

                  reqcity.setVisible(city.getText().isEmpty());
                  reqstate.setVisible(state.getValue() == null);

                  reqdescription.setVisible(description.getText().isEmpty());

                  reqimage.setVisible(image.getText().isEmpty());

                  reqcategory.setVisible(false);
                  if (!price.getText().matches("([0-9]*[.])?[0-9]+")) {
                      reqprice.setText("this field can accept only numeric value");
                      reqprice.setVisible(true);
                  }else {

                    // If all required fields are filled, proceed with adding the item
                    // Assuming 'gp' is defined elsewhere

                    // Set the attributes of the 'gp' object based on the input fields
                    p.setTitreProduit(title.getText());
                    p.setVille(city.getText());
                    p.setPrix(Double.parseDouble(price.getText()));
                    p.setEtat((String) state.getValue());
                    p.setIdCategorie(id);
                    p.setChoixEchange(exchange.isSelected());
                    p.setDescriptionProduit(description.getText());
                    p.setPhoto(image.getText());
                    p.setIdUtilisateur(2);

                    // Call a method to add the item to your data source (assuming 'addProduit' or similar)
                    try {
                        gp.ajouter(p);
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduitsFX.fxml"));
                            Parent root = loader.load();
                            title.getScene().setRoot(root);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    // Display a success message using an Alert
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("OK");
                    alert.setHeaderText("Ajout effectue");
                    alert.setContentText("Item has been successfully added.");
                    alert.showAndWait();
                }}
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Category error");
                alert.setHeaderText("Non existant category");
                alert.setContentText("Click Cancel to exit.");
                alert.showAndWait();

                reqtitle.setVisible(title.getText().isEmpty());

                reqcity.setVisible(city.getText().isEmpty());

                if(price.getText().isEmpty()){
                    reqprice.setVisible(true);

                } else if (!price.getText().matches("([0-9]*[.])?[0-9]+")) {
                    reqprice.setText("this field can accept only numeric value");
                    reqprice.setVisible(true);
                }

                reqstate.setVisible(state.getValue() == null);

                reqdescription.setVisible(description.getText().isEmpty());

                reqimage.setVisible(image.getText().isEmpty());

                reqcategory.setVisible(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Method to add the product to your data source



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
           load();
    }

    private void load()  {
        try {
            List<String> categories= gp.ListeCategories();
            for (String cat: categories) {
                category.getItems().add (cat);

            }
            for (State s : State.values()){
                state.getItems().add(s.name());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void returnback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduitsFX.fxml"));
            Parent root = loader.load();
            title.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }
}
