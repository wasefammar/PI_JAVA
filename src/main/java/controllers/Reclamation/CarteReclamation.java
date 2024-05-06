package controllers.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

public class CarteReclamation {

    @FXML
    private Label titre_rLabel;


    @FXML
    private Label description_rLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label urgenceLabel;
    @FXML
    private Rating rating;
    @FXML
    private Label avgLabel;

    public void setTitre_rLabel(String nom) {
        titre_rLabel.setText(nom);
    }

    public void setDescription_rLabel(String adresse) {
        description_rLabel.setText(adresse);
    }

    public void setDateLabel(String description) {
        dateLabel.setText(description);
    }

    public void setStatusLabel (String description) {
        statusLabel.setText(description);
    }

    public void setUrgenceLabel(String description) {
        urgenceLabel.setText(description);
    }

    public void setAvg(String avg) {avgLabel.setText(avg);}
    @FXML
    public void handleButtonAction(ActionEvent event) {
    }

}