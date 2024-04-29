package Controller.gestion_User;

import Esprit.models.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {




    @FXML
    private Label mailU;

    @FXML
    private Label AdressU;

    @FXML
    private Label prenomU;

    @FXML
    private Label phoneU;

    @FXML
    private Label nomU;

    @FXML
    private Label Idu;






    SessionUser user = SessionUser.getUser();



    @FXML
    private void initProfile() {
        // Set user's information
        if (user != null) {
            nomU.setText(user.getNom());
            prenomU.setText(user.getPrenom());
            phoneU.setText(user.getTelphone());
            mailU.setText(user.getAdresseEmail());
            AdressU.setText(user.getAdress());
            Idu.setText(String.valueOf(user.getId()));





        } else {
            System.out.println("No logged-in user found.");
        }
    }


  @FXML
    public void EditProfil(ActionEvent actionEvent) {
        try {
            // Close the current stage (Home stage)
            Node source = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();

            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditProfil.fxml"));
            Parent root = loader.load();

            EditProfil putUserController = loader.getController();
            putUserController.Profiluser(user);


            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Create a new stage for the login interfacetage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");

            // Show the login stage
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





   public  void RetourProfil(Personne u){

       SessionUser.getInstance(u.getEmail(), u.getNom(), u.getPrenom(), u.getAdresse(), u.getTelephone(), u.getId());

       SessionUser user = SessionUser.getUser();

       if (user != null) {
           nomU.setText(user.getNom());
           prenomU.setText(user.getPrenom());
           phoneU.setText(user.getTelphone());
           mailU.setText(user.getAdresseEmail());
           AdressU.setText(user.getAdress());
           Idu.setText(String.valueOf(user.getId()));





       } else {
           System.out.println("No logged-in user found.");
       }







   }














    @FXML
    public void LogOut(ActionEvent actionEvent) {
        try {
            // Close the current stage (Home stage)
            Node source = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();

            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gestion_user/userSigninInterface.fxml"));
            Parent root = loader.load();

            // Create a new stage for the login interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");

            // Show the login stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initProfile();
    }
}
