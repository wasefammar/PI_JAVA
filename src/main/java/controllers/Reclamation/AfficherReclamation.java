package controllers.Reclamation;

import controllers.User.SessionTempo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Reclamation.Reclamation;
import services.ServicesReclamation.ServiceReclamation;


public class AfficherReclamation implements Initializable {

    public VBox idComplaints;
    public Button idAddReclamation;
    @FXML
    private HBox cartev;
    @FXML
    private Button idDelete;


    @FXML
    private TextField text;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSend;


    @FXML
    private Button button;

    @FXML
    private Button stat;

    @FXML
    private TableView<?> complaintsTable;

    @FXML
    private TableColumn<Reclamation, String> date_col;

    @FXML
    private TableColumn<Reclamation, String> descrip_col;

    @FXML
    private TableColumn<Reclamation, String> reason_col;

    @FXML
    private TableColumn<Reclamation, String> status_col;

    @FXML
    private TableColumn<Reclamation, String> urgency_col;
    ServiceReclamation rs = new ServiceReclamation();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            System.out.println(SessionTempo.getUser());

            ServiceReclamation serviceReclamation = new ServiceReclamation();

            int size = serviceReclamation.listerReclamation().size();
            List<Reclamation> evenements = serviceReclamation.listerReclamation();
            System.out.println("oui1");
            for (Reclamation evenement : evenements) {
                int id = 0;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReclamationItem.fxml"));
                VBox box = loader.load();
                ReclamationItem carte = loader.getController();
                carte.setData(evenement);


                System.out.println("oui2");
                idComplaints.getChildren().add(box);

                /*carte.setTitre_rLabel(evenement.getTitre_r());
                carte.setDescription_rLabel(evenement.getDescription_r());
                carte.setStatusLabel(evenement.getStatus());
                carte.setUrgenceLabel(evenement.getUrgence());*/

                /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = dateFormat.format(evenement.getDate());
                carte.setDateLabel(dateString);*/
                System.out.println("oui");
                System.out.println("java version: "+System.getProperty("java.version"));
                System.out.println("javafx.version: " + System.getProperty("javafx.version"));
               /* for ( Reclamation evenement2 : serviceReclamation.listerReclamation())
                {
                    if (evenement2.getTitre_r().equalsIgnoreCase(evenement.getTitre_r())){
                        id = evenement.getId();

                    }
                }
                carte.setAvg(String.valueOf(serviceRatingE.getAverageRating(id)));

                */
            }

        } catch (IOException e) {
            System.err.println("Error loading cartearticle.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void ajouter_reclamation1(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouter_reclamation.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            AjouterReclamation ajouter_reclamation1 = loader.getController();

            // Pass any necessary data to the new interface


            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
            complaintsTable.getScene().setRoot(root);

            // Close the current window
            btnSend.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }



    public void update(javafx.event.ActionEvent event) {
/*
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateevenement.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            updateevenement updatearticle = loader.getController();
            // Pass any necessary data to the new interface

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            btn3.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }*/
    }



    @FXML

    public void button(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherReclamation.fxml"));
            Parent root = loader.load();
            // Access the controller of the new interface
            AfficherReclamation ajouterReservation = loader.getController();
            // Pass any necessary data to the new interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Close the current window
            button.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
    @FXML
// le bouton yhezek lel Statistique
    public void stat(javafx.event.ActionEvent event) {
       /* try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/statistique.fxml"));
            Parent root = loader.load();
            // Access the controller of the new interface
            statistique statistique = loader.getController();
            // Pass any necessary data to the new interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Close the current window
            stat.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
*/
    }


    public void addReclamation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouter_reclamation.fxml"));
        Parent root = loader.load();
        idAddReclamation.getScene().setRoot(root);
    }
}