package SwapNShare.sami.controllers;

import SwapNShare.sami.models.Event;
import SwapNShare.sami.services.EventService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class AddEvent {

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

    public void AddE(ActionEvent actionEvent) {

        LocalDate dateS = E_StartDP.getValue();
        String timeSs = E_StartTF.getText();
        LocalTime timeS = LocalTime.parse(timeSs);
        LocalDateTime startDate = dateS.atTime(timeS);

        LocalDate dateE = E_EndDP.getValue();
        String timeEs = E_EndTF.getText();
        LocalTime timeE = LocalTime.parse(timeEs);
        LocalDateTime endDate = dateE.atTime(timeE);

        es.add(new Event(Integer.parseInt(E_ProductIdTF.getText()), E_TitleTF.getText(), E_DescripTF.getText(), startDate, endDate));

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllEvent.fxml"));
            Parent root = loader.load();
            E_TitleTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

//        if (timeSs.isEmpty()) {
//            showAlert(Alert.AlertType.ERROR, "Error", null, "Please fill in all fields.");
//            return;
//        }
    }

    @FXML
    void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllEvent.fxml"));
            Parent root = loader.load();
            E_TitleTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
