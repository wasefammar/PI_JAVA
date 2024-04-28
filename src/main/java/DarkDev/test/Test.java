package DarkDev.test;

import DarkDev.models.LigneCommande;
import DarkDev.models.Produit;
import DarkDev.services.GProduit;
import DarkDev.services.G_ligneCommande;

import java.sql.SQLException;
import java.util.List;

public class Test {
    public static void main(String[] args) {

/*        GProduit gp = new GProduit();
        try {
            //gp.ajouter(new Produit(2, 3, 1, 0,1.1, "sabat", "test", "test", "test","test"));
            //gp.modifier(new Produit(45,2, 3,1, 5.5, "klast", "test", "Tunis", "gabes", "neuf"));
            // gp.supprimer(27);
           *//* System.out.println("Tous les produits");

            List<Produit> produitList= gp.recuperer();
            if(produitList.isEmpty()){
                System.out.println("No products for the moment");
            }else{
                for (Produit s: produitList) {
                    System.out.println(s.toString());
                }}*//*

           System.out.println("Recherche par titre");

            List<Produit> produitParTitre= gp.rechercherParTitre("di");
            if(produitParTitre.isEmpty()){
                System.out.println("No products for the moment");
            }else{
                for (Produit s: produitParTitre) {
                    System.out.println(s.toString());
                }}

            System.out.println("Recherche Par ville");

            List<Produit> produitParVille= gp.rechercherParVille("gabes");
            if(produitParVille.isEmpty()){
                System.out.println("No products for the moment");
            }else{
                for (Produit s: produitParVille) {
                    System.out.println(s.toString());
                }}
            System.out.println("Recherche Par prix croissant");

            List<Produit> produitParPrixCroissant= gp.filtreParPrixCroissant();
            if(produitParVille.isEmpty()){
                System.out.println("No products for the moment");
            }else{
                for (Produit s: produitParPrixCroissant) {
                    System.out.println(s.toString());
                }}

            System.out.println("Recherche Par prix décroissant");

            List<Produit> produitParPrixDecroissant= gp.filtreParPrixDecroissant();
            if(produitParVille.isEmpty()){
                System.out.println("No products for the moment");
            }else{
                for (Produit s: produitParPrixDecroissant) {
                    System.out.println(s.toString());
                }}

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }*/

        G_ligneCommande glc = new G_ligneCommande();
        try{
            //glc.ajouter(new LigneCommande(1,44));
            glc.supprimer(35);

            System.out.println("Tous les ligne commande");

            List<LigneCommande> lcs= glc.Afficher();
            if(lcs.isEmpty()){
                System.out.println("No ligne commande for the moment");
            }else{
                for (LigneCommande s: lcs) {
                    System.out.println(s.toString());
                }}

        }catch (SQLException e){
            System.err.println(e.getMessage());

        }


    }}
