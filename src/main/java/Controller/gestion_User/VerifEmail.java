package Controller.gestion_User;

import Esprit.models.EmailSender;
import Esprit.models.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServicePersonne;

import javax.imageio.IIOException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class VerifEmail {


    @FXML
    private  TextField EmailTX ;


    private final ServicePersonne serviceuser  = new ServicePersonne();


    @FXML
    void VerfiMail ( ActionEvent event){


        try {
            String Email = EmailTX.getText();

            //Personne u  = serviceuser.getUserByEmail(Email);
            // String emailBD = u.getEmail();


/*
            if (!Email.equals(emailBD)) {
                showAlert(Alert.AlertType.ERROR, "Error", "User not found.");
                return; // Sortir de la méthode si l'e-mail n'existe pas
            }*/


            if (serviceuser.emailExists(Email)) {
                // Génère un nombre aléatoire à 8 chiffres
                int randomnumber = generateRandomNumber(6);
                // Envoie l'e-mail de confirmation
                EmailSender emailSender = new EmailSender();

                emailSender.sendEmail(Email, "Code De confirmation", "WElcom to SwapNshare,\n Merci D'Ecrire Ce Code Pour Confirmer  : " + randomnumber + ".");
                // Charge l'interface pour saisir le code

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Codeverif.fxml"));
                Parent root = loader.load();
                Codeverif putCodeController = loader.getController();
                putCodeController.initCode(randomnumber, Email);
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Email address not found.");
            }











          /*  // Génère un nombre aléatoire à 8 chiffres
            int randomnumber = generateRandomNumber(6);
            // Envoie l'e-mail de confirmation
            EmailSender emailSender = new EmailSender();

            emailSender.sendEmail(Email, "Code De confirmation", "WElcom to SwapNshare,\n Merci D'Ecrire Ce Code Pour Confirmer  : " + randomnumber + ".");
            // Charge l'interface pour saisir le code */



          /*
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();*/



            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Codeverif.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/VerifCodeInterface.fxml"));
            Parent root = loader.load();
            Codeverif putCodeController = loader.getController();
            putCodeController.initCode(randomnumber, Email);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        */





           /*
            // Close the current stage (Home stage)
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewPassword.fxml"));
            Parent root = loader.load();
            // Create a new stage for the login interface
            NewPassword passcontroller = loader.getController();
            passcontroller.initData(emailBD);


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("New Password");

            // Show the login stage
            stage.show();
            */

        }catch (IOException e){

            e.printStackTrace();
        }



    }




    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



@FXML
    public void Back(ActionEvent actionEvent){
        try {

            // Close the current stage (Home stage)
            Node source = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();

            // Load the Adduser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            // Create a new stage for the Adduser interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Show the Adduser stage
            stage.show();
        } catch (IOException e) {
            // Handle any potential IOException
            e.printStackTrace();

        }
    }





    private int generateRandomNumber(int maxDigits) {
        Random random = new Random();
        // Calcule la valeur maximale en fonction du nombre de chiffres
        int max = (int) Math.pow(10, maxDigits) - 1;
        // Génère un nombre aléatoire dans la plage spécifiée
        return random.nextInt(max);
    }
















}
