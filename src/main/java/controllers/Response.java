package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import models.Reclamation;
import models.Reponse;
import services.BadWordsFilter;
import services.ServiceReponse;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class Response {

    String[] badWordsArray = {"5ra", "le", "non","fuck","putin","yezi","edara","esprit","asshole"};

    BadWordsFilter filter=new BadWordsFilter(badWordsArray);

    ServiceReponse sr= new ServiceReponse();
    public Button btnSend;
    public Button btnCancel;
    public Label idRequiredResponse;
    public TextArea description_id;

    public void cancelRec(ActionEvent actionEvent) {
    }

    public void ajouter_reclamation1(ActionEvent actionEvent) {
        String descriptionService = description_id.getText();
        Date currentDate = Date.valueOf(LocalDate.now());

        try{


            if ( descriptionService.isEmpty()){


                idRequiredResponse.setVisible(true);

            }
            else {
                if ( filter.hasBadWord(description_id.getText()))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ALERT !!");
                    alert.setContentText("BAD WORD");
                    alert.showAndWait();

                }else {
                    Reponse response = new Reponse(8,"bad service",descriptionService,currentDate);
                    sr.ajouter_Reponse(response);
                    System.out.println("Complaint added successfully");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
                    Parent root = loader.load();
                    description_id.getScene().setRoot(root);
                }

            }


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
