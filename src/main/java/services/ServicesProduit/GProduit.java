package services.ServicesProduit;
import models.Produit.Produit;
import models.Services.Service;
import models.User.Personne;
import utils.DatabaseConnexion;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GProduit implements iProduit<Produit> {
    Connection cnx;

    public GProduit() {
        cnx = DatabaseConnexion.getInstance().getConnection();
    }

    @Override
    public void ajouter(Produit produit) throws SQLException {
        String sql = "INSERT INTO produit (categorie_id, utilisateur_id, titre_produit, description_produit, photo, ville, choix_echange,etat,prix) values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, produit.getIdCategorie());
        ps.setInt(2, produit.getIdUtilisateur());
        ps.setString(3, produit.getTitreProduit());
        ps.setString(4, produit.getDescriptionProduit());
        ps.setString(5, produit.getPhoto());
        ps.setString(6, produit.getVille());
        ps.setInt(7, produit.getChoixEchange());
        ps.setString(8, produit.getEtat());
        ps.setDouble(9, produit.getPrix());

        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM produit where id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void modifier(Produit produit) throws SQLException {
        String sql = "UPDATE produit set categorie_id = ? , titre_produit = ? , description_produit = ? , ville = ? , photo = ? , choix_echange = ? ,etat = ? ,prix = ? where id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, produit.getIdCategorie());
        ps.setString(2, produit.getTitreProduit());
        ps.setString(3, produit.getDescriptionProduit());
        ps.setString(4, produit.getVille());
        ps.setString(5, produit.getPhoto());
        ps.setInt(6, produit.getChoixEchange());
        ps.setString(7, produit.getEtat());
        ps.setDouble(8, produit.getPrix());
        ps.setInt(9, produit.getId());
        ps.executeUpdate();
    }

    //L'affichage des produits pour l'admin
    public List<Produit> recuperer() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit";
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

    //l'affichage des produits pour l'utilisateur
    public List<Produit> Afficher() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit where valid = 1 ";
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

 /*   public void validerProduit(int id) throws SQLException{
        String sql= "UPDATE produit set valid = 1 where id = ?";
        PreparedStatement ps= cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }*/

    public List<Produit> rechercherParTitre(String titre) throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit where titre_produit LIKE '%" + titre + "%' ";
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

    public List<Produit> rechercherParVille(String ville) throws SQLException {
        List<Produit> services = new ArrayList<>();
        String sql = "SELECT * FROM produit where ville LIKE '%" + ville + "%'";
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

            services.add(produit);
        }
        return services;
    }

    public List<Produit> filtreParPrixCroissant() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit ORDER BY prix ASC";
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

    public List<Produit> filtreParPrixDecroissant() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit ORDER BY prix DESC ";
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


    public List<String> ListeCategories() throws  SQLException{
        List<String> categories = new ArrayList<>();
        String sql= "SELECT * FROM categorie";
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()){
            categories.add(rs.getString("nom_categorie"));
        }
        return categories;
    }


    public int getCategoryByName(String nom) throws SQLException {
        String sql= "SELECT id FROM categorie where nom_categorie LIKE '%"+nom+"%'";
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()){
            return  rs.getInt("id");
        }

        return  0;
    }


    public String getCategoryById(int id) throws SQLException {
        String sql= "SELECT nom_categorie FROM categorie where id="+id;
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()){
            return  rs.getString("nom_categorie");
        }

        return  "hhhh";
    }

    public Produit getProduitById(int id) throws SQLException {
        String sql= "SELECT * FROM produit where id="+id;
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()){
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
            return  produit;
        }

        return  null;
    }

    public List<Produit> rechercherParCategory(int id) throws  SQLException {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit WHERE categorie_id=" + id;
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

    public List<Produit> getProduitByUserId(int utilisateur_id) throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit WHERE utilisateur_id = ?";
        PreparedStatement pst = cnx.prepareStatement(sql);
        pst.setInt(1, utilisateur_id);
        ResultSet rs = pst.executeQuery();
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

    public Personne getIdUtilisateurByEmail(String adresse_mail) throws SQLException {
        Personne utilisateur = null;
        PreparedStatement pstmt = null;
        try {
            String query = "SELECT * FROM utilisateur WHERE email = ?";
            pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, adresse_mail);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                utilisateur = new Personne();
                utilisateur.setId(rs.getInt("id"));
                //utilisateur.setAdresse("email");
                utilisateur.setEmail("email");
                utilisateur.setNom("nom");
                utilisateur.setPrenom("prenom");


            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }

        return utilisateur;
    }

    public Produit getProduitByName(String nom) throws SQLException {
        String sql= "SELECT * FROM produit where titre_produit LIKE '%"+nom+"%'";
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()){
            Produit produit= new Produit();
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
            return produit;
        }

        return  null;
    }


}
