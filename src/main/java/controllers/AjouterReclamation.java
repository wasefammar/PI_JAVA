package controllers;

import com.sun.javafx.scene.SpotLightHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Reclamation;
import services.BadWordsFilter;
import services.ServiceReclamation;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterReclamation implements Initializable {

    String[] badWordsArray = {"5ra", "le", "non","fuck","putin","yezi","edara","esprit","asshole"};

    BadWordsFilter filter=new BadWordsFilter(badWordsArray);


    @FXML
    private HBox cartev;
    @FXML
    private ComboBox<String> reason_combo;

    @FXML
    private ComboBox<String> urgency_combo;

    @FXML
    private TextArea description_id;
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSend;

    @FXML
    private Text required_descrip_id;

    @FXML
    private Text required_level_id;

    @FXML
    private Text required_reason_id;

    int id;
    String status;


    ServiceReclamation rs = new ServiceReclamation();
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String otpStr;

public void initialize(URL url, ResourceBundle rb) {
    reason_combo.getItems().add("employee misconduct");
    reason_combo.getItems().add("Bad Service");
    reason_combo.getItems().add("Security problem");
    reason_combo.getItems().add("Late Shipping");
    System.out.println(reason_combo.getItems());

    urgency_combo.getItems().add("High");
    urgency_combo.getItems().add("Urgent");
    urgency_combo.getItems().add("Normal");
    urgency_combo.getItems().add("Low");
    System.out.println(urgency_combo.getItems());

}


    public void ajouter_reclamation1()  {

        String descriptionService = description_id.getText();
        Date currentDate = Date.valueOf(LocalDate.now());

        try{

            /*if(id!=0){*/
                if ( descriptionService.isEmpty() || reason_combo.getValue()== null|| urgency_combo.getValue()== null){


                    required_descrip_id.setVisible(descriptionService.isEmpty());

                    required_level_id.setVisible(urgency_combo.getValue()==null);
                    required_reason_id.setVisible(reason_combo.getValue()== null);
                    if (descriptionService.isEmpty()) {
                        description_id.setStyle("-fx-border-color: red;");
                    } else {
                        if ( filter.hasBadWord(description_id.getText()) || reason_combo.getItems() != null)
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("ALERT !!");
                            alert.setContentText("BAD WORD");
                            alert.showAndWait();

                        }else {
                            description_id.setStyle("-fx-border-color: green;");
                        }

                    }
                }
                else {
                    if ( filter.hasBadWord(description_id.getText()))
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ALERT !!");
                        alert.setContentText("BAD WORD");
                        alert.showAndWait();

                    }else {
                        String titre_r = reason_combo.getValue();
                        String urgence = urgency_combo.getValue();
                        Reclamation service = new Reclamation(5,titre_r,descriptionService, "pending", urgence ,currentDate);
                        rs.ajouter_Reclamation(service);
                        System.out.println("Complaint added successfully");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherReclamation.fxml"));
                        Parent root = loader.load();
                        description_id.getScene().setRoot(root);
                    }

                }


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void cancelRec(ActionEvent actionEvent) {
    }

    public void SendRec(ActionEvent actionEvent) {
    }
}
