package SwapNShare.sami.controllers;

import SwapNShare.sami.models.Event;
import SwapNShare.sami.models.SceneSwitch;
import SwapNShare.sami.services.EventService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;


import java.io.IOException;
import java.sql.SQLException;

public class ShowEvent {

    EventService es= new EventService();

    @FXML
    private BorderPane EventDetail;

    @FXML
    private Text Descriptionlbl;

    @FXML
    private Label EndigDatelbl;

    @FXML
    private Label EventNamelbl;

    @FXML
    private Label ProduitIDlbl;

    @FXML
    private Label Startdatelbl;

    private int eventID;


    @FXML
    void switchToBack(ActionEvent event) throws IOException {
        new SceneSwitch(EventDetail, "/AllEvent.fxml");
    }

    @FXML
    void switchToUpdate(ActionEvent event){
        try{

            Event eventt = es.getOne(eventID);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvent.fxml"));
            Parent root = loader.load();
            UpdateEvent updateEvent = loader.getController();
            updateEvent.setFields(eventt);
            EventNamelbl.getScene().setRoot(root);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    void deleteEvent(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this event?");
        alert.setContentText("This action cannot be undone.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    es.delete(es.getOne(eventID));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllEvent.fxml"));
                    Parent root = loader.load();
                    EventNamelbl.getScene().setRoot(root);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setFields(Event event) throws SQLException {
        EventNamelbl.setText(event.getTitre_evenement());
        Descriptionlbl.setText(event.getDescription_evenement());
        Startdatelbl.setText(event.getDate_debut().toString());
        EndigDatelbl.setText(event.getDate_fin().toString());
        ProduitIDlbl.setText(String.valueOf(event.getProduit_id()));
        eventID = event.getId();
    }

    public void back(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllEvent.fxml"));
            Parent root = loader.load();
            EventNamelbl.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
