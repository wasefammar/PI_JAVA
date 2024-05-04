package Controller.gestion_User;

import Esprit.models.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServicePersonne;

import java.io.IOException;
import java.sql.SQLException;

public class VerifCode {
    private int code;
    private String  adresse_mail;
    @FXML
    private TextField codeTF;
    private String verificationCode;



    private final ServicePersonne serviceuser  = new ServicePersonne();

    private boolean validateCode(String inputCode) {
        // Check if the input code matches the verification code
        return inputCode.equals(verificationCode);
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
/*
    @FXML
    void back(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gestion_user/CheckMailInterface.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @FXML
    void confirmCode(ActionEvent event){

        try {
            // Get the user's input code from the codeTF field
            String inputCode = codeTF.getText();

            // Validate the input code using the validateCode method
            if (validateCode(inputCode)) {

                SessionTempo user = SessionTempo.getUser();
                // Appeler la méthode du service pour créer l'utilisateur
                serviceuser.ajoutUser(user);
                System.out.println("Compte créé avec succès !");

                System.out.println( user.toString());


                 //Supprimer le user de la session Temporaire

                  SessionTempo.resetSession();

                   //verifier la suppresion de user dans session tempo
                System.out.println(user.toString());

                // Affichage de l'alerte de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("User added successfully .");
                alert.showAndWait();


                // Show another scene where the user can enter their new password
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
                Parent root = loader.load();
                //NewPassword controller = loader.getController();
                //controller.initData(adresse_mail);
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
            } else {
                // Show an alert that the code is incorrect and ask the user to try again
                showAlert(Alert.AlertType.ERROR, "Code invalide", "Le code que vous avez entré est incorrect. Veuillez réessayer.");
            }
        }catch ( IOException e) {
            // Gérer l'exception SQL
            e.printStackTrace();
            //displayErrorDialog("Une erreur SQL s'est produite lors de la création de l'utilisateur.");
        }catch (SQLException e) {
            // Gérer l'exception spécifiquement ou logger l'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la connexion à la base de données.");
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

    public void initCode(int code, String adresse_mail) {
        this.verificationCode = String.valueOf(code);
        this.adresse_mail = adresse_mail;
    }




    @FXML
    void backSignup(ActionEvent event) throws IOException {
        try {

            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
            // Load the Adduser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/useradd.fxml"));
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






}
