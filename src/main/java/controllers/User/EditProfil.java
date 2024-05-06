package controllers.User;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.User.Personne;
import services.ServicesPersonne.ServicePersonne;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;


public class EditProfil {

    @FXML
    private TextField AdressInp;

    @FXML
    private TextField lastnameIn;

    @FXML
    private TextField PhoneInp;

    @FXML
    private TextField firstnameInp;

    @FXML
    private TextField EmailInp;


    private final ServicePersonne serviceUser = new ServicePersonne();
    private Personne User;


    @FXML
    void modifierUtilisateur(ActionEvent event) {
        try {
            if (allFieldsFilled()) {
                // Vérification de l'adresse email
                if (!EmailInp.getText().contains("@")|| !EmailInp.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    displayErrorDialog("AdressEmail should contains @..");
                    return;
                }

                // Vérification du numéro de téléphone (8 chiffres)
                if (PhoneInp.getText().length() != 8 || !PhoneInp.getText().matches("\\d{8}")|| !PhoneInp.getText().matches("\\d+") ) {
                    displayErrorDialog("The phone number must contain exactly 8 digits.");
                    return;
                }



                int Id = SessionUser.getUser().getId();

                System.out.println(Id);





                // Créer un objet Utilisateur avec le constructeur approprié
                Personne u = new Personne(
                        Id,
                        EmailInp.getText(),
                        firstnameInp.getText(),
                        lastnameIn.getText(),
                        AdressInp.getText(),
                        PhoneInp.getText()


                );




                Dialog<ButtonType> dialg = new Dialog<>();
                dialg.setTitle("Save Confirmation");
                dialg.setHeaderText("Are you sure you want to save");
                dialg.initModality(Modality.APPLICATION_MODAL);
                ButtonType okbtn = new ButtonType("Save",ButtonBar.ButtonData.OK_DONE);
                ButtonType cancelbtn = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
                dialg.getDialogPane().getButtonTypes().addAll(okbtn,cancelbtn);
                Optional<ButtonType> result =dialg.showAndWait();
                if(result.isPresent() && result.get()== okbtn){
                    serviceUser.modifier(u);

                    // Afficher une boîte de dialogue de confirmation
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("User modified  successfully .");
                    alert.showAndWait();
                }


                //SessionUser.getInstance(u.getEmail(), u.getNom(), u.getPrenom(), u.getAdresse(), u.getTelephone(), u.getId());



                Node source = (Node) event.getSource();
                Stage currentStage = (Stage) source.getScene().getWindow();
                currentStage.close();

                // Load the login.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
                Parent root = loader.load();

                Home  UserController = loader.getController();
                 UserController.RetourProfil(u);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                // Create a new stage for the login interfacetage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Login");

                // Show the login stage
                stage.show();





            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            // Gérer les erreurs de connexion à la base de données ou autres erreurs SQL
            e.printStackTrace();
            displayErrorDialog("Une erreur s'est produite lors de la modification de l'utilisateur.");
        } catch (NumberFormatException e) {
            // Gérer les erreurs de format de nombre
            e.printStackTrace();
            displayErrorDialog("Le numéro de téléphone doit être un nombre entier.");
        }
    }

    // Méthode de validation pour vérifier si tous les champs sont remplis
    private boolean allFieldsFilled() {
        if (PhoneInp.getText().isEmpty() ||
                EmailInp.getText().isEmpty() ||
                lastnameIn.getText().isEmpty() ||
                firstnameInp.getText().isEmpty() ||
                AdressInp.getText().isEmpty()
                ) {

            // Afficher une boîte de dialogue d'erreur indiquant les champs requis
            displayErrorDialog("Please fill in all  fields.");
            return false;
        }
        return true;
    }

    // Méthode pour afficher une boîte de dialogue d'erreur avec un message personnalisé
    private void displayErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public void initData(Personne utilisateur) {
        this.User=utilisateur;
        if (utilisateur != null) {
            lastnameIn.setText(utilisateur.getNom());
            firstnameInp.setText(utilisateur.getPrenom());
            EmailInp.setText(utilisateur.getEmail());
            PhoneInp.setText(String.valueOf(utilisateur.getTelephone()));
            AdressInp.setText(utilisateur.getAdresse());


        }
    }




    @FXML
    public void BackProfil(ActionEvent actionEvent){
        try {

            Node source = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
            // Load the Adduser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
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






  public  void Profiluser(SessionUser user){

        AdressInp.setText(user.getAdress());
        lastnameIn.setText(user.getPrenom());
        firstnameInp.setText(user.getNom());
        PhoneInp.setText(user.getTelphone());
        EmailInp.setText(user.getAdresseEmail());



  }



}
