package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import models.Commentaire;
import models.Service;
import services.GestionService;
import services.ServiceCommentaire;

import java.io.IOException;
import java.sql.SQLException;

public class SingleComment {


    GestionService gs= new GestionService();
    ServiceCommentaire sc= new ServiceCommentaire();
    public Label idServiceID;
    public Label idCommentID;
    public Label idUsername;
    public Button idUpdate;
    public Button idDelete;
    public Text idComment;

    public  void setData(Commentaire commentaire) throws SQLException {
        idUsername.setText(gs.getUserById(commentaire.getIdUtilisateur()));
        idComment.setText(commentaire.getDescription());
        idCommentID.setText(""+commentaire.getId());
        idServiceID.setText(""+commentaire.getIdService());
    }

    public void updateComment(ActionEvent event) {
        try {
            Service service = gs.getServiceById(Integer.parseInt(idServiceID.getText()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowService.fxml"));
            Parent root = loader.load();
            ShowService showService = loader.getController();
            showService.setFields(service);
            showService.idCommentDescription.setText(idComment.getText());
            idCommentID.getScene().setRoot(root);

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteComment(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this comment?");
        alert.setContentText("This action cannot be undone.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    sc.supprimer(Integer.parseInt(idCommentID.getText()));
                    Service service = gs.getServiceById(Integer.parseInt(idServiceID.getText()));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowService.fxml"));
                    Parent root = loader.load();
                    ShowService showService = loader.getController();
                    showService.setFields(service);
                    idCommentID.getScene().setRoot(root);

                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
