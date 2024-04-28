package controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import models.Personne;
import models.Reclamation;
import services.ServicePersonne;
import services.ServiceReclamation;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherREC_ADMIN implements Initializable {

    public VBox idComplaints;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ServiceReclamation serviceReclamation = new ServiceReclamation();
            ServicePersonne servicePersonne = new ServicePersonne();


            int size = serviceReclamation.listerReclamation().size();
            List<Reclamation> evenements = serviceReclamation.listerReclamation();
            List<Personne> personne = servicePersonne.listerPersonne();
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
}
