package DarkDev.services;

import DarkDev.interfaces.iLigneCommande;
import DarkDev.models.Produit;
import DarkDev.util.DatabaseConnexion;
import DarkDev.models.LigneCommande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class G_ligneCommande implements iLigneCommande <LigneCommande> {

    Connection cnx;

    public G_ligneCommande (){
        cnx = DatabaseConnexion.getInstance().getConnection();
    }
    @Override
    public void ajouter(LigneCommande ligne_commande) throws SQLException {
        String sql = "INSERT INTO ligne_commande ( panier_id, produit_id )"+
                " values ('"+ligne_commande.getIdPanier()+"'," +"'"+ligne_commande.getIdProduit()+"'"+")";
        Statement st= cnx.createStatement();
        st.executeUpdate(sql);

    }

    @Override
    public void supprimer(int id) throws SQLException {

        String sql= "DELETE FROM ligne_commande where produit_id = ? AND panier_id="+1;
        PreparedStatement ps= cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();

    }

    //l'affichage des produits pour l'utilisateur
    public List<LigneCommande> Afficher() throws SQLException {
        List<LigneCommande> lcs = new ArrayList<>();
        String sql= "SELECT * FROM ligne_commande ";
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()){
            LigneCommande lc= new LigneCommande();
            lc.setIdPanier(rs.getInt("panier_id"));
            lc.setIdProduit(rs.getInt("produit_id"));


            lcs.add(lc);
        }
        return lcs;
    }


    public  List<Produit> ListeProduits() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String sql= "SELECT * FROM produit where id IN(SELECT produit_id FROM ligne_commande WHERE panier_id="+1+")";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Produit produit = new Produit();
            produit.setId(rs.getInt("id"));
            produit.setIdCategorie(rs.getInt("categorie_id"));
            produit.setIdUtilisateur(rs.getInt("utilisateur_id"));
            produit.setTitreProduit(rs.getString("titre_produit"));
            produit.setDescriptionProduit(rs.getString("description_produit"));
            produit.setVille(rs.getString("ville"));
            produit.setPhoto(rs.getString("photo"));
            produit.setChoixEchange(rs.getInt("choix_echange"));
            produit.setEtat(rs.getString("etat"));
            produit.setPrix(rs.getDouble("prix"));


            produits.add(produit);
        }
        return produits;
    }

    public boolean tester(int id) throws SQLException {
        List<LigneCommande> lcs = new ArrayList<>();
        String sql= "SELECT * FROM ligne_commande where produit_id="+id+" AND panier_id="+1;
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()){
            LigneCommande lc= new LigneCommande();
            lc.setIdPanier(rs.getInt("panier_id"));
            lc.setIdProduit(rs.getInt("produit_id"));


            lcs.add(lc);
        }
        return lcs.isEmpty();
    }



}
