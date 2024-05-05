package services.ServicesReclamation;


import models.Reclamation.Reponse;
import utils.DatabaseConnexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class ServiceReponse implements IReponse<Reponse> {


    private Connection cnx;

    public ServiceReponse() {
        cnx = DatabaseConnexion.getInstance().getConnection();
    }

    public void ajouter_Reponse(Reponse e) {
        try {
            String requete1 = "INSERT INTO reponse(reponse_id,titre_r,description_r,date) VALUES(?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete1);
            pst.setInt(1, e.getReponse_id());

            pst.setString(2, e.getTitre_r());
            pst.setString(3, e.getDescription_id());
            pst.setDate(4 , (Date) e.getDate());

            pst.executeUpdate();
            System.out.println("The Response has been sent successfully !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public ArrayList<Reponse> listerReponse() {
        ArrayList<Reponse> myList = new ArrayList<>();
        try {

            String requete3 = "Select * FROM reponse";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()) {
                Reponse rec = new Reponse();

                rec.setId (rs.getInt(1));
                rec.setReponse_id(rs.getInt("reponse_id"));
                rec.setTitre_r(rs.getString("titre_r"));
                rec.setDescription_id(rs.getString("description_r"));
                rec.setDate(rs.getDate("date"));


                myList.add(rec);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        System.out.println("Affichage executé");
        return myList;


    }

    public Reponse getRepByIdRec(int id) {

        String requete5 = "SELECT *  FROM reponse WHERE reponse_id = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(requete5);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Reponse rec = new Reponse();

                rec.setId(rs.getInt("id"));
                rec.setReponse_id(rs.getInt("reponse_id"));
                rec.setTitre_r(rs.getString("titre_r"));
                rec.setDescription_id(rs.getString("description_r"));
                rec.setDate(rs.getDate("date"));

                return rec;

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

}
