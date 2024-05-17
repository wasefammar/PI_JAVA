package services.ServicesPersonne;


import controllers.User.SessionTempo;
import models.User.Personne;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import utils.DatabaseConnexion;

public class ServicePersonne implements IService<Personne, SessionTempo> {


    Connection cnx;

    public ServicePersonne() {
        cnx = DatabaseConnexion.getInstance().getConnection();
    }




    @Override
    public void ajoutUser(SessionTempo personne) throws SQLException {

        String sql = "INSERT INTO user (email,roles,password,nom, prenom, adresse,telephone) values(?, ?, ?, ?, ?, ?,?)";


        PreparedStatement ps = cnx.prepareStatement(sql);

        ps.setString(1,  personne.getAdresseEmail());
        String roleuser = "[\"ROLE_USER\"]";
        ps.setString(2, roleuser);
        ps.setString(3, personne.getPassword());
        ps.setString(4, personne.getNom());
        ps.setString(5, personne.getPrenom());
        ps.setString(6, personne.getAdress());
        ps.setString(7, personne.getTelphone());


        ps.executeUpdate();

    }




    @Override
    public void modifier(Personne personne) throws SQLException {

        String sql = "UPDATE user set email = ? , nom = ? , prenom = ? , adresse= ?  , telephone= ? where id = ?";

        PreparedStatement ps = cnx.prepareStatement(sql);


        ps.setString(1,personne.getEmail());

        ps.setString(2,personne.getNom());
        ps.setString(3,personne.getPrenom());
        ps.setString(4,personne.getAdresse());
        ps.setString(5,personne.getTelephone());
        ps.setInt(6,personne.getId());

        ps.executeUpdate();





    }

    @Override
    public void supprimer(int id) throws SQLException {

        String sql = "DELETE FROM user where id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);

        ps.setInt(1,id);
        ps.executeUpdate();

    }

    @Override
    public List<Personne> recuperer() throws SQLException {

        List<Personne> personnes = new ArrayList<>();
        String sql = "SELECT * FROM user";

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
            personne.setRole(rs.getString("roles"));



            personnes.add(personne);

        }
        return personnes;
    }


    public boolean userExists(String nom, String prenom) {
        try {
            String query = "SELECT COUNT(*) FROM user WHERE `nom` = ? AND `prenom` = ?";
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public boolean emailExists(String adresse_mail) {
        try {
            String query = "SELECT COUNT(*) FROM user WHERE `email` = ?";
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, adresse_mail);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }




    public boolean connecter(String adresse_mail, String mot_passe) {
        try {
            String req = "SELECT * FROM user WHERE `email` = ? AND `password` = ?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, adresse_mail);
            pstmt.setString(2, mot_passe);
            ResultSet rs = pstmt.executeQuery();

           if (rs.next()) {


                return true;
           }

        } catch (SQLException ex) {
            ex.printStackTrace();


        }

        // Si aucune ligne n'est retournée, cela signifie que la connexion a échoué
        return false;
    }



    public boolean VerifPwd(String mot_passe) {
        try {
            String req = "SELECT * FROM user WHERE  `password` = ?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, mot_passe);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {


                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();


        }

        // Si aucune ligne n'est retournée, cela signifie que la connexion a échoué
        return false;
    }




















    public Personne getUserByEmail3(String email) throws SQLException {
        Personne utilisateur = null;
        String query = "SELECT * FROM utilisateur WHERE email = ?";
        try  {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                utilisateur = new Personne();
                utilisateur.setId(resultSet.getInt("id_utilisateur"));
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setPrenom(resultSet.getString("prenom"));
                utilisateur.setAdresse(resultSet.getString("adresse_mail"));
                utilisateur.setRole(resultSet.getString("roles"));

            }
        }catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return utilisateur;
    }





    public void updatePassword(int id_utilisateur, String newPassword) throws SQLException {
        try {
            String req = "UPDATE `user` SET `password`=? WHERE `id`=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, id_utilisateur);
            pstmt.executeUpdate();
            System.out.println("Password updated successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Personne getUserByEmail(String adresse_mail) throws SQLException {
        Personne utilisateur = null;
        PreparedStatement pstmt = null;
        try {
            String query = "SELECT * FROM user WHERE email = ?";
            pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, adresse_mail);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                utilisateur = new Personne();
                utilisateur.setId(rs.getInt("id"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setAdresse(rs.getString("adresse"));
                utilisateur.setTelephone(rs.getString("telephone"));
                utilisateur.setPassword(rs.getString("password"));
                utilisateur.setRole(rs.getString("roles"));


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





    public static ByteArrayInputStream exportConventionExcel(List<Personne> personnes) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet("Conventions");

            // Création des titres des colonnes
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nom");
            headerRow.createCell(1).setCellValue("Prénom");
            headerRow.createCell(2).setCellValue("Reduction");


            // Remplissage des données des conventions
            int rowNum = 1; // Commencer à partir de la deuxième ligne après les titres
            for (Personne personne : personnes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(personne.getNom());
                row.createCell(1).setCellValue(personne.getPrenom());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public  void addUtilisateur(SessionTempo personne) throws SQLException {
        String sql = "INSERT INTO utilisateur (nom,prenom,adresse,telephone, email,mot_de_passe,score,gender,roles) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";


        PreparedStatement ps = cnx.prepareStatement(sql);

        ps.setString(1,  personne.getNom());
        //String roleuser = "\"roles\": \"User\"]";
        ps.setString(2, personne.getPrenom());
        ps.setString(3, personne.getAdress());
        ps.setString(4, personne.getTelphone());
        ps.setString(5, personne.getAdresseEmail());
        ps.setString(6, personne.getPassword());
        ps.setInt(7, 12);
        ps.setString(8, "femme");
        ps.setString(9, "[\"ROLE_USER\"]");


        ps.executeUpdate();
    }

    public void createPanier(int idUtilisateur) throws SQLException {
        String sql = "INSERT INTO panier (utilisateur_id) values(?)";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1,  idUtilisateur);
        ps.executeUpdate();
    }












}
