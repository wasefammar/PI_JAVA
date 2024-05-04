package org.example.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

import org.example.models.Categorie;
import org.example.services.GestionService;
import org.example.services.ServiceCategorie;
import org.example.services.ServiceCommentaire;

public class CategorieController {
    public Button AddBtn;
    public TextField NomCat;
    @FXML
    private ChoiceBox<String> choicefx;

    private ServiceCategorie serviceCategorie = new ServiceCategorie();


    @FXML
    void initialize() {
    }

    @FXML
    public void AddCategorie(ActionEvent event) {
        String nomCategorie = NomCat.getText();
        String type = choicefx.getValue();

        if (nomCategorie != null && !nomCategorie.isEmpty() && type != null) {
            Categorie categorie = new Categorie(nomCategorie, type);
            try {
                serviceCategorie.ajouter(categorie);

            } catch (SQLException e) {
                e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}