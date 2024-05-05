package services.GestionServices;

import models.Services.Service;
import models.User.Personne;
import utils.DatabaseConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionService implements IService<Service>{

    Connection cnx;

    public  GestionService(){
        cnx = DatabaseConnexion.getInstance().getConnection();
    }


    @Override
    public void ajouter(Service service) throws SQLException {
        String sql = "INSERT INTO service (categorie_id, titre_service, description_service, ville, photo, choix_echange, utilisateur_id, valid) values(?,?,?,?,?,?,?,?)";
        PreparedStatement ps= cnx.prepareStatement(sql);
        ps.setInt(1, service.getIdCategorie());
        ps.setString(2,service.getTitreService());
        ps.setString(3,service.getDescriptionService());
        ps.setString(4,service.getVille());
        ps.setString(5,service.getPhoto());
        ps.setInt(6,service.isChoixEchange());
        ps.setInt(7,service.getIdUtilisateur());
        ps.setInt(8,0);
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql= "DELETE FROM service where id = ?";
        PreparedStatement ps= cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void modifier(Service service) throws SQLException {
       String sql= "UPDATE service set categorie_id = ? , titre_service = ? , description_service = ? , ville = ? , photo = ? , choix_echange = ?  where id = ?";
        PreparedStatement ps= cnx.prepareStatement(sql);
        ps.setInt(1, service.getIdCategorie());
        ps.setString(2, service.getTitreService());
        ps.setString(3, service.getDescriptionService());
        ps.setString(4, service.getVille());
        ps.setString(5, service.getPhoto());
        ps.setInt(6, service.isChoixEchange());
        ps.setInt(7, service.getId());
        ps.executeUpdate();
    }

    //L'affichage des services pour l'admin
    public List<Service> recuperer() throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql= "SELECT * FROM service";
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()){
            Service service = new Service();
            service.setId(rs.getInt("id"));
            service.setIdCategorie(rs.getInt("categorie_id"));
            service.setIdUtilisateur(rs.getInt("utilisateur_id"));
            service.setTitreService(rs.getString("titre_service"));
            service.setDescriptionService(rs.getString("description_service"));
            service.setVille(rs.getString("ville"));
            service.setPhoto(rs.getString("photo"));
            service.setChoixEchange(rs.getInt("choix_echange"));
            service.setValid(rs.getInt("valid"));

            services.add(service);
        }
        return services;
    }

    //l'affichage des services pour l'utilisateur
    public List<Service> Afficher() throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql= "SELECT * FROM service";
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()){
            Service service = new Service();
            service.setId(rs.getInt("id"));
            service.setIdCategorie(rs.getInt("categorie_id"));
            service.setIdUtilisateur(rs.getInt("utilisateur_id"));
            service.setTitreService(rs.getString("titre_service"));
            service.setDescriptionService(rs.getString("description_service"));
            service.setVille(rs.getString("ville"));
            service.setPhoto(rs.getString("photo"));
            service.setChoixEchange(rs.getInt("choix_echange"));
            service.setValid(rs.getInt("valid"));

            services.add(service);
        }
        return services;
    }

    public void validerService(int id) throws SQLException{
        String sql= "UPDATE service set valid = 1 where id = ?";
        PreparedStatement ps= cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public List<Service> rechercherParTitre(String titre) throws  SQLException{
        List<Service> services = new ArrayList<>();
        String sql= "SELECT * FROM service where titre_service LIKE '%"+titre+"%' ";
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()){
            Service service = new Service();
            service.setId(rs.getInt("id"));
            service.setIdCategorie(rs.getInt("categorie_id"));
            service.setIdUtilisateur(rs.getInt("utilisateur_id"));
            service.setTitreService(rs.getString("titre_service"));
            service.setDescriptionService(rs.getString("description_service"));
            service.setVille(rs.getString("ville"));
            service.setPhoto(rs.getString("photo"));
            service.setChoixEchange(rs.getInt("choix_echange"));
            service.setValid(rs.getInt("valid"));

            services.add(service);
        }
        return services;
    }

    public List<Service> rechercherParVille(String ville) throws  SQLException{
        List<Service> services = new ArrayList<>();
        String sql= "SELECT * FROM service where ville LIKE '%" +ville+"%'";
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()){
            Service service = new Service();
            service.setId(rs.getInt("id"));
            service.setIdCategorie(rs.getInt("categorie_id"));
            service.setIdUtilisateur(rs.getInt("utilisateur_id"));
            service.setTitreService(rs.getString("titre_service"));
            service.setDescriptionService(rs.getString("description_service"));
            service.setVille(rs.getString("ville"));
            service.setPhoto(rs.getString("photo"));
            service.setChoixEchange(rs.getInt("choix_echange"));
            service.setValid(rs.getInt("valid"));

            services.add(service);
        }
        return services;
    }

    public List<Service> rechercherParCategory(int id) throws  SQLException{
        List<Service> services = new ArrayList<>();
        String sql= "SELECT * FROM service where categorie_id="+id;


        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()){
            Service service = new Service();
            service.setId(rs.getInt("id"));
            service.setIdCategorie(rs.getInt("categorie_id"));
            service.setIdUtilisateur(rs.getInt("utilisateur_id"));
            service.setTitreService(rs.getString("titre_service"));
            service.setDescriptionService(rs.getString("description_service"));
            service.setVille(rs.getString("ville"));
            service.setPhoto(rs.getString("photo"));
            service.setChoixEchange(rs.getInt("choix_echange"));
            service.setValid(rs.getInt("valid"));

            services.add(service);
        }
        return services;
    }

    public List<Service> filterByEchange(String echange) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql="";
        if(echange.equals("Exchangeable")){
             sql= "SELECT * FROM service where choix_echange="+1;
        }
        else {
             sql= "SELECT * FROM service where choix_echange="+0;
        }

        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()){
            Service service = new Service();
            service.setId(rs.getInt("id"));
            service.setIdCategorie(rs.getInt("categorie_id"));
            service.setIdUtilisateur(rs.getInt("utilisateur_id"));
            service.setTitreService(rs.getString("titre_service"));
            service.setDescriptionService(rs.getString("description_service"));
            service.setVille(rs.getString("ville"));
            service.setPhoto(rs.getString("photo"));
            service.setChoixEchange(rs.getInt("choix_echange"));
            service.setValid(rs.getInt("valid"));

            services.add(service);
        }
        return services;
    }

    public Service getServiceById(int id) throws SQLException {
        String sql= "SELECT * FROM service WHERE id="+id;
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        if(rs.next()){
            Service service = new Service();
            service.setId(rs.getInt("id"));
            service.setIdCategorie(rs.getInt("categorie_id"));
            service.setIdUtilisateur(rs.getInt("utilisateur_id"));
            service.setTitreService(rs.getString("titre_service"));
            service.setDescriptionService(rs.getString("description_service"));
            service.setVille(rs.getString("ville"));
            service.setPhoto(rs.getString("photo"));
            service.setChoixEchange(rs.getInt("choix_echange"));
            service.setValid(rs.getInt("valid"));
            return service;
        }
        return null;
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

    public String getUserById(int id) throws SQLException {
        String sql= "SELECT nom, prenom FROM utilisateur where id="+id;
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()){
            String username= rs.getString("nom") +" "+rs.getString("prenom");
            return  username;
        }

        return  "No user";
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






}
