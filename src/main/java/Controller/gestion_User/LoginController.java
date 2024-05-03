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
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    private final ServicePersonne serviceuser  = new ServicePersonne();

    @FXML
    private Button LoginFT;

    @FXML
    private PasswordField passwordFT;


    @FXML
    private TextField emailFT;

    private int incorrectAttempts = 0;



    @FXML
    public void SignUp (ActionEvent actionEvent){
        try {

            // Close the current stage (Home stage)
            Node source = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();

            // Load the Adduser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/useradd.fxml"));
            Parent root = loader.load();

            // Create a new stage for the Adduser interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up");

            // Show the Adduser stage
            stage.show();
        } catch (IOException e) {
            // Handle any potential IOException
            e.printStackTrace();

        }
    }




    @FXML
    private void LogInAction() {
        String adresse_mail = emailFT.getText();
        String mot_passe = passwordFT.getText();

        // Vérifier si les champs email et mot de passe sont vides
        if (adresse_mail.isEmpty() || mot_passe.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", " Please entre email et password.");
            return;
        }

        // Authentifier l'utilisateur
        boolean authenticated =  serviceuser.connecter(adresse_mail, doHashing(mot_passe));

        System.out.println(doHashing(mot_passe));
        System.out.println(adresse_mail);
        //System.out.println(authenticated);


        boolean VerifPassword = serviceuser.VerifPwd(doHashing(mot_passe));



        if (VerifPassword) {
            System.out.println("Connexion réussie!");
        } else {
            incorrectAttempts++;
            if (incorrectAttempts >= 3) {
                // Désactiver les labels après trois tentatives incorrectes
                passwordFT.setDisable(true);
                emailFT.setDisable(true);
                showAlert(Alert.AlertType.ERROR, "Erreur", "Trop de tentatives incorrectes. Veuillez contacter l'assistance");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Mot de passe incorrect. Tentatives restantes : " + (3 - incorrectAttempts));
            }
        }





        // Vérifier si l'authentification a réussi
        if (authenticated) {
            // Succès de la connexion (naviguer vers la prochaine scène, etc.)
            System.out.println("Connexion réussie !");
            try {
                // Déterminer le rôle de l'utilisateur
                Personne utilisateur = serviceuser.getUserByEmail(adresse_mail);
                System.out.println(utilisateur);
                String role = utilisateur.getRole();

                System.out.println(role);

               // Charger le fichier FXML approprié en fonction du rôle de l'utilisateur
                String fxmlFile = "";
                switch (role) {
                    case "[\"ROLE_ADMIN\"]":
                        fxmlFile = "/Dashuser.fxml";
                        break;

                    case "{\"roles\": \"User\"}":
                        fxmlFile = "/Home.fxml";
                        break;
                    default:
                        // Si le rôle n'est pas reconnu, afficher un message d'erreur et quitter la méthode
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Rôle utilisateur non reconnu.");
                        return;
                }


                //Ajour le user dans une session
                SessionUser.getInstance(utilisateur.getEmail(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getAdresse(), utilisateur.getTelephone(), utilisateur.getId());

                //Afficher le User dans la sessionUser
                SessionUser user = SessionUser.getUser();
                System.out.println(user.toString());



                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();

                // Afficher la nouvelle scène
                Stage stage = (Stage) LoginFT.getScene().getWindow(); // Obtenir la scène actuelle
                stage.setScene(new Scene(root));
                stage.setTitle("Page " + role);
                stage.show();
            } catch (IOException e){

                e.printStackTrace();

            }catch (SQLException e) {
                // Gérer l'exception spécifiquement ou logger l'erreur
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la connexion à la base de données.");
                e.printStackTrace();
            }
        } else {
            // Afficher un message d'erreur pour des identifiants non valides
            showAlert(Alert.AlertType.ERROR, "Erreur", "AdressEmail or Password incorrect.");
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
    public void pwdOublier(ActionEvent actionEvent) {
        try {

            // Close the current stage (Home stage)
            Node source = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();

            // Load the Adduser.fxml file
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/VerifMail.fxml"));
            Parent root = loader.load();



            // Create a new stage for the Adduser interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Mot de passe oUBLIE");

            // Show the Adduser stage
            stage.show();
        } catch (IOException e) {
            // Handle any potential IOException
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec du chargement de l'interface de mot de passe oublié.");
        }
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






































}
