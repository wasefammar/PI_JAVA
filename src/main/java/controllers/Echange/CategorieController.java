package controllers.Echange;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

import models.Echange.Categorie;
import services.EchangeServices.ServiceCategorie;

public class CategorieController {
    @FXML
    private TextField NomCat;
    @FXML
    private ChoiceBox<String> choicefx;
    private ServiceCategorie serviceCategorie = new ServiceCategorie();

    @FXML
    public void initialize() {

    }

    public void AddCategorie() {
        String nomCategorie = NomCat.getText();
        String type = choicefx.getValue();

        if (nomCategorie != null && !nomCategorie.isEmpty() && type != null) {
            Categorie categorie = new Categorie(nomCategorie, type);
            try {
                serviceCategorie.ajouter(categorie);

            } catch (SQLException e) {
                // Replace with a more robust logging mechanism
                System.out.println("An error occurred while adding a category: " + e.getMessage());
            }
        }
    }

    @FXML
    private void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CategorieFX.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Replace with a more robust logging mechanism
            System.out.println("An error occurred while loading the CategorieFX view: " + e.getMessage());
        }
    }
}