package SwapNShare.sami.controllers;

import SwapNShare.sami.models.Event;
import SwapNShare.sami.services.EventService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class EventD {

    @FXML
    private Label E_Datelbl;

    @FXML
    private Label E_Namelbl;

    @FXML
    private Label E_Timelbl;

    @FXML
    private Pane EventSc;

    private int eventID;

    EventService es =new EventService();

    public void setData(Event event){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String formattedDate = event.getDate_debut().format(dateFormatter);
        String formattedTime = event.getDate_debut().format(timeFormatter);

        E_Datelbl.setText(formattedDate);
        E_Timelbl.setText(formattedTime);

        E_Namelbl.setText(event.getTitre_evenement());

        eventID = event.getId();
    }


    @FXML
    void switchToEvent(MouseEvent mouseEvent){
        try {
            // Load the sign-up window FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowEvent.fxml"));
            Parent root = loader.load();

            ShowEvent showEvent = loader.getController();
            showEvent.setFields(es.getOne(eventID));

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("");
            stage.setScene(new Scene(root));

            // Get the reference to the current window
            Stage currentStage = (Stage) EventSc.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException e) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
