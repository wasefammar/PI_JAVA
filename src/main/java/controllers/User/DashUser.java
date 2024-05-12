package controllers.User;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.User.Personne;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.ServicesPersonne.ServicePersonne;

import java.io.FileOutputStream;
import java.io.File;

public class DashUser {


    @FXML
    private ListView<Personne> userListview;

    private final ServicePersonne serviceuser = new ServicePersonne();


    @FXML
    void initialize() {
        try {
            List<Personne> userList = serviceuser.recuperer();

            userListview.getItems().addAll(userList);

            userListview.setCellFactory(new Callback<ListView<Personne>, ListCell<Personne>>() {

                @Override
                public ListCell<Personne> call(ListView<Personne> personneListView) {
                    return new ListCell<>() {

                        @Override
                        protected void updateItem(Personne utilisateur, boolean empty) {
                            super.updateItem(utilisateur, empty);

                            if (empty || utilisateur == null) {
                                setText(null);
                            } else {
                                // Set the text of the cell to display only the desired fields
                                setText("First Name:                       "+ utilisateur.getNom() + "\n" +
                                        "Last Name:                       " + utilisateur.getPrenom() + "\n" +
                                        "AdressEmail:                     " + utilisateur.getEmail() + "\n" +
                                        "Adress:                          "+ utilisateur.getAdresse() + "\n" +
                                        "PhoneNumber:               " + utilisateur.getTelephone() + "\n" +
                                        "Role:                            "  + utilisateur.getRole());
                            }


                        }
                    };
                }


            });


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void supprimerUtilisateur(ActionEvent event) {
        Personne selectedUser = userListview.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText("Supprimer l'utilisateur ?");
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Implémenter la logique pour supprimer l'utilisateur sélectionné
                    serviceuser.supprimer(selectedUser.getId());
                    // Rafraîchir les données après la suppression
                    initData();
                } catch (SQLException e) {
                    afficherMessageErreur("Erreur lors de la suppression de l'utilisateur.");
                }
            }
        } else {
            afficherMessageErreur("Veuillez sélectionner un utilisateur à supprimer.");
        }
    }

    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void initData() {
        try {
            List<Personne> userList = serviceuser.recuperer();
            ObservableList<Personne> observableList = FXCollections.observableArrayList(userList);
            userListview.setItems(observableList);
        } catch (SQLException e) {
            afficherMessageErreur("Erreur lors de l'initialisation des données.");
        }
    }




    public void FichierExel(ActionEvent actionEvent) {
        String filePath = "C:\\Users\\AGENT17\\Desktop\\users.xlsx"; // Chemin du fichier Excel

        try {
            // Créer un nouveau classeur Excel
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Utilisateurs");

            // Ajouter les en-têtes
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nom");
            headerRow.createCell(1).setCellValue("Prénom");
            headerRow.createCell(2).setCellValue("Adresse Email");
            headerRow.createCell(3).setCellValue("Adresse");
            headerRow.createCell(4).setCellValue("Numéro de téléphone");
            headerRow.createCell(5).setCellValue("Rôle");

            // Ajouter les données des utilisateurs
            ObservableList<Personne> users = userListview.getItems();
            int rowNum = 1;
            for (Personne user : users) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getNom());
                row.createCell(1).setCellValue(user.getPrenom());
                row.createCell(2).setCellValue(user.getAdresse());
                row.createCell(3).setCellValue(user.getAdresse());
                row.createCell(4).setCellValue(user.getTelephone());
                row.createCell(5).setCellValue(user.getRole());
            }

            // Écrire le classeur dans le fichier
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }

            // Afficher un message de réussite
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Export réussi");
            successAlert.setHeaderText(null);
            successAlert.setContentText("La liste des utilisateurs a été exportée avec succès vers un fichier Excel.");
            successAlert.showAndWait();

        } catch (IOException e) {
            afficherMessageErreur("Erreur lors de l'exportation vers un fichier Excel.");
        }
    }


    public void users(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/Dashuser.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) userListview.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void service(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ServicesAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) userListview.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void product(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ProduitsAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) userListview.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void transaction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/AfficheEchangeService.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) userListview.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void events(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) userListview.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }


    public void logout(ActionEvent actionEvent) throws IOException {
        SessionUser.resetSession();
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) userListview.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void complaint(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) userListview.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void returnToCategories(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/CategorieFX.fxml"));        Parent anchorPane = loader.load();
        Stage stage = (Stage) userListview.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void returnToHome(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/dash_admin.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) userListview.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }



}