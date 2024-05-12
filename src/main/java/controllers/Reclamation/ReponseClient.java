package controllers.Reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Reclamation.Reponse;

import java.io.IOException;

public class ReponseClient {

    public Button idBack;

    @FXML
    private Text id_reponse_descrip;


    public void mrigl(ActionEvent actionEvent) throws IOException {



    }

    public void setData(Reponse reponse){
        id_reponse_descrip.setText(reponse.getDescription_id());

    }

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/afficherReclamation.fxml"));
        Stage stage = (Stage) id_reponse_descrip.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }
}
