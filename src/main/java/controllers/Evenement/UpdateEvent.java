package controllers.Evenement;

import models.Evenement.Event;
import services.ServicesEvenement.EventService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UpdateEvent {

    @FXML
    private TextArea E_DescripTF;

    @FXML
    private DatePicker E_EndDP;

    @FXML
    private TextField E_EndTF;

    @FXML
    private TextField E_ProductIdTF;

    @FXML
    private DatePicker E_StartDP;

    @FXML
    private TextField E_StartTF;

    @FXML
    private TextField E_TitleTF;

    @FXML
    private Text idRequiredDate;

    @FXML
    private Text idRequiredDate1;

    @FXML
    private Text idRequiredDesc;

    @FXML
    private Text idRequiredProduct;

    @FXML
    private Text idRequiredTitle;

    private final EventService es = new EventService();
    private int eventID;

    @FXML
    private BorderPane EventSc;


    @FXML
    void UpdateE(ActionEvent event) {
        LocalDate dateS = E_StartDP.getValue();
        String timeSs = E_StartTF.getText();
        LocalTime timeS = LocalTime.parse(timeSs);
        LocalDateTime startDate = dateS.atTime(timeS);

        LocalDate dateE = E_EndDP.getValue();
        String timeEs = E_EndTF.getText();
        LocalTime timeE = LocalTime.parse(timeEs);
        LocalDateTime endDate = dateE.atTime(timeE);

        es.update(new Event(eventID, Integer.parseInt(E_ProductIdTF.getText()), E_TitleTF.getText(), E_DescripTF.getText(), startDate, endDate));

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllEvent.fxml"));
            Parent root = loader.load();
            E_TitleTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void back(ActionEvent event) {
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

    public void setFields(Event event) throws SQLException {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String formattedDateS = event.getDate_debut().format(dateFormatter);
        String formattedTimeS = event.getDate_debut().format(timeFormatter);

        String formattedDateE = event.getDate_fin().format(dateFormatter);
        String formattedTimeE = event.getDate_fin().format(timeFormatter);

        LocalDate startDate = LocalDate.parse(formattedDateS, dateFormatter);
        LocalDate endDate = LocalDate.parse(formattedDateE, dateFormatter);

        this.E_TitleTF.setText(event.getTitre_evenement());
        this.E_StartTF.setText(formattedTimeS);
        this.E_StartDP.setValue(startDate);
        this.E_EndTF.setText(formattedTimeE);
        this.E_EndDP.setValue(endDate);
        this.E_ProductIdTF.setText(String.valueOf(event.getProduit_id()));
        this.E_DescripTF.setText(event.getDescription_evenement());
        this.eventID = event.getId();
    }
}
