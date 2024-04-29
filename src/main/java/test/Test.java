package test;

import Esprit.models.Personne;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.ServicePersonne;

import java.sql.SQLException;

public class Test   {


    public static void main(String[] args) {


        ServicePersonne sp = new ServicePersonne();


        try {

            //sp.ajout(new Personne());

            //sp.modifier(new Personne(12,"obs@gmail.com","22**", "bensalah","ones","blabla","26252592"));

            //sp.supprimer(11);

            System.out.println(sp.recuperer());


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }















    }



