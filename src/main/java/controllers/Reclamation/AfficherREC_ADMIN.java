package controllers.Reclamation;

import controllers.User.SessionUser;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Reclamation.Reclamation;



import javafx.event.ActionEvent;
import services.ServicesPersonne.ServicePersonne;
import services.ServicesReclamation.ServiceReclamation;
//import tray.*;
//import tray.animations.AnimationType;
//import tray.notification.NotificationType;
//import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class AfficherREC_ADMIN implements Initializable {

    public VBox idComplaints;
    public MenuButton idUrgency;
    public MenuButton idStatus;
    ServiceReclamation serviceReclamation = new ServiceReclamation();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ServiceReclamation serviceReclamation = new ServiceReclamation();
            ServicePersonne servicePersonne = new ServicePersonne();
            for (MenuItem node: idUrgency.getItems()){
                node.addEventHandler(ActionEvent.ACTION, e->{
                    try {
                        filterByUrgency(node.getText());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }


            int size = serviceReclamation.listerReclamation().size();
            List<Reclamation> evenements = serviceReclamation.listerReclamation();
            MenuItem menuItem2 = new MenuItem();
            menuItem2.setText("Both");
            menuItem2.addEventHandler(ActionEvent.ACTION, e->{
                try {
                    filterByStatus("Both");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            idStatus.getItems().add(menuItem2);
            MenuItem menuItem = new MenuItem();
            menuItem.setText("Treated");
            menuItem.addEventHandler(ActionEvent.ACTION, e->{
                try {
                    filterByStatus("Treated");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            idStatus.getItems().add(menuItem);
            MenuItem menuItem1 =new MenuItem();
            menuItem1.setText("pending");
            menuItem1.addEventHandler(ActionEvent.ACTION, e->{
                try {
                    filterByStatus("pending");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            idStatus.getItems().add(menuItem1);

           // List<Personne> personne = servicePersonne.listerPersonne();
            System.out.println("oui1");
            for (Reclamation evenement : evenements) {
                int id = 0;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecAdminItem.fxml"));
                VBox box = loader.load();
                //VBox box = loader.<VBox>load();
                RecAdminItem carte = loader.getController();
                carte.setData(evenement);

                System.out.println("oui2");
                idComplaints.getChildren().add(box);


                System.out.println("oui");
                System.out.println("java version: "+System.getProperty("java.version"));
                System.out.println("javafx.version: " + System.getProperty("javafx.version"));

            }

        } catch (IOException e) {
            System.err.println("Error loading cartearticle.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void filterByStatus(String s) throws IOException {


        List<Reclamation> reclamations = serviceReclamation.listerReclamation();
        if(idUrgency.getText().equals("All") || idUrgency.getText().equals("Filter By Urgency")){
            idUrgency.setText("All");
        }
        if (idUrgency.getText().equals("Low")){
            idUrgency.setText("Low");
            reclamations = reclamations.stream().filter(r->r.getUrgence().equals("Low")).toList();
        }
        if (idUrgency.getText().equals("High")){
            idUrgency.setText("High");
            reclamations = reclamations.stream().filter(r->r.getUrgence().equals("High")).toList();
        }
        if (idUrgency.getText().equals("Urgent")){
            idUrgency.setText("Urgent");
            reclamations = reclamations.stream().filter(r->r.getUrgence().equals("Urgent")).toList();
        }
        if (idUrgency.getText().equals("Normal")){
            idUrgency.setText("Normal");
            reclamations = reclamations.stream().filter(r->r.getUrgence().equals("Normal")).toList();
        }


        if(s.equals("Both")){
            idStatus.setText("Both");
        }
        if (s.equals("Treated")){
            idStatus.setText("Treated");
            reclamations = reclamations.stream().filter(r->r.getStatus().equals("Treated")).toList();
        }
        if (s.equals("pending")){
            idStatus.setText("pending");
            reclamations = reclamations.stream().filter(r->r.getStatus().equals("pending")).toList();
        }

        idComplaints.getChildren().clear();

        for (Reclamation evenement : reclamations) {
            int id = 0;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecAdminItem.fxml"));
            VBox box = loader.load();
            //VBox box = loader.<VBox>load();
            RecAdminItem carte = loader.getController();
            carte.setData(evenement);

            System.out.println("oui2");
            idComplaints.getChildren().add(box);


            System.out.println("oui");
            System.out.println("java version: "+System.getProperty("java.version"));
            System.out.println("javafx.version: " + System.getProperty("javafx.version"));

        }
    }

    public  void filterByUrgency(String s) throws IOException {
        List<Reclamation> reclamations = serviceReclamation.listerReclamation();

        if(idStatus.getText().equals("Both") || idStatus.getText().equals("Filter By Status")){
            idStatus.setText("Both");
        }
        if (idStatus.getText().equals("Treated")){
            idStatus.setText("Treated");
            reclamations = reclamations.stream().filter(r->r.getStatus().equals("Treated")).toList();
        }
        if (idStatus.getText().equals("pending")){
            idStatus.setText("pending");
            reclamations = reclamations.stream().filter(r->r.getStatus().equals("pending")).toList();
        }

        if(s.equals("All")){
            idUrgency.setText("All");
        }
        if (s.equals("Low")){
            idUrgency.setText("Low");
            reclamations = reclamations.stream().filter(r->r.getUrgence().equals("Low")).toList();
        }
        if (s.equals("High")){
            idUrgency.setText("High");
            reclamations = reclamations.stream().filter(r->r.getUrgence().equals("High")).toList();
        }
        if (s.equals("Urgent")){
            idUrgency.setText("Urgent");
            reclamations = reclamations.stream().filter(r->r.getUrgence().equals("Urgent")).toList();
        }
        if (s.equals("Normal")){
            idUrgency.setText("Normal");
            reclamations = reclamations.stream().filter(r->r.getUrgence().equals("Normal")).toList();
        }

        idComplaints.getChildren().clear();

        for (Reclamation evenement : reclamations) {
            int id = 0;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecAdminItem.fxml"));
            VBox box = loader.load();
            //VBox box = loader.<VBox>load();
            RecAdminItem carte = loader.getController();
            carte.setData(evenement);

            System.out.println("oui2");
            idComplaints.getChildren().add(box);


            System.out.println("oui");
            System.out.println("java version: "+System.getProperty("java.version"));
            System.out.println("javafx.version: " + System.getProperty("javafx.version"));

        }
    }

    public void stat(ActionEvent actionEvent) {

        try {
            String title = "Congratulations sir";
            String message = "You've successfully created your first Tray Notification";


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/statReclamation.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) idStatus.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }

    }

    public void users(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/Dashuser.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) idComplaints.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void service(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ServicesAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) idComplaints.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
       // idComplaints.getScene().setRoot(root);
    }

    public void product(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ProduitsAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) idComplaints.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void transaction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/AfficheEchangeService.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) idComplaints.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void events(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) idComplaints.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }


    public void logout(ActionEvent actionEvent) throws IOException {
        SessionUser.resetSession();
        System.out.println(SessionUser.getUser());
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) idComplaints.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void complaint(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) idComplaints.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void returnToCategories(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/CategorieFX.fxml"));        Parent anchorPane = loader.load();
        Stage stage = (Stage) idComplaints.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void returnToHome(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/dash_admin.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) idComplaints.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }
}
