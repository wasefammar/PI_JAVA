package controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import models.User.Personne;


import java.util.*;
import java.io.*;
import java.security.*;

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
    private ImageView avatarImage;


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


            String userName = user.getPrenom(); // Replace with your username logic
            System.out.println(userName);
            String baseUrl = "https://ui-avatars.com/api/";
            String imageUrl = baseUrl + "name=" + userName;

            try {
                Image image = new Image(imageUrl);
                avatarImage.setImage(image);
            } catch (Exception e) {
                // Handle image loading exception (e.g., display default avatar)
                System.out.println("Error loading image: " + e);
            }







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






           /*
           String email = user.getAdresseEmail().toLowerCase();
           String hash = sha256Hex(email);
           String gravatarURL = "https://gravatar.com/avatar/" + hash;
           Image image = new Image(gravatarURL);
           avatarImage.setImage(image);
*/
         /*
           String prenom = user.getNom(); // Remplacez ceci par le prénom de l'utilisateur
           String imageUrl = "https://ui-avatars.com/api/?name= "+ prenom;



           // Chargement de l'image depuis l'URL
           Image image = new Image(imageUrl);
           avatarImage.setImage(image);





           if (avatarImage != null) {
               avatarImage.setImage(image);
           } else {
               System.out.println("Avatar Image View is null. Check if it's properly initialized in the FXML.");
           }


        */


       } else {
           System.out.println("No logged-in user found.");
       }







   }














    @FXML
    public void LogOut(ActionEvent actionEvent) {
        try {



            SessionUser user = SessionUser.getUser();
            System.out.println(user.toString());
            SessionUser.resetSession();

            System.out.println(user.toString());

            // Close the current stage (Home stage)
            Node source = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();

            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
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



    public static String sha256Hex(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return hex(md.digest(message.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            // Consider logging this exception or rethrowing as a RuntimeException
        } catch (UnsupportedEncodingException e) {
            // Consider logging this exception or rethrowing as a RuntimeException
        }
        return null;
    }

    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i]
                    & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }







}
