package controllers.User;

import services.ServicesPersonne.ServicePersonne;
import utils.EmailSender;
import utils.SendEmail;
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

import java.io.IOException;
import java.util.Random;

public class CheckMail {


    @FXML
    private Button backBT;

    @FXML
    private TextField mailTF;

    @FXML
    private Button resetBT;
    private final ServicePersonne ServiceUtilisateur = new ServicePersonne();


    @FXML
    void ForgotPWD(ActionEvent event) throws IOException {


            String email = mailTF.getText();
            // Vérifie si l'adresse e-mail existe dans la base de données
            if (ServiceUtilisateur.emailExists(email)) {
                // Génère un nombre aléatoire à 8 chiffres
                int randomnumber = generateRandomNumber(6);
                // Envoie l'e-mail de confirmation
                EmailSender emailSender = new EmailSender();

                emailSender.sendEmail(email, "Code De confirmation", "WElcom to SwapNshare,\n Merci D'Ecrire Ce Code Pour Confirmer  : " + randomnumber + ".");
                // Charge l'interface pour saisir le code
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/VerifCodeInterface.fxml"));
                Parent root = loader.load();
                VerifCode putCodeController = loader.getController();
                putCodeController.initCode(randomnumber, email);
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Email address not found.");
            }

    }

    private int generateRandomNumber(int maxDigits) {
        Random random = new Random();
        // Calcule la valeur maximale en fonction du nombre de chiffres
        int max = (int) Math.pow(10, maxDigits) - 1;
        // Génère un nombre aléatoire dans la plage spécifiée
        return random.nextInt(max);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void back(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately (e.g., log it)
        }
    }
}
