package Controller.gestion_User;

import Esprit.models.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import services.ServicePersonne;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
                    return new ListCell<>(){

                        @Override
                        protected void updateItem(Personne utilisateur, boolean empty) {
                            super.updateItem(utilisateur, empty);

                            if (empty || utilisateur == null) {
                                setText(null);
                            } else {
                                // Set the text of the cell to display only the desired fields
                                setText("First Name: " + utilisateur.getNom() + "\n" +
                                        "Last Name: " + utilisateur.getPrenom() + "\n" +
                                        "AdressEmail:" + utilisateur.getAdresse()+"\n" +
                                        "Adress:" + utilisateur.getAdresse()+ "\n"+
                                        "PhoneNumber:" + utilisateur.getTelephone()+ "\n"+
                                        "Role: " + utilisateur.getRole());
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








}