package services.ServicesReclamation;


import models.Reclamation.Reclamation;
import models.User.Personne;
import utils.DatabaseConnexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ServiceReclamation implements IReclamation<Reclamation> {

    private Connection cnx;

    public ServiceReclamation() {
        cnx = DatabaseConnexion.getInstance().getConnection();
    }

    public void ajouter_Reclamation(Reclamation e) {
        try {
            String requete1 = "INSERT INTO reclamation(utilisateur_id,titre_r,description_r,date,status,urgence) VALUES(?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete1);
            pst.setInt(1, e.getUtilisateur_id());

            pst.setString(2, e.getTitre_r());
            pst.setString(3, e.getDescription_r());
            pst.setDate(4, (Date) e.getDate());
            pst.setString(5, e.getStatus());
            pst.setString(6, e.getUrgence());
            pst.executeUpdate();
            System.out.println("Complaint added successfully !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public Reclamation getReclamationById(int id) {

        try {

            String requete3 = "Select * FROM reclamation where id=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete3);
            if (rs.next()) {
                Reclamation rec = new Reclamation();

                rec.setId(rs.getInt(1));
                rec.setUtilisateur_id(rs.getInt("utilisateur_id"));
                rec.setTitre_r(rs.getString("titre_r"));
                rec.setDescription_r(rs.getString("description_r"));
                rec.setDate(rs.getDate("date"));
                rec.setStatus(rs.getString("status"));
                rec.setUrgence(rs.getString("urgence"));

                return rec;

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return null;

    }


    public void supprimer_Reclamation(int id) {

        try {
            String requete2 = "DELETE FROM reclamation WHERE id=" + id;
            Statement st = cnx.createStatement();

            st.executeUpdate(requete2);
            System.out.println("Complaint deleted successfully !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public ArrayList<Reclamation> listerReclamation(int idUtilisateur) {
        ArrayList<Reclamation> myList = new ArrayList<>();
        try {

            String requete3 = "Select * FROM reclamation where utilisateur_id="+idUtilisateur;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()) {
                Reclamation rec = new Reclamation();

                rec.setId(rs.getInt(1));
                rec.setUtilisateur_id(rs.getInt("utilisateur_id"));
                rec.setTitre_r(rs.getString("titre_r"));
                rec.setDescription_r(rs.getString("description_r"));
                rec.setDate(rs.getDate("date"));
                rec.setStatus(rs.getString("status"));
                rec.setUrgence(rs.getString("urgence"));

                myList.add(rec);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        System.out.println("Affichage executé");
        return myList;


    }

    public ArrayList<Reclamation> listerReclamation() {
        ArrayList<Reclamation> myList = new ArrayList<>();
        try {

            String requete3 = "Select * FROM reclamation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()) {
                Reclamation rec = new Reclamation();

                rec.setId(rs.getInt(1));
                rec.setUtilisateur_id(rs.getInt("utilisateur_id"));
                rec.setTitre_r(rs.getString("titre_r"));
                rec.setDescription_r(rs.getString("description_r"));
                rec.setDate(rs.getDate("date"));
                rec.setStatus(rs.getString("status"));
                rec.setUrgence(rs.getString("urgence"));

                myList.add(rec);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        System.out.println("Affichage executé");
        return myList;


    }

    public Personne getUserById(int id) {

        try {

            String requete3 = "Select * FROM utilisateur WHERE id=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete3);
            if (rs.next()) {
                Personne rec = new Personne();

                rec.setId(rs.getInt("id"));
                rec.setAdresse(rs.getString("adresse"));
                rec.setEmail(rs.getString("email"));
                rec.setNom(rs.getString("nom"));
                rec.setPrenom(rs.getString("prenom"));
                rec.setTelephone(rs.getString("telephone"));
                rec.setRole(rs.getString("roles"));

                return rec;

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        System.out.println("Affichage executé");
        return null;
    }


        public void modifier_reclamation(Reclamation reclamation){
        try {
            String requete4 = " UPDATE reclamation SET status = ? WHERE id= "+ reclamation.getId();
            PreparedStatement pst =cnx.prepareStatement(requete4);

            pst.setString(1, reclamation.getStatus());

            pst.executeUpdate();
            System.out.println("Reclamation modifié !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public Map<String, Long> getUrgenceDistribution() {
        Map<String, Long> urgenceDistribution = new HashMap<>();
        String query = "SELECT urgence, COUNT(*) as count FROM reclamation GROUP BY urgence";

        try {
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String gender = resultSet.getString("urgence");
                long count = resultSet.getLong("count");
                urgenceDistribution.put(gender, count);
            }

            System.out.println("Urgence distribution retrieved!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return urgenceDistribution;
    }



}
