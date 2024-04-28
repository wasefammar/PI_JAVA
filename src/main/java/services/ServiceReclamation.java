package services;

import iservices.IReclamation;
import models.Personne;
import models.Reclamation;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;


public class ServiceReclamation implements IReclamation<Reclamation> {

    private Connection cnx;

    public ServiceReclamation() {
        cnx = MyDataBase.getInstance().getConnection();
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
                rec.setRole(rs.getString("role"));

                return rec;

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        System.out.println("Affichage executé");
        return null;



      /*  public void modifier_reclamation(int id,String titre_r,String description_r,Date date,String status,String urgence) {
        try {
            String requete4 = " UPDATE reclamation SET " + " titre_r = ?,description_r = ? ,date = ? , status = ?, urgence = ?WHERE id= "+ id;
            PreparedStatement pst =cnx.prepareStatement(requete4);

            pst.setString(1, titre_r);
            pst.setString(2, description_r);
            pst.setDate(3, date);
            pst.setString(4, status);
            pst.setString(5, urgence);
            pst.executeUpdate();
            System.out.println("Reclamation modifié !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
*/

    }
}
