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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServicePersonne;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class NewPassword {


    @FXML
    private Button backBT;

    @FXML
    private Button confirmBT;

    @FXML
    private PasswordField PasswdTX;


    private final ServicePersonne serviceUtilisateur = new ServicePersonne();
    private String adresse_mail;

    public void initData(String adresse_mail) {

        System.out.println(adresse_mail);
        this.adresse_mail= adresse_mail;
        System.out.println(adresse_mail);
    }



    @FXML
    public void confirm(ActionEvent event) {
        try {

            if (PasswdTX.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a new password.");
                return;
            }


            // L'e-mail existe, continuer avec la réinitialisation du mot de passe
            Personne u = serviceUtilisateur.getUserByEmail(adresse_mail);
            String hashedPassword = hashPassword(PasswdTX.getText());


            u.setPassword(hashedPassword);

            
            //serviceUtilisateur.mettreAJourUtilisateur(u);
            serviceUtilisateur.updatePassword(u.getId(), hashedPassword); // Call updatePassword method
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Mot de passe mis à jour avec succès.");
            closeCurrentWindow();
            openSignInWindow();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la mise à jour du mot de passe : " + e.getMessage());
            e.printStackTrace();
        }
    }


    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de hachage du mot de passe", e);
        }
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) PasswdTX.getScene().getWindow();
        stage.close();
    }

    private void openSignInWindow() {
        try {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            primaryStage.setTitle("Connectez");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }








}
