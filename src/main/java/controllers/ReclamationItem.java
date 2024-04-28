package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Reclamation;
import services.ServiceReclamation;

import java.io.IOException;

public class ReclamationItem {
    public Label idUsername;
    public Button idUpdate;
    public Button idDelete;

    public Label reasonTitre;
    public Label idUrgence;
    public Label idStatus;
    public Text idDescription;
    public Label idDate;
    public Label idComplaint;
    public Button idRespond;

    ServiceReclamation rs = new ServiceReclamation();
    public void setData(Reclamation reclamation){
        reasonTitre.setText(reclamation.getTitre_r());
        idUrgence.setText(reclamation.getUrgence());
        idDate.setText(reclamation.getDate().toString());
        idStatus.setText(reclamation.getStatus());
        idDescription.setText(reclamation.getDescription_r());
        idComplaint.setText(""+reclamation.getId());

    }

    public void updateComment(ActionEvent actionEvent) {
    }

    public void deleteComment(ActionEvent actionEvent) {
    }

    public void Delete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this Complaint?");
        alert.setContentText("This action cannot be undone.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {

                    rs.supprimer_Reclamation(Integer.parseInt(idComplaint.getText()));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherReclamation.fxml"));
                    Parent root = loader.load();
                    idDelete.getScene().setRoot(root);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public void respond(ActionEvent actionEvent) {

        try {
            Reclamation reclamation = rs.getReclamationById(Integer.parseInt(idComplaint.getText()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Response.fxml"));
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/ReclamationItem.fxml"));
            VBox box = loader2.load();
            BorderPane borderPane = loader.load();
            ReclamationItem  reclamationItem= loader2.getController();
            reclamationItem.setData(reclamation);
            System.out.println("oui2");
            VBox box1= (VBox) borderPane.getChildren().get(1);
            VBox box2= (VBox) box1.getChildren().get(0);
            box2.getChildren().add(box);
            idDelete.getScene().setRoot(borderPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
