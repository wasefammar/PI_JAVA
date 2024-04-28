package services;
import iservices.IPersonne;
import iservices.IReclamation;
import models.Personne;
import models.Reclamation;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

public class ServicePersonne implements IPersonne<Personne> {

    Connection cnx;

    public ServicePersonne() {
        cnx = MyDataBase.getInstance().getConnection();
    }




    @Override
    public void ajoutUser(Personne personne) throws SQLException {

        String sql = "INSERT INTO user (email,role,mot_de_passe,nom, prenom, adresse,telephone) values(?, ?, ?, ?, ?, ?,?)";


        PreparedStatement ps = cnx.prepareStatement(sql);

        ps.setString(1,  personne.getEmail());
        String roleuser = "{\"roles\": \"User\"}";
        ps.setString(2, roleuser);
        ps.setString(3, personne.getMot_de_passe());
        ps.setString(4, personne.getNom());
        ps.setString(5, personne.getPrenom());
        ps.setString(6, personne.getAdresse());
        ps.setString(7, personne.getTelephone());


        ps.executeUpdate();

    }








    @Override
    public List<Personne> recuperer() throws SQLException {

        List<Personne> personnes = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";

        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(sql);

        while (rs.next()){
            Personne personne = new Personne();
            personne.setId(rs.getInt("id"));
            personne.setNom(rs.getString("nom"));
            personne.setPrenom(rs.getString("prenom"));
            personne.setEmail(rs.getString("email"));
            personne.setAdresse(rs.getString("adresse"));
            personne.setTelephone(rs.getString("telephone"));
            personne.setRole(rs.getString("role"));



            personnes.add(personne);

        }
        return personnes;
    }

    public ArrayList<Personne> listerPersonne() {
        ArrayList<Personne> myList = new ArrayList<>();
        try {

            String requete3 = "Select * FROM utilisateur";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()) {
                Personne rec = new Personne();

                rec.setId (rs.getInt(1));
                rec.setAdresse(rs.getString("adresse"));
                rec.setEmail(rs.getString("email"));
                rec.setNom(rs.getString("nom"));
                rec.setPrenom(rs.getString("prenom"));
                rec.setTelephone(rs.getString("telephone"));
                rec.setRole(rs.getString("role"));

                myList.add(rec);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        System.out.println("Affichage executé");
        return myList;


    }







































}
