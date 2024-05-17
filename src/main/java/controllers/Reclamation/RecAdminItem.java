package controllers.Reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Reclamation.Reclamation;
import models.User.Personne;
import services.GestionServices.GestionService;
import services.ServicesReclamation.ServiceReclamation;


import java.io.IOException;

public class RecAdminItem {

    public Label idUsername;
    public Button idUpdate;
    public Button idRespond;
    public Button idDelete;
    public Label idComplaint;
    public Label idEmail;
    public Label idPhone;
    public Label reasonTitre;
    public Label idUrgence;
    public Label idStatus;
    public Text idDescription;
    public Label idDate;

    ServiceReclamation rs = new ServiceReclamation();
    GestionService gs = new GestionService();
   /* public void setData(Reclamation reclamation){
        reasonTitre.setText(reclamation.getTitre_r());
        idUrgence.setText(reclamation.getUrgence());
        idDate.setText(reclamation.getDate().toString());
        idStatus.setText(reclamation.getStatus());
        idDescription.setText(reclamation.getDescription_r());

    }
*/


    public void setData(Reclamation reclamation){
        Personne personne = rs.getUserById(reclamation.getUtilisateur_id());
        reasonTitre.setText(reclamation.getTitre_r());
        idUrgence.setText(reclamation.getUrgence());
        idDate.setText(reclamation.getDate().toString());
        idStatus.setText(reclamation.getStatus());
        idDescription.setText(reclamation.getDescription_r());
        idEmail.setText(personne.getEmail());
        idUsername.setText(personne.getNom()+" "+personne.getPrenom());
        idPhone.setText(""+personne.getTelephone());
        idComplaint.setText(""+reclamation.getId());
        idRespond.setDisable(reclamation.getStatus().equals("Treated"));


    }
    public void setData2(Personne personne){
        idEmail.setText(personne.getEmail());
        idPhone.setText(personne.getTelephone());


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


                    rs.supprimer_Reclamation(7);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
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
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/RecAdminItem.fxml"));
            VBox box = loader2.load();
            BorderPane borderPane = loader.load();
            Response respone= loader.getController();
            respone.setData(reclamation);
            RecAdminItem  reclamationItem= loader2.getController();
            reclamationItem.setData(reclamation);
            System.out.println("oui2");
            VBox box1= (VBox) borderPane.getCenter();
            VBox box2= (VBox) box1.getChildren().get(0);
            box2.getChildren().add(box);
            idDelete.getScene().setRoot(borderPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
