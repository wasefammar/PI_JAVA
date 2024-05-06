package controllers.Evenement;

import services.ServicesEvenement.EventService;
import models.Evenement.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class ShowEventP {

    EventService es= new EventService();

    @FXML
    private Text Descriptionlbl;

    @FXML
    private Label EndigDatelbl;

    @FXML
    private BorderPane EventDetail;

    @FXML
    private Label EventNamelbl;

    @FXML
    private Label ProduitIDlbl;

    @FXML
    private Label Startdatelbl;

    @FXML
    private ImageView idImage;

    private int eventID;

    @FXML
    void Particip(ActionEvent event) {

    }

    @FXML
    void back(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllEvent.fxml"));
            Parent root = loader.load();
            EventNamelbl.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setFields(Event event) throws SQLException {
        EventNamelbl.setText(event.getTitre_evenement());
        Descriptionlbl.setText(event.getDescription_evenement());
        Startdatelbl.setText(event.getDate_debut().toString());
        EndigDatelbl.setText(event.getDate_fin().toString());
        ProduitIDlbl.setText(String.valueOf(event.getProduit_id()));
        eventID = event.getId();
    }

}