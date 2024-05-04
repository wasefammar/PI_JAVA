package Controller.gestion_User;

import Esprit.models.EmailSender;
import Esprit.models.Personne;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServicePersonne;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;






public class UserAddController  {

    @FXML
    private TextField lastNmafield;

    @FXML
    private TextField firstnamefield;

    @FXML
    private TextField adressfield;

    @FXML
    private TextField phonenumber;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private TextField Confirmpassword;
    private final ServicePersonne serviceuser  = new ServicePersonne();

    // Method to validate email using regex pattern


    // Method to set up validation to allow only letters in the text field
    private void setupLetterValidation(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[A-Za-z]*")) {
                return change;
            }
            return null;
        }));
    }


    private void setupValidation() {
        // Validate nom and prenom fields to allow only letters
        setupLetterValidation(lastNmafield);
        setupLetterValidation(firstnamefield);

        // Validate email field to match the email regex pattern
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidEmail(newValue)) {
                email.setStyle("-fx-border-color: red;");
            } else {
                email.setStyle(""); // Remove red border if email is valid
            }
        }
        );

        phonenumber.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!isValidNumber(newValue)) {
                        phonenumber.setStyle("-fx-border-color: red;");
                    } else {
                        phonenumber.setStyle(""); // Remove red border if email is valid
                    }
                }
        );
    }


    private boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }


    // Regular expression for validating email addresses
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";



    private boolean isValidNumber(String number) {
        return Pattern.matches(NUMBER_REGEX, number);
    }

    // Expression régulière pour valider uniquement les nombres
    private static final String NUMBER_REGEX = "\\d+";







    private boolean allFieldsFilled() {
        if (
                email.getText().isEmpty() ||
                        firstnamefield.getText().isEmpty() ||
                        phonenumber.getText().isEmpty() ||
                        password.getText().isEmpty() ||
                        lastNmafield.getText().isEmpty() ||
                        adressfield.getText().isEmpty() ) { // Vérifier si le champ d'adresse locale est rempli




            // Afficher une boîte de dialogue d'erreur indiquant les champs requis
            displayErrorDialog("Please fill in all  fields");
            return false;
        }
        return true;
    }




    public static String doHashing(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            byte[] resultByteArray = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }











    @FXML
    void createAccount(ActionEvent event) {
        try {
            if (allFieldsFilled()) {

                this.setupValidation();
                // Vérification de l'adresse email
                if (!email.getText().contains("@")) {
                    displayErrorDialog("AdressEmail should contains @.");
                    return;
                }

                // Vérification du numéro de téléphone (8 chiffres)
                if (phonenumber.getText().length() != 8 || !phonenumber.getText().matches("\\d{8}" )) {
                    displayErrorDialog("The phone number must contain exactly 8 digits.");
                    return;
                }


                if (!password.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}$")){
                    displayErrorDialog("Password must contain 8 characters at leats 1 lowercase letter and 1 uppercase letter and symbol.");
                    return;
                }

                if (!password.getText().equals(Confirmpassword.getText())){
                    displayErrorDialog("should be the same");
                    return;
                }



                String nom = lastNmafield.getText().trim();
                String prenom = firstnamefield.getText().trim();
                String adresse_mail = email.getText().trim();
                // String password = password.getText(); // No need to trim password

                // Check if the combination of nom and prenom already exists
                if (serviceuser.userExists(nom, prenom)) {
                    displayErrorDialog("Existing User\", \"A user with the same FirstName and LastName already exists");
                    return;
                }

                // Check if the email is already registered
                if (serviceuser.emailExists(adresse_mail)) {
                    displayErrorDialog("Existing Email\", \"This email is already registered.");
                    return;
                }

                /*
                Personne utilisateur = new Personne(
                        email.getText(),
                        doHashing(password.getText()),//  isActive est toujours true lors de la création d'un compte
                        lastNmafield.getText(),
                        firstnamefield.getText(),
                        adressfield.getText(), // Utiliser le champ d'adresse locale
                        phonenumber.getText()



                );*/

                //Ajout de l'utilisateur dans une ssesion tempo
               SessionTempo.getInstance(email.getText(), lastNmafield.getText(), firstnamefield.getText(),adressfield.getText(),phonenumber.getText(), doHashing(password.getText()));



                // Appeler la méthode du service pour créer l'utilisateur
              /*
                // Affichage de l'alerte de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("User added successfully .");
                alert.showAndWait();*/




                String Email = email.getText();

                /*
                SessionTempo.setAdresseEmail(Email);

                String emailDansSession = SessionTempo.getInstance().getAdresseEmail();*/



                // Vérifie si l'adresse e-mail existe dans la base de données

                    // Génère un nombre aléatoire à 8 chiffres
                    int randomnumber = generateRandomNumber(6);
                    // Envoie l'e-mail de confirmation
                    EmailSender emailSender = new EmailSender();

                    emailSender.sendEmail(Email, "Code De confirmation", "WElcom to SwapNshare,\n Merci D'Ecrire Ce Code Pour Confirmer  : " + randomnumber + ".");
                    // Charge l'interface pour saisir le code



                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/VerifCodeInterface.fxml"));
                    //Parent root = FXMLLoader.load(getClass().getResource("/VerifCodeInterface.fxml"));
                    Parent root = loader.load();
                    VerifCode putCodeController = loader.getController();
                    putCodeController.initCode(randomnumber, Email);
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);


            }
        } catch ( IOException e) {
            // Gérer l'exception SQL
            e.printStackTrace();
            //displayErrorDialog("Une erreur SQL s'est produite lors de la création de l'utilisateur.");
        } catch (NumberFormatException e) {
            // Gérer l'exception de format de nombre
            e.printStackTrace();
            displayErrorDialog("Le numéro de téléphone doit être un nombre entier.");
        } catch (IllegalArgumentException e) {
            // Gérer l'exception d'argument illégal
            e.printStackTrace();
            displayErrorDialog("Une erreur s'est produite lors de la création de l'utilisateur.");
        }

    }



    private void displayErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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





}
