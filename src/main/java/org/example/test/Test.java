package org.example.test;

import org.example.models.Service;
import org.example.services.GestionService;

import java.sql.SQLException;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        GestionService gs= new GestionService();
        try {
            gs.ajouter(new Service(2, 3, "", "org/example/test", "org/example/test", "org/example/test", 1, 0));
           // gs.modifier(new Service(26,2, 3, "Coup de Main", "test", "test", "Tunis", 0, 0));
           // gs.supprimer(27);
            System.out.println("Tous les services");

            List<Service> serviceList= gs.recuperer();
           if(serviceList.isEmpty()){
                System.out.println("No services for the moment");
            }else{
            for (Service s: serviceList) {
                System.out.println(s.toString());
            }}

            System.out.println("Recherche par titre");

            List<Service> serviceParTitre= gs.rechercherParTitre("pose");
            if(serviceParTitre.isEmpty()){
                System.out.println("No services for the moment");
            }else{
                for (Service s: serviceParTitre) {
                    System.out.println(s.toString());
                }}

            System.out.println("Recherche Par ville");

            List<Service> serviceParVille= gs.rechercherParVille("Tunis");
            if(serviceParVille.isEmpty()){
                System.out.println("No services for the moment");
            }else{
                for (Service s: serviceParVille) {
                    System.out.println(s.toString());
                }}
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

      /*  ServiceCommentaire sc= new ServiceCommentaire();
        try {
            //sc.ajouter(new Commentaire(3,16,"à tester"));
            sc.modifier(new Commentaire(13,3,16,"No longer à tester"));
            System.out.println("Comment updated successfully");

            sc.supprimer(14);
            System.out.println("Comment deleted successfully");
            List<Commentaire> commentaires= sc.recuperer(14);
            if(commentaires.isEmpty()){
                System.out.println("No comments for the moment");
            }else {
            for (Commentaire c: commentaires) {
                System.out.println(c.toString());
            }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }*/

    }}
