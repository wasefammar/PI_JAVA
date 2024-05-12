package controllers.Reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Reclamation.Reclamation;
import models.Reclamation.Reponse;
import services.GestionServices.GestionService;
import services.ServicesReclamation.ServiceReclamation;
import services.ServicesReclamation.ServiceReponse;

import java.io.IOException;
import java.sql.SQLException;

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
    GestionService gs = new GestionService();
    ServiceReponse rp = new ServiceReponse();
    public void setData(Reclamation reclamation) throws SQLException {
        idUsername.setText(gs.getUserById(reclamation.getUtilisateur_id()));
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
            System.out.println(reclamation);
            Reponse reponse = rp.getRepByIdRec(reclamation.getId());
            if(reponse == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ALERT !!");
                alert.setContentText("There is no response ");
                alert.showAndWait();
            }else {
                System.out.println(reponse);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reponseClient.fxml"));
                AnchorPane hihi = loader.load();
                ReponseClient reponseClient = loader.getController();
                reponseClient.setData(reponse);


                System.out.println("oui2");

                idDelete.getScene().setRoot(hihi);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
