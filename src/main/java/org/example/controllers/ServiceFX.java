package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Service;
import org.example.services.GestionService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ServiceFX implements Initializable {

    public TextField idTitre;
    public Label IdPhoto;
    public TextArea IdDescription;
    public CheckBox IdExchange;
    public TextField IdAddress;
    public Button IdAdd;
    public ComboBox<String> idCategory;

    private final GestionService gs = new GestionService();
    public Button idBack;
    public Text idRequiredTitle;
    public Text idRequiredDesc;
    public Text idRequiredPhoto;
    public Text idRequiredCategory;
    public Text idRequiredAddress;
    public ImageView idUpload;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        load();
    }
    private void load()  {
        try {
            List<String> categories= gs.ListeCategories();
            for (String cat: categories) {
                 idCategory.getItems().add(cat);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addService(ActionEvent event)  {
        String titreService = idTitre.getText();
        String descriptionService = IdDescription.getText();
        String photo = IdPhoto.getText();
        String address = IdAddress.getText();
        boolean exchange = IdExchange.isSelected();
        String category = idCategory.getValue();
        try{
            int id = gs.getCategoryByName(category);
            if(id!=0){
              if (titreService.isEmpty() || descriptionService.isEmpty() || photo.isEmpty() || address.isEmpty() || photo.equals("Upload a photo")){
                  idRequiredTitle.setVisible(titreService.isEmpty());
                  if (titreService.isEmpty()) {
                      idTitre.setStyle("-fx-border-color: red;");
                  } else {
                      idTitre.setStyle("-fx-border-color: green;");
                  }

                  idRequiredDesc.setVisible(descriptionService.isEmpty());
                  if (descriptionService.isEmpty()) {
                      IdDescription.setStyle("-fx-border-color: red;");
                  } else {
                      IdDescription.setStyle("-fx-border-color: green;");
                  }

                  idRequiredPhoto.setVisible(photo.isEmpty() || photo.equals("Upload a photo"));

                  idRequiredAddress.setVisible(address.isEmpty());
                  if (address.isEmpty()) {
                      IdAddress.setStyle("-fx-border-color: red;");
                  } else {
                      IdAddress.setStyle("-fx-border-color: green;");
                  }

                  idRequiredCategory.setVisible(false);

              }
              else {
                  Service service = new Service(id, 3, titreService, descriptionService, photo, address, exchange ? 1 : 0,0);
                  gs.ajouter(service);
                  System.out.println("Service added successfully");
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/Services.fxml"));
                  Parent root = loader.load();
                  IdDescription.getScene().setRoot(root);
              }


            }
            else{

                idRequiredTitle.setVisible(titreService.isEmpty());
                if (titreService.isEmpty()) {
                    idTitre.setStyle("-fx-border-color: red;");
                } else {
                    idTitre.setStyle("-fx-border-color: green;");
                }

                idRequiredDesc.setVisible(descriptionService.isEmpty());
                if (descriptionService.isEmpty()) {
                    IdDescription.setStyle("-fx-border-color: red;");
                } else {
                    IdDescription.setStyle("-fx-border-color: green;");
                }
                idRequiredPhoto.setVisible(photo.isEmpty() || photo.equals("Upload a photo"));

                idRequiredAddress.setVisible(address.isEmpty());
                if (address.isEmpty()) {
                    IdAddress.setStyle("-fx-border-color: red;");
                } else {
                    IdAddress.setStyle("-fx-border-color: green;");
                }
                idRequiredCategory.setText("Enter a valid category");
                idRequiredCategory.setVisible(true);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }


    public void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Services.fxml"));
            Parent root = loader.load();
            idTitre.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public void parcourir(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File file= fileChooser.showOpenDialog(new Stage());
        if(file != null){
            IdPhoto.setText(file.getAbsolutePath().toString());
            File imageFile= new File(IdPhoto.getText());
            idUpload.setImage(new Image(imageFile.toURI().toString()));
        }
    }

    public void retour(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Services.fxml"));
            Parent root = loader.load();
            idTitre.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
